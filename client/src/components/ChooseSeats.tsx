/* eslint-disable @typescript-eslint/ban-ts-comment */
import { useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { ISeat } from "../interfaces/seat";
import Seat from "./Seat";
import { Box, Button, Flex, List, Text, ThemeIcon } from "@mantine/core";
import { IconCircleDashed } from "@tabler/icons-react";

const ChooseSeats = () => {
  const [searchParams] = useSearchParams();
  const seanceId = searchParams.get("seanceId");
  const movieId = searchParams.get("movieId");
  const cinemaHallId = searchParams.get("cinemaHallId");
  const [seats, setSeats] = useState<ISeat[]>([]);
  const [reservedSeats, setReservedSeats] = useState<ISeat[]>([]);
  const [maxSeatsInRow, setMaxSeatsInRow] = useState<number>(0);
  const [selectedSeats, setSelectedSeats] = useState<ISeat[]>([]);
  const reservedIds = reservedSeats.map((seat) => seat.id);
  const navigate = useNavigate();

  const handleSeatClick = (seat: ISeat) => {
    if (selectedSeats.includes(seat)) {
      setSelectedSeats(selectedSeats.filter((s) => s !== seat));
    } else {
      setSelectedSeats([...selectedSeats, seat]);
    }
  };

  const handleContinueClick = () => {
    navigate(
      `/personalData?seanceId=${seanceId}&movieId=${movieId}&cinemaHallId=${cinemaHallId}&seats=${selectedSeats
        .map((seat) => seat.id)
        .join(",")}`
    );
  };

  useEffect(() => {
    const fetchSeats = async () => {
      const res = await fetch(
        `${import.meta.env.VITE_APP_URL}/seatsInHall/${cinemaHallId}`
      );
      const data = await res.json();
      setSeats(data);
      setMaxSeatsInRow(
        Math.max(...data.map((seat: ISeat) => seat.columnInHall))
      );
    };
    fetchSeats();
  }, [cinemaHallId]);

  useEffect(() => {
    const fetchReservedSeats = async () => {
      const res = await fetch(
        `${import.meta.env.VITE_APP_URL}/reservedSeats/${cinemaHallId}`
      );
      const data = await res.json();
      setReservedSeats(data);
    };
    fetchReservedSeats();
  }, [cinemaHallId]);

  return (
    <Flex sx={{ flexWrap: "wrap" }} mt={10} align={"flex-start"}>
      <Flex direction="column" justify="center" sx={{ flex: "2 1 0" }}>
        <Flex
          h={5}
          bg="#444"
          mb="32px"
          justify="center"
          sx={{ borderRadius: "10px" }}
        ></Flex>
        <Box
          style={{
            display: "grid",
            gridTemplateColumns: `repeat(${maxSeatsInRow}, 1fr)`,
            gap: "8px",
          }}
        >
          {seats.map((seat) => (
            <Seat
              key={seat.id}
              seat={seat}
              isReserved={seat.id % 13 === 0}
              // isReserved={reservedIds.includes(seat.id)}
              isSelected={selectedSeats.includes(seat)}
              onClick={() => handleSeatClick(seat)}
            />
          ))}
        </Box>
      </Flex>
      <Flex direction="column" align="center" sx={{ flex: "1 1 0" }}>
        <Text
          weight={500}
          size="lg"
          style={{ marginBottom: "16px", textAlign: "center" }}
        >
          Seats legend
        </Text>

        <List spacing="xs" size="sm" miw="300px">
          <List.Item
            icon={
              <ThemeIcon color="#777" size={24} radius="xl">
                <IconCircleDashed size="1rem" />
              </ThemeIcon>
            }
          >
            Reserved seat
          </List.Item>
          <List.Item
            icon={
              <ThemeIcon color="#ccc" size={24} radius="xl">
                <IconCircleDashed size="1rem" />
              </ThemeIcon>
            }
          >
            Standard seat
          </List.Item>
          <List.Item
            icon={
              <ThemeIcon color="#228be655" size={24} radius="xl">
                <IconCircleDashed size="1rem" />
              </ThemeIcon>
            }
          >
            Premium seat
          </List.Item>
          <List.Item
            icon={
              <ThemeIcon color="#228be6" size={24} radius="xl">
                <IconCircleDashed size="1rem" />
              </ThemeIcon>
            }
          >
            Selected seat
          </List.Item>
        </List>

        {selectedSeats.length !== 0 && (
          <>
            <Text
              weight={500}
              size="lg"
              mt="16px"
              style={{ marginBottom: "16px", textAlign: "center" }}
            >
              Your seats
            </Text>

            <List spacing="xs" size="sm" miw="300px">
              {selectedSeats.map((seat) => (
                <List.Item key={seat.id}>
                  1x <i>{seat.seatType.type}</i> seat {seat.rowInHall} row{" "}
                  {seat.columnInHall} - {seat.seatType.price} $
                </List.Item>
              ))}
            </List>
          </>
        )}
        <Button
          size="xs"
          variant="outline"
          color="blue"
          mt="32px"
          disabled={selectedSeats.length === 0}
          onClick={handleContinueClick}
        >
          Continue{" "}
          {selectedSeats.length !== 0 &&
            `(${selectedSeats.reduce((a, b) => a + b.seatType.price, 0)}$)`}
        </Button>
      </Flex>
    </Flex>
  );
};

export default ChooseSeats;
