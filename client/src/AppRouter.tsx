import { AppShell, Navbar, Text } from "@mantine/core";
import AppHeader from "./components/AppHeader";
import { useState } from "react";

const AppRouter = () => {
  const [opened, setOpened] = useState(false);

  return (
    <AppShell
      navbarOffsetBreakpoint="sm"
      asideOffsetBreakpoint="sm"
      navbar={
        <Navbar
          p="md"
          hiddenBreakpoint="sm"
          hidden={!opened}
          width={{ sm: 200, lg: 300 }}
        >
          <Text>Application navbar</Text>
        </Navbar>
      }
      header={<AppHeader opened={opened} setOpened={setOpened} />}
    >
      <Text>Resize app to see responsive navbar in action</Text>
    </AppShell>
  );
};

export default AppRouter;
