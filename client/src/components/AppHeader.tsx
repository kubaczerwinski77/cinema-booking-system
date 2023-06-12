/* eslint-disable @typescript-eslint/ban-ts-comment */
import { Dispatch, FC, SetStateAction, useLayoutEffect } from "react";
import {
  Burger,
  Flex,
  Group,
  Header,
  MediaQuery,
  Text,
  useMantineTheme,
} from "@mantine/core";

interface IProps {
  opened: boolean;
  setOpened: Dispatch<SetStateAction<boolean>>;
}

const AppHeader: FC<IProps> = ({ opened, setOpened }) => {
  const theme = useMantineTheme();

  useLayoutEffect(() => {
    // @ts-ignore
    google.accounts.id.renderButton(
      document.getElementById("google-login-button"),
      {
        theme: "outline",
        size: "large",
        text: "continue_with",
        width: "auto",
        height: 50,
        longtitle: true,
      }
    );
  }, []);

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

        <Group spacing={0}>
          <Text size="36px" weight={700} color={"blue"}>
            Cinema
          </Text>
          <Text size="36px" weight={700}>
            App
          </Text>
        </Group>

        <Flex id="google-login-button"></Flex>
      </Flex>
    </Header>
  );
};

export default AppHeader;
