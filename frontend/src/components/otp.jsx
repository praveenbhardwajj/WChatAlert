import "../App.css";
import { useState } from "react";

function OTP({ setScreen }) {
  const [otp, setOtp] = useState("");

  const handleVerify = () => {
    console.log("Verify OTP:", otp);

    // backend verify call
    setScreen("login");
  };

  return (
    <>
      <div className="header">WChatAlert</div>

      <div className="card">
        <h3>OTP Verification</h3>

        <input
          className="input"
          placeholder="Enter OTP"
          value={otp}
          onChange={(e) => setOtp(e.target.value)}
        />

        <button className="button" onClick={handleVerify}>
          Verify OTP
        </button>
      </div>
    </>
  );
}

export default OTP;