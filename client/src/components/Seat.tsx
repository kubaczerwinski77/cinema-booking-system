import { FC } from "react";
import { ISeat } from "../interfaces/seat";
import { Flex, Text } from "@mantine/core";
import { useMediaQuery } from "@mantine/hooks";

interface IProps {
  seat: ISeat;
  isReserved: boolean;
  isSelected: boolean;
  onClick?: () => void;
}

const getSeatColor = (
  seat: ISeat,
  isReserved: boolean,
  isSelected: boolean
) => {
  if (seat.seatType.type === "EMPTY") {
    return "#fff";
  }

  if (isReserved) {
    return "#777";
  }

  if (isSelected) {
    return "#228be6";
  }

  if (seat.seatType.type === "STANDARD") {
    return "#ccc";
  }

  if (seat.seatType.type === "PREMIUM") {
    return "#228be655";
  }
};

const Seat: FC<IProps> = ({ seat, isReserved, isSelected, onClick }) => {
  const isMobile = useMediaQuery("(max-width: 768px)");

  const shouldDisableCursor = seat.seatType.type === "EMPTY" || isReserved;

  return (
    <Flex
      bg={getSeatColor(seat, isReserved, isSelected)}
      justify="center"
      align="center"
      mih="50px"
      sx={{
        borderRadius: "4px",
        userSelect: "none",
        cursor: shouldDisableCursor ? "not-allowed" : "pointer",
      }}
      onClick={onClick}
    >
      <Text size={isMobile ? "xs" : "sm"}>
        {seat.seatType.type === "EMPTY" || isReserved
          ? ""
          : seat.seatType.type[0]}
      </Text>
    </Flex>
  );
};

export default Seat;
