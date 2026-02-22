import "../App.css";
import { useState } from "react";

function Register({ setScreen }) {
  const [form, setForm] = useState({
    name: "",
    whatsappNumber: "",
    email: "",
    password: "",
    confirmPassword: "",
  });

  const [otpSent, setOtpSent] = useState(false);
  const [otp, setOtp] = useState("");

  const handleChange = (e) => {
    setForm({ ...form, [e.target.name]: e.target.value });
  };

  const handleSendOtp = async () => {
    if (form.password !== form.confirmPassword) {
      alert("Passwords do not match");
      return;
    }

    console.log("Send OTP API call here");

    // 🔥 Backend register API call
    /*
    await fetch("http://localhost:8080/user/register", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify(form),
    });
    */

    setOtpSent(true);
  };

  const handleVerifyOtp = async () => {
    console.log("Verify OTP API call");

    /*
    await fetch("http://localhost:8080/auth/verify-otp", {
      method: "POST",
      headers: { "Content-Type": "application/json" },
      body: JSON.stringify({
        email: form.email,
        otp: otp,
      }),
    });
    */

    alert("Registration Successful ✅");
    setScreen("login");
  };

  return (
    <>
      <div className="header">WChatAlert</div>

      <div className="card">
        <h3>Register</h3>

        <input
          className="input"
          name="name"
          placeholder="Full Name"
          onChange={handleChange}
        />

        <input
          className="input"
          name="whatsappNumber"
          placeholder="WhatsApp Number"
          onChange={handleChange}
        />

        <input
          className="input"
          name="email"
          placeholder="Email"
          onChange={handleChange}
        />

        <input
          className="input"
          type="password"
          name="password"
          placeholder="Password"
          onChange={handleChange}
        />

        <input
          className="input"
          type="password"
          name="confirmPassword"
          placeholder="Confirm Password"
          onChange={handleChange}
        />

        {!otpSent ? (
          <button className="button" onClick={handleSendOtp}>
            Send OTP
          </button>
        ) : (
          <>
            <input
              className="input"
              placeholder="Enter OTP"
              value={otp}
              onChange={(e) => setOtp(e.target.value)}
            />

            <button className="button" onClick={handleVerifyOtp}>
              Verify & Register
            </button>
          </>
        )}

        <p>
          Already have account?{" "}
          <span className="link" onClick={() => setScreen("login")}>
            Login
          </span>
        </p>
      </div>
    </>
  );
}

export default Register;