import {
  Button,
  Checkbox,
  Flex,
  Group,
  Stepper,
  TextInput,
} from "@mantine/core";
import { useForm } from "@mantine/form";
import { useNavigate, useSearchParams } from "react-router-dom";
import { ReservationContext } from "../AppRouter";
import { useContext, useEffect, useState } from "react";
import { ISeance } from "../interfaces/seance";

const PersonalData = () => {
  const { data, dispatch: setReservation } = useContext(ReservationContext);
  const [searchParams] = useSearchParams();
  const seanceId = searchParams.get("seanceId");
  const seatsIds = searchParams.get("seats")?.split(",");
  const navigate = useNavigate();
  const [seance, setSeance] = useState<ISeance>();

  const handleFormSubmit = async (values: {
    firstName: string;
    lastName: string;
    email: string;
    termsOfService: boolean;
  }) => {
    setReservation({
      data: {
        ...data,
        personalData: values,
      },
    });
    // navigate(`/summary`);
    const res = await fetch(`${import.meta.env.VITE_APP_URL}/bookings`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({
        seats: seatsIds,
        seanseId: seanceId,
        date: seance?.date,
        name: values.firstName,
        lastName: values.lastName,
        email: values.email,
      }),
    });
    console.log({ res });
  };

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

  useEffect(() => {
    const fetchSeance = async () => {
      const res = await fetch(
        `${import.meta.env.VITE_APP_URL}/seanses/${seanceId}`
      );
      const data = await res.json();
      setSeance(data);
    };
    fetchSeance();
  }, [seanceId]);

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
      <Flex maw="50%" align="center" w="300px">
        <form
          onSubmit={form.onSubmit(handleFormSubmit)}
          style={{ width: "100%" }}
        >
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
