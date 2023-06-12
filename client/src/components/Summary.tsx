import { useContext } from "react";
import {
  Document,
  Page,
  Text,
  View,
  StyleSheet,
  PDFDownloadLink,
} from "@react-pdf/renderer";
import { ReservationContext } from "../AppRouter";
import { Button, Flex, Group, Stepper } from "@mantine/core";
import { useNavigate } from "react-router-dom";

const styles = StyleSheet.create({
  page: {
    fontFamily: "Helvetica",
    fontSize: 20,
    padding: 24,
  },
  container: {
    display: "flex",
    flexDirection: "column",
    alignItems: "flex-start",
  },
  heading: {
    fontSize: 22,
    marginBottom: 12,
    fontWeight: "bold",
  },
  subHeading: {
    fontSize: 20,
    marginTop: 12,
    marginBottom: 6,
    fontWeight: "bold",
  },
});

const generatePdfContent = ({ data: contextData }: any) => {
  const { data, personalData } = contextData;
  const { seanceId, movieId, cinemaHallId, seats } = data;
  const { firstName, lastName, email } = personalData;

  return (
    <Document>
      <Page size="A4" style={styles.page}>
        <View style={styles.container}>
          <Text style={styles.heading}>
            Thank you for your reservation! Here is your summary:
          </Text>
          <Text>Seance ID: {seanceId}</Text>
          <Text>Movie ID: {movieId}</Text>
          <Text>Cinema Hall ID: {cinemaHallId}</Text>
          <Text>First Name: {firstName}</Text>
          <Text>Last Name: {lastName}</Text>
          <Text>Email: {email}</Text>
          <Text style={styles.subHeading}>Seats:</Text>
          {seats.map((seat: any, index: any) => (
            <Text key={index}>
              Seat {index + 1}: Row {seat.rowInHall}, Column {seat.columnInHall}
              , Type: {seat.seatType.type}, Price: {seat.seatType.price}
            </Text>
          ))}
        </View>
      </Page>
    </Document>
  );
};

const Summary = () => {
  const { data } = useContext(ReservationContext);
  const navigate = useNavigate();

  const myDoc = generatePdfContent(data);

  return (
    <Flex direction="column" gap={"32px"} align="center">
      <Stepper active={3} breakpoint="sm" maw="50%">
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
        p={"32px"}
        sx={{
          backgroundColor: "#fcfcfc",
          borderRadius: "2px",
          width: "fit-content",
          boxShadow: "rgba(100, 100, 111, 0.2) 0px 7px 29px 0px",
        }}
      >
        {myDoc}
      </Flex>
      <Group>
        <PDFDownloadLink document={myDoc} fileName="reservation.pdf">
          {({ loading }) => (
            <Button loading={loading} disabled={loading}>
              Download PDF
            </Button>
          )}
        </PDFDownloadLink>
        <Button
          onClick={() => {
            navigate("/");
          }}
          variant="outline"
        >
          Go back to main page
        </Button>
      </Group>
    </Flex>
  );
};

export default Summary;
