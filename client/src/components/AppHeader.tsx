/* eslint-disable @typescript-eslint/ban-ts-comment */
import { Dispatch, FC, SetStateAction, useLayoutEffect } from "react";
import {
  Burger,
  Button,
  Flex,
  Group,
  Header,
  MediaQuery,
  Text,
  useMantineTheme,
} from "@mantine/core";
import { useNavigate } from "react-router-dom";
import { IGoogleUser } from "../interfaces/user";
import jwtDecode from "jwt-decode";

interface IProps {
  opened: boolean;
  setOpened: Dispatch<SetStateAction<boolean>>;
  user: IGoogleUser;
  setUser: Dispatch<SetStateAction<IGoogleUser>>;
}

const AppHeader: FC<IProps> = ({ opened, setOpened, user, setUser }) => {
  const theme = useMantineTheme();
  const navigate = useNavigate();
  const tokenData: any = user.credential && jwtDecode(user.credential);

  useLayoutEffect(() => {
    try {
      // @ts-ignore
      google.accounts.id.renderButton(
        document.getElementById("google-login-button"),
        {
          theme: "outline",
          size: "large",
          text: "continue_with",
          width: "auto",
          height: 50,
        }
      );
    } catch (error) {
      console.log(error);
    }
  }, [tokenData]);

  return (
    <Header height={{ base: 50, md: 70 }} p="md">
      <Flex align="center" h="100%" justify={"space-around"}>
        <MediaQuery largerThan="sm" styles={{ display: "none" }}>
          <Burger
            opened={opened}
            onClick={() => setOpened((opened) => !opened)}
            size="sm"
            color={theme.colors.gray[6]}
            mr="xl"
          />
        </MediaQuery>

        <Group
          spacing={0}
          onClick={() => navigate("/movies")}
          sx={{
            cursor: "pointer",
          }}
        >
          <Text size="36px" weight={700} color={"blue"}>
            Cinema
          </Text>
          <Text size="36px" weight={700}>
            App
          </Text>
        </Group>

        {user.credential ? (
          <Group>
            <Text>{tokenData.email}</Text>
            <Button
              onClick={() => {
                setUser({} as IGoogleUser);
              }}
            >
              Logout
            </Button>
          </Group>
        ) : (
          <Flex w="300px" id="google-login-button"></Flex>
        )}
      </Flex>
    </Header>
  );
};

export default AppHeader;
