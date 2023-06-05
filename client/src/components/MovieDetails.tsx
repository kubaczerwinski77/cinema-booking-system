import { FC } from "react";
import { Container, Text, Image, Card, Group, Badge } from "@mantine/core";
import { Carousel } from "@mantine/carousel";
import { movies } from "../movies";
import { useParams } from "react-router-dom";

const MovieBookingPage: FC = () => {
  const { id } = useParams();
  const movie = movies.find((movie) => movie.imdbID === id);

  if (!movie) {
    return (
      <Container size="md">
        <Card shadow="sm" padding="lg" radius="md" mih={350}>
          <Text
            style={{
              fontSize: "1.5rem",
              fontWeight: "bold",
            }}
          >
            Movie not found
          </Text>
        </Card>
      </Container>
    );
  }

  return (
    <Container size="md">
      <Card shadow="sm" padding="lg" radius="md" mih={350}>
        <Card.Section>
          <Carousel mx="auto" withIndicators height={250}>
            {movie.Images.map((image) => (
              <Carousel.Slide key={image}>
                <Image src={image} alt="Movie poster" height={250} />
              </Carousel.Slide>
            ))}
          </Carousel>
        </Card.Section>

        <Group position="apart" mt="md">
          <Text
            style={{
              fontSize: "1.5rem",
              fontWeight: "bold",
            }}
          >
            {movie.Title} ({movie.Year})
          </Text>
          <Badge
            color="yellow"
            variant="light"
            style={{
              fontWeight: "bold",
            }}
          >
            {movie.imdbRating} ⭐ / 10
          </Badge>
        </Group>

        <Text size="sm" color="dimmed" align="justify">
          {movie.Genre} | 🕐 {movie.Runtime} | {movie.Rated}
        </Text>

        <Text size="sm" color="dimmed">
          Writer: {movie.Writer}
        </Text>
        <Text size="sm" color="dimmed">
          Actors: {movie.Actors}
        </Text>
        <Text size="sm" color="dimmed">
          Director: {movie.Director}
        </Text>
        <Text size="sm" color="dimmed">
          Language: {movie.Language} | Country: {movie.Country}
        </Text>

        <Text size="sm" color="dimmed">
          Released: {movie.Released}
        </Text>
        <Text
          size="sm"
          color="dimmed"
          style={{
            fontSize: "1.2rem",
            fontWeight: "bold",
          }}
        >
          <br />
          About the movie:
        </Text>
        <Text size="md" align="justify">
          {movie.Plot}
        </Text>
      </Card>
    </Container>
  );
};

export default MovieBookingPage;
