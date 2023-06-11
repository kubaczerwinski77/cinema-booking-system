import { Dispatch, FC, SetStateAction } from "react";
import { IconSun, IconMoonStars } from "@tabler/icons-react";
import {
  ActionIcon,
  Burger,
  Flex,
  Group,
  Header,
  MediaQuery,
  Text,
  useMantineColorScheme,
  useMantineTheme,
} from "@mantine/core";

interface IProps {
  opened: boolean;
  setOpened: Dispatch<SetStateAction<boolean>>;
}

const AppHeader: FC<IProps> = ({ opened, setOpened }) => {
  const theme = useMantineTheme();
  const { colorScheme, toggleColorScheme } = useMantineColorScheme();
  const isDark = colorScheme === "dark";

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
          <Text size="36px" weight={700} color={isDark ? "yellow" : "blue"}>
            Cinema
          </Text>
          <Text size="36px" weight={700}>
            App
          </Text>
        </Group>

        <ActionIcon
          variant="outline"
          color={isDark ? "yellow" : "blue"}
          onClick={() => toggleColorScheme()}
          title="Toggle color scheme"
        >
          {isDark ? <IconSun size="1.1rem" /> : <IconMoonStars size="1.1rem" />}
        </ActionIcon>
      </Flex>
    </Header>
  );
};

export default AppHeader;
