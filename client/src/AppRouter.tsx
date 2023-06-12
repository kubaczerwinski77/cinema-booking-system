/* eslint-disable @typescript-eslint/ban-ts-comment */
import { AppShell, Navbar, Text } from "@mantine/core";
import AppHeader from "./components/AppHeader";
import { type Dispatch, useEffect, useState } from "react";
import { movieIdsToPromote, movies } from "./movies";
import MoviesList from "./components/MoviesList";
import MovieDetails from "./components/MovieDetails";
import { BrowserRouter, Route, Routes } from "react-router-dom";
import ChooseSeats from "./components/ChooseSeats";
import PersonalData from "./components/PersonalData";
import { createContext } from "react";

export const UserContext = createContext(null);
export const ReservationContext = createContext({
  dispatch: (() => undefined) as Dispatch<any>,
  data: null,
});

const AppRouter = () => {
  const [opened, setOpened] = useState(false);
  const [user, setUser] = useState(null);
  const [reservation, setReservation] = useState(null);

  useEffect(() => {
    // @ts-ignore
    google.accounts.id.initialize({
      client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
      callback: (res: any) => {
        setUser(res);
      },
    });
  }, []);

  console.log({ reservation });

  return (
    <UserContext.Provider value={user}>
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
        <ReservationContext.Provider
          value={{
            dispatch: setReservation,
            data: reservation,
          }}
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
              <Route path="/personalData" element={<PersonalData />} />
            </Routes>
          </BrowserRouter>
        </ReservationContext.Provider>
      </AppShell>
    </UserContext.Provider>
  );
};

export default AppRouter;
