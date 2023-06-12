import {
  Button,
  Checkbox,
  Flex,
  Group,
  Stepper,
  TextInput,
} from "@mantine/core";
import { useForm } from "@mantine/form";
import { useSearchParams } from "react-router-dom";
import { ReservationContext } from "../AppRouter";
import { useContext } from "react";

const PersonalData = () => {
  const [searchParams] = useSearchParams();
  const seanceId = searchParams.get("seanceId");
  const movieId = searchParams.get("movieId");
  const cinemaHallId = searchParams.get("cinemaHallId");
  const seatsIds = searchParams.get("seats")?.split(",");
  const { data: reservation } = useContext(ReservationContext);

  console.log({ reservation });

  const form = useForm({
    initialValues: {
      firstName: "",
      lastName: "",
      email: "",
      termsOfService: false,
    },

    validate: {
      email: (value) => (/^\S+@\S+$/.test(value) ? null : "Invalid email"),
      termsOfService: (value) =>
        value ? null : "You must agree to terms of service",
      firstName: (value) =>
        value.length > 2 ? null : "First name must be at least 3 characters",
    },
  });

  return (
    <Flex justify="center" direction="column" align="center" gap="60px">
      <Stepper active={2} breakpoint="sm" maw="50%">
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
      <Flex direction="column" maw="40%" align="center">
        <form onSubmit={form.onSubmit((values) => console.log(values))}>
          <TextInput
            withAsterisk
            label="First name"
            placeholder="John"
            {...form.getInputProps("firstName")}
          />

          <TextInput
            label="Last name"
            placeholder="Doe"
            {...form.getInputProps("lastName")}
          />

          <TextInput
            withAsterisk
            label="Email"
            placeholder="john@doe.com"
            {...form.getInputProps("email")}
          />

          <Checkbox
            mt="md"
            label="I agree to sell my privacy"
            {...form.getInputProps("termsOfService", { type: "checkbox" })}
          />

          <Group position="right" mt="md">
            <Button type="submit">Submit</Button>
          </Group>
        </form>
      </Flex>
    </Flex>
  );
};

export default PersonalData;
