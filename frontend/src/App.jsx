import { useState } from "react";
import Login from "./components/login";
import Register from "./components/register";
import OTP from "./components/otp";
import Dashboard from "./components/dashbord";

function App() {
  const [screen, setScreen] = useState("login");

  if (screen === "login") return <Login setScreen={setScreen} />;
  if (screen === "register") return <Register setScreen={setScreen} />;
  if (screen === "otp") return <OTP setScreen={setScreen} />;
  if (screen === "dashboard") return <Dashboard setScreen={setScreen} />;

  return null;
}

export default App;