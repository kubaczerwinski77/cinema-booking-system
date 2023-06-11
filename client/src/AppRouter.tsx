import { AppShell, Navbar, Text } from "@mantine/core";
import AppHeader from "./components/AppHeader";
import { useState } from "react";
import { movieIdsToPromote, movies } from "./movies";
import MoviesList from "./components/MoviesList";
import MovieDetails from "./components/MovieDetails";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ChooseSeats from "./components/ChooseSeats";

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
          width={{ sm: 150, lg: 150 }}
        >
          <Text>Application navbar</Text>
        </Navbar>
      }
      header={<AppHeader opened={opened} setOpened={setOpened} />}
    >
      <BrowserRouter>
        <Routes>
          <Route
            path="/"
            element={
              <MoviesList
                movies={movies}
                movieIdsToPromote={movieIdsToPromote}
              />
            }
          />
          <Route path="/:id" element={<MovieDetails />} />
          <Route path="/order" element={<ChooseSeats />} />
        </Routes>
      </BrowserRouter>
    </AppShell>
  );
};

export default AppRouter;
