import React, { useState } from "react";
import cvimg from './assets/cvimg.png'
import gmailimg from './assets/envelope.png'
import lockimg from './assets/padlock.png'
import googleimg from './assets/google.png'
import "./Login.css";

const Login = () => {
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });



  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = (e) => {
    e.preventDefault();
    console.log(formData);

    
  };

  return (
    <div className="login-container">
      <div className="left-section">
        <img src={cvimg} alt="login" className="login-image" />
      </div>

      <div className="right-section">
        <div className="header">

        <h2 className="title">Welcome to CvFit</h2>
        <p className="subtitle">Log into Existing Account</p>
        </div>
        <div className="form">

        <form className="login-form" onSubmit={handleSubmit}>
          <div className="input-group">
            {/* <span className="icon">📧</span> */}
            <img src={gmailimg} alt="" />
            <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
          </div>

          <div className="input-group">
            {/* <span className="icon">🔒</span> */}
            <img src={lockimg} alt="" />
            <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
          </div>

          <button type="submit" className="login-button">Login</button>
        </form>

        </div>



        <div className="separator">
          <hr />
          <span>Or Sign Up with</span>
          <hr />
        </div>

        <button className="google-button">
        <img src={googleimg} alt="" />
        Login with Google
        </button>
      </div>
    </div>
  );
};

export default Login;
