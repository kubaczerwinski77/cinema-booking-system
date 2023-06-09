import { GoogleLogout } from "@leecheuk/react-google-login";
import { Flex } from "@mantine/core";

const Logout = () => {
  const onSuccess = () => {
    console.log("Logout Success");
  };

  return (
    <Flex id="signOutButton">
      <GoogleLogout
        clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}
        buttonText="Logout"
        onLogoutSuccess={onSuccess}
      />
    </Flex>
  );
};

export default Logout;
