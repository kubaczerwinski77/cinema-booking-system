import { ActionIcon, Button, Chip, Flex, Group, Text } from "@mantine/core";
import { useContext, useEffect, useRef, useState } from "react";
import { Movie, movies } from "../../movies";
import { ICinemaHall } from "../../interfaces/cinemaHall";
import { DatePicker, TimeInput } from "@mantine/dates";
import { notifications } from "@mantine/notifications";
import { IconClock } from "@tabler/icons-react";
import { UserContext } from "../../AppRouter";

const SeanceCreator = () => {
  const { credential } = useContext(UserContext);
  const [cinemaHalls, setCinemaHalls] = useState<ICinemaHall[]>([]);
  const [choosenCinemaHall, setChoosenCinemaHall] = useState<number | null>(
    null
  );
  const [choosenMovie, setChoosenMovie] = useState<string>("");
  const [choosenDate, setChoosenDate] = useState<Date | null>(null);
  const [loading, setLoading] = useState<boolean>(false);
  const ref = useRef<any>();
  const submitButtonDisabled = !(
    choosenCinemaHall &&
    choosenDate &&
    choosenMovie
  );

  const handleTimeChange = (value: string) => {
    const hours = value?.split(":")[0];
    const minutes = value?.split(":")[1];
    if (hours && minutes) {
      choosenDate?.setHours(parseInt(hours));
      choosenDate?.setMinutes(parseInt(minutes));
    }
  };

  const handleMovieClick = (movieId: string) => {
    if (choosenMovie === movieId) {
      setChoosenMovie("");
    } else {
      setChoosenMovie(movieId);
    }
  };

  const fetchCinemaHalls = async () => {
    const response = await fetch(
      `${import.meta.env.VITE_API_URL || "http://localhost:8080"}/cinemaHalls`
    );
    const data = await response.json();
    setCinemaHalls(data);
  };

  const handleCinemaHallClick = (cinemaHallId: number) => {
    if (choosenCinemaHall === cinemaHallId) {
      setChoosenCinemaHall(null);
    } else {
      setChoosenCinemaHall(cinemaHallId);
    }
  };

  const handleSubmitButtonClick = async () => {
    setLoading(true);
    const res = await fetch(
      `${import.meta.env.VITE_API_URL || "http://localhost:8080"}/seances`,
      {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
          Authorization: `Bearer ${credential}`,
        },
        body: JSON.stringify({
          movieId: choosenMovie,
          cinemaHallId: choosenCinemaHall,
          date: choosenDate?.toISOString(),
        }),
      }
    );
    const data = await res.json();
    notifications.show({
      title: "Seance created",
      message: "Seance created successfully",
      variant: "success",
    });

    setChoosenCinemaHall(null);
    setChoosenDate(null);
    setChoosenMovie("");
    setLoading(false);
  };

  useEffect(() => {
    fetchCinemaHalls();
  }, []);

  return (
    <Flex w="100%" gap="md" sx={{ flexWrap: "wrap" }}>
      <Flex
        direction="column"
        sx={{
          borderRadius: "8px",
          boxShadow: "rgba(149, 157, 165, 0.2) 0px 8px 24px",
        }}
      >
        <Text mx="16px" p="16px" size={"30px"}>
          Choose movie:
        </Text>
        <Flex
          mah="80vh"
          p="16px"
          direction="column"
          sx={{
            overflowY: "scroll",
          }}
          gap="xs"
        >
          {movies.map((movie: Movie) => {
            return (
              <Chip
                key={movie.imdbID}
                radius="md"
                variant="filled"
                size="xl"
                mx="16px"
                sx={{
                  label: {
                    justifyContent: "center",
                    width: "100%",
                  },
                }}
                checked={choosenMovie === movie.imdbID}
                onClick={() => handleMovieClick(movie.imdbID)}
              >
                <Text weight={500}>{movie.Title}&nbsp;</Text>
                <Text>({movie.Year})</Text>
              </Chip>
            );
          })}
        </Flex>
      </Flex>
      <Flex
        direction="column"
        sx={{
          borderRadius: "8px",
          boxShadow: "rgba(149, 157, 165, 0.2) 0px 8px 24px",
        }}
      >
        <Text mx="16px" p="16px" size={"30px"}>
          Choose cinema hall:
        </Text>
        <Flex
          w="372px"
          mah="80vh"
          p="16px"
          direction="column"
          sx={{
            overflowY: "scroll",
          }}
          gap="xs"
        >
          {cinemaHalls.map((hall: ICinemaHall) => {
            return (
              <Chip
                key={hall.id}
                radius="md"
                variant="filled"
                size="xl"
                mx="16px"
                sx={{
                  label: {
                    justifyContent: "center",
                    width: "100%",
                  },
                }}
                checked={choosenCinemaHall === hall.id}
                onClick={() => handleCinemaHallClick(hall.id)}
              >
                <Text weight={500}>{hall.name}&nbsp;</Text>
                <Text>({hall.totalSize} seats)</Text>
              </Chip>
            );
          })}
        </Flex>
      </Flex>
      <Flex
        direction="column"
        sx={{
          borderRadius: "8px",
          boxShadow: "rgba(149, 157, 165, 0.2) 0px 8px 24px",
        }}
      >
        <Text mx="16px" p="16px" size={"30px"}>
          Choose seance date:
        </Text>
        <Flex
          mah="80vh"
          p="16px"
          direction="column"
          sx={{
            overflowY: "scroll",
          }}
          gap="xl"
        >
          <Group position="center" mx="16px">
            <DatePicker
              value={choosenDate}
              allowDeselect
              onChange={setChoosenDate}
              minDate={new Date()}
            />
          </Group>
          <TimeInput
            label="Seance start time"
            ref={ref as any}
            disabled={!choosenDate}
            onChange={(e) => handleTimeChange(e.target.value)}
            rightSection={
              <ActionIcon onClick={() => ref.current.showPicker()}>
                <IconClock size="1rem" stroke={1.5} />
              </ActionIcon>
            }
            mx="24px"
          />
          <Button
            disabled={submitButtonDisabled || loading}
            loading={loading}
            onClick={handleSubmitButtonClick}
          >
            Create seance
          </Button>
        </Flex>
      </Flex>
    </Flex>
  );
};

export default SeanceCreator;
