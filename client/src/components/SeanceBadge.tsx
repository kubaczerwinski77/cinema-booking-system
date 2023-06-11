import { Flex } from "@mantine/core";
import { FC } from "react";

interface IProps {
  dateString: string;
  choosen?: boolean;
  onClick?: () => void;
}

const SeanceBadge: FC<IProps> = ({ dateString, choosen, onClick }) => {
  const date = new Date(dateString);

  return (
    <Flex
      onClick={onClick}
      p={8}
      bg={choosen ? "#ccc" : "#f0f0f0"}
      sx={{
        borderRadius: "30px",
        width: "fit-content",
        cursor: "pointer",
        border: "3px solid #ccc",
      }}
    >
      {date.toLocaleDateString()} {date.getHours()}:{date.getMinutes()}
    </Flex>
  );
};

export default SeanceBadge;
