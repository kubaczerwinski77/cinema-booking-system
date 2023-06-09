import {
  GoogleLogin,
  GoogleLoginResponse,
  GoogleLoginResponseOffline,
} from "@leecheuk/react-google-login";
import { Flex } from "@mantine/core";

const Login = () => {
  const onSuccess = (res: GoogleLoginResponse | GoogleLoginResponseOffline) => {
    console.log("Login Success:", res);
  };

  const onFailure = (res: any) => {
    console.log("Login Failed:", res);
  };

  return (
    <Flex id="signInButton">
      <GoogleLogin
        clientId={import.meta.env.VITE_GOOGLE_CLIENT_ID}
        buttonText="Login"
        onSuccess={onSuccess}
        onFailure={onFailure}
        cookiePolicy={"single_host_origin"}
        isSignedIn={true}
      />
    </Flex>
  );
};

export default Login;
