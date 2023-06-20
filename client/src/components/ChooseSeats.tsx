/* eslint-disable @typescript-eslint/ban-ts-comment */
import { useContext, useEffect, useState } from "react";
import { useNavigate, useSearchParams } from "react-router-dom";
import { ISeat } from "../interfaces/seat";
import Seat from "./Seat";
import {
  Box,
  Button,
  Flex,
  List,
  Stepper,
  Text,
  ThemeIcon,
} from "@mantine/core";
import { IconCircleDashed } from "@tabler/icons-react";
import { ReservationContext } from "../AppRouter";
import { appUrl } from "../utils";
import { useQuery } from "@tanstack/react-query";

const ChooseSeats = () => {
  const [searchParams] = useSearchParams();
  const seanceId = searchParams.get("seanceId");
  const movieId = searchParams.get("movieId");
  const cinemaHallId = searchParams.get("cinemaHallId");
  const [seats, setSeats] = useState<ISeat[]>([]);
  // const [reservedSeats, setReservedSeats] = useState<ISeat[]>([]);
  const [maxSeatsInRow, setMaxSeatsInRow] = useState<number>(0);
  const [selectedSeats, setSelectedSeats] = useState<ISeat[]>([]);
  const navigate = useNavigate();
  const { dispatch: setReservation } = useContext(ReservationContext);

  const fetchReservedSeats = async () => {
    const res = await fetch(`${appUrl}/reservedSeates/${seanceId}`);
    return res.json();
  };

  const { data: reservedSeats } = useQuery(
    ["reservedSeats"],
    fetchReservedSeats
  );

  const reservedIds = (reservedSeats as ISeat[])?.map((seat) => seat.id);
  const handleSeatClick = (seat: ISeat) => {
    if (reservedIds?.includes(seat.id)) {
      return;
    }
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
    setReservation({
      data: {
        seanceId,
        movieId,
        cinemaHallId,
        seats: selectedSeats,
      },
    });
  };

  useEffect(() => {
    const fetchSeats = async () => {
      const res = await fetch(`${appUrl}/seatsInHall/${cinemaHallId}`);
      const data = await res.json();
      setSeats(data);
      setMaxSeatsInRow(
        Math.max(...data.map((seat: ISeat) => seat.columnInHall))
      );
    };
    fetchSeats();
  }, [cinemaHallId]);

  // useEffect(() => {
  //   fetchReservedSeats();
  // }, [seanceId]);

  return (
    <Flex justify="center" direction="column" align="center" gap="32px">
      <Stepper active={1} breakpoint="sm" maw="50%">
        <Stepper.Step
          label="First step"
          description="Pick a movie"
        ></Stepper.Step>
        <Stepper.Step
          label="Second step"
          description="Choose seats"
        ></Stepper.Step>
        <Stepper.Step
          label="Final step"
          description="Provide personal data"
        ></Stepper.Step>
      </Stepper>
      <Flex
        sx={{ flexWrap: "wrap" }}
        mt={10}
        align={"flex-start"}
        w="100%"
        gap={"xl"}
      >
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
                isReserved={reservedIds?.includes(seat.id)}
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
                    1x <i>{seat.seatType.type}</i> seat {seat.columnInHall} row{" "}
                    {seat.rowInHall} - {seat.seatType.price} $
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
    </Flex>
  );
};

export default ChooseSeats;
