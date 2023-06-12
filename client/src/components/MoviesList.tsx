import { FC, useEffect, useState } from "react";
import { Movie } from "../movies";
import {
  Badge,
  Button,
  Card,
  Col,
  Flex,
  Grid,
  Group,
  Image,
  Text,
  TextInput,
  useMantineColorScheme,
} from "@mantine/core";
import {
  useDebouncedValue,
  useHover,
  useMediaQuery,
  useScrollIntoView,
} from "@mantine/hooks";
import { useLocation, useNavigate } from "react-router-dom";

interface IProps {
  movies: Movie[];
  movieIdsToPromote: string[];
}

const PromoCard: FC<{ movie: Movie }> = ({ movie }) => {
  const navigate = useNavigate();

  const handleReadMoreClick = () => {
    navigate(`/${movie.imdbID}`);
  };

  return (
    <Card shadow="sm" padding="lg" radius="md" withBorder maw={350} mih={350}>
      <Card.Section>
        <Image src={movie.Images[0]} height={160} alt="Movie poster" />
      </Card.Section>

      <Group position="apart" mt="md" mb="xs">
        <Text weight={500}>{movie.Title}</Text>
        <Badge color="yellow" variant="light">
          HOT 🔥
        </Badge>
      </Group>

      <Text size="sm" color="dimmed" align="justify" lineClamp={3}>
        {movie.Plot}
      </Text>

      <Button
        variant="light"
        color="blue"
        fullWidth
        mt="md"
        radius="md"
        onClick={handleReadMoreClick}
      >
        Read more
      </Button>
    </Card>
  );
};

const MovieListItem: FC<{ movie: Movie; isMobile: boolean }> = ({
  movie,
  isMobile,
}) => {
  const { colorScheme } = useMantineColorScheme();
  const isDark = colorScheme === "dark";
  const { ref, hovered } = useHover();
  const navigate = useNavigate();

  const handleItemClick = () => {
    navigate(`/${movie.imdbID}`);
  };

  return (
    <Card
      ref={ref}
      shadow="xs"
      padding="md"
      w="100%"
      style={{
        display: "flex",
        alignItems: "center",
        cursor: "pointer",
        backgroundColor: isDark
          ? hovered
            ? "#444"
            : undefined
          : hovered
          ? "#eee"
          : "#fff",
      }}
      onClick={handleItemClick}
    >
      <Grid gutter="md">
        {!isMobile && (
          <Col span={3}>
            <Image
              src={movie.Images[0]}
              alt={movie.Title}
              height={200}
              width="100%"
              fit="cover"
            />
          </Col>
        )}
        <Col span={9}>
          <div>
            <Text size="lg" weight={500}>
              {movie.Title} ({movie.Year})
            </Text>
            <Text size="sm" color="#909296" style={{ marginTop: "0.5rem" }}>
              {movie.Genre}
            </Text>
            <Text style={{ marginTop: "1rem" }}>{movie.Plot}</Text>
            <Text size="sm" color="#909296" style={{ marginTop: "1rem" }}>
              Director: {movie.Director}
            </Text>
            <Text size="sm" color="#909296">
              Actors: {movie.Actors}
            </Text>
            <Text size="sm" color="#909296">
              Runtime: {movie.Runtime}
            </Text>
          </div>
        </Col>
      </Grid>
    </Card>
  );
};

const MoviesList: FC<IProps> = ({ movies, movieIdsToPromote }) => {
  const isMobile = useMediaQuery("(max-width: 768px)");
  const [value, setValue] = useState("");
  const [debounced] = useDebouncedValue(value, 200);
  const { scrollIntoView, targetRef } = useScrollIntoView({
    offset: 100,
  });
  const {
    scrollIntoView: scrollRecommendedIntoView,
    targetRef: targetRecommendedRef,
  } = useScrollIntoView({
    offset: 200,
  });
  const location = useLocation();
  const { hash } = location;

  useEffect(() => {
    if (hash === `#all`) {
      scrollIntoView();
    }
    if (hash === `#recommended` || !hash) {
      scrollRecommendedIntoView();
    }
  }, [scrollIntoView, hash, scrollRecommendedIntoView]);

  const filteredMovies = debounced
    ? movies.filter((movie) =>
        movie.Title.toLowerCase().includes(debounced.toLowerCase())
      )
    : movies.filter((movie) => movieIdsToPromote.indexOf(movie.imdbID) === -1);

  useEffect(() => {
    document.title = `CinemaApp`;
  }, []);

  return (
    <Flex w={"100%"} h={"100%"} direction="column" p={25} align="center">
      <Flex
        align="center"
        justify="space-between"
        mb={isMobile ? "lg" : "xl"}
        w="90%"
      >
        <Text
          weight={700}
          size={36}
          ref={
            targetRecommendedRef as React.MutableRefObject<HTMLHeadingElement>
          }
        >
          {debounced
            ? `
          Search results for "${debounced}"`
            : "Recommended this week"}
        </Text>
        <TextInput
          value={value}
          onChange={(event) => setValue(event.currentTarget.value)}
          placeholder="Search"
          icon={<Text size="sm">🔍</Text>}
        />
      </Flex>
      {!debounced && (
        <>
          <Flex
            justify="center"
            align="center"
            gap={20}
            direction={isMobile ? "column" : "row"}
            w={"90%"}
          >
            {movies
              .filter((movie) => movieIdsToPromote.indexOf(movie.imdbID) !== -1)
              .map((movie) => (
                <PromoCard movie={movie} key={movie.imdbID} />
              ))}
          </Flex>

          <Text
            ref={targetRef as React.MutableRefObject<HTMLHeadingElement>}
            weight={700}
            size={36}
            mt={isMobile ? "lg" : "xl"}
            w="90%"
          >
            All movies
          </Text>
        </>
      )}
      <Flex
        justify="center"
        align="center"
        gap={20}
        direction={"column"}
        w="90%"
      >
        {filteredMovies.map((movie) => (
          <MovieListItem movie={movie} isMobile={isMobile} key={movie.imdbID} />
        ))}
      </Flex>
    </Flex>
  );
};

export default MoviesList;
