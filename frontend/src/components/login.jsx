import "../App.css";
import { useState } from "react";

function Login({ setScreen }) {
  const [emailOrNumber, setEmailOrNumber] = useState("");
  const [password, setPassword] = useState("");

  const handleLogin = async () => {
    console.log("Login:", emailOrNumber, password);

    // 🔥 Backend call yaha aayega
    /*
    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify({
        emailOrNumber,
        password
      })
    });

    const data = await response.json();

    if (data.success) {
      chrome.storage.local.set({ token: data.token });
      setScreen("dashboard");
    }
    */

    // Temporary testing ke liye:
    setScreen("dashboard");
  };

  return (
    <>
      <div className="header">WChatAlert</div>

      <div className="card">
        <h3>Login</h3>

        <input
          className="input"
          placeholder="Email or Mobile Number"
          value={emailOrNumber}
          onChange={(e) => setEmailOrNumber(e.target.value)}
        />

        <input
          className="input"
          type="password"
          placeholder="Password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
        />

        <button className="button" onClick={handleLogin}>
          Login
        </button>

        <p>
          Don't have account?{" "}
          <span
            className="link"
            onClick={() => setScreen("register")}
          >
            Register
          </span>
        </p>
      </div>
    </>
  );
}

export default Login;