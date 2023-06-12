/* eslint-disable @typescript-eslint/ban-ts-comment */
import { AppShell, NavLink, Navbar, Text } from "@mantine/core";
import AppHeader from "./components/AppHeader";
import { type Dispatch, useEffect, useState } from "react";
import { movieIdsToPromote, movies } from "./movies";
import MoviesList from "./components/MoviesList";
import MovieDetails from "./components/MovieDetails";
import { Navigate, Route, Routes, useNavigate } from "react-router-dom";
import ChooseSeats from "./components/ChooseSeats";
import PersonalData from "./components/PersonalData";
import { createContext } from "react";
import Summary from "./components/Summary";
import {
  IconMenu2,
  IconMovie,
  IconNewSection,
  IconThumbUp,
  IconUserPlus,
} from "@tabler/icons-react";

export const UserContext = createContext(null);
export const ReservationContext = createContext({
  dispatch: (() => undefined) as Dispatch<any>,
  data: {
    data: {
      seanceId: "",
      movieId: "",
      cinemaHallId: "",
      seats: [],
    },
    personalData: {
      name: "",
      surname: "",
      email: "",
    },
  },
});

const INITIAL_RESERVATION = {
  data: { seanceId: "", movieId: "", cinemaHallId: "", seats: [] },
  personalData: {
    name: "",
    surname: "",
    email: "",
  },
};

const AppRouter = () => {
  const [opened, setOpened] = useState(false);
  const [user, setUser] = useState(null);
  const [reservation, setReservation] = useState(INITIAL_RESERVATION);
  const navigate = useNavigate();

  useEffect(() => {
    // @ts-ignore
    google.accounts.id.initialize({
      client_id: import.meta.env.VITE_GOOGLE_CLIENT_ID,
      callback: (res: any) => {
        setUser(res);
      },
    });
  }, []);

  console.log({ user });

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
            width={{ sm: 220, lg: 220 }}
          >
            <NavLink
              label="Movies"
              icon={<IconMovie size="1rem" stroke={1.5} />}
              childrenOffset={28}
              active={
                window.location.pathname === "/movies" ||
                window.location.pathname.startsWith("/tt")
              }
              opened
            >
              <NavLink
                label="Recommended"
                icon={<IconThumbUp size="1rem" stroke={1.5} />}
                onClick={() => navigate("/movies#recommended")}
              />
              <NavLink
                label="All movies"
                icon={<IconMenu2 size="1rem" stroke={1.5} />}
                onClick={() => navigate("/movies#all")}
              />
            </NavLink>
            {user && (
              <NavLink
                opened
                label="Admin panel"
                icon={<IconUserPlus size="1rem" stroke={1.5} />}
                childrenOffset={28}
                active={window.location.pathname.startsWith("/admin")}
              >
                <NavLink
                  label="Create seanse"
                  icon={<IconNewSection size="1rem" stroke={1.5} />}
                />
              </NavLink>
            )}
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
          <Routes>
            <Route
              path="/movies"
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
            <Route path="/summary" element={<Summary />} />
            <Route path="*" element={<Navigate to="/movies" replace />} />
          </Routes>
        </ReservationContext.Provider>
      </AppShell>
    </UserContext.Provider>
  );
};

export default AppRouter;
