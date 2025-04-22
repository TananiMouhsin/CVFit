import React, { useState } from "react";
import cvimg from './assets/cvimg.png'
import gmailimg from './assets/envelope.png'
import lockimg from './assets/padlock.png'
import googleimg from './assets/google.png'
import { useNavigate } from "react-router-dom";

import "./Login.css";

const Login = () => {
  const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    email: "",
    password: "",
  });



  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {

      email: formData.email,
      password: formData.password, // Vérifier que ce champ a bien une valeur
    };

    console.log("Sending data:", data); // Vérifie le contenu envoyé

    const response = await fetch("http://localhost:8080/auth/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      credentials: "include",
      body: JSON.stringify(data),
    });

    if (response.ok) {
      console.log(document.cookie); 
      console.log("User registered successfully");
      // try {
      //   const response = await fetch("http://localhost:8080/user/me", {
      //     method: "GET",
      //     credentials: "include", // 👈 makes the browser send the JSESSIONID cookie
      //   });
    
      //   if (response.ok) {
      //     const userData = await response.json();
      //     console.log("✅ User info:", userData);
      //   } else {
      //     const error = await response.text();
      //     console.error("❌ Failed to fetch user info:", error);
      //   }
      // } catch (err) {
      //   console.error("❗ Error while fetching user info:", err);
      // }
      

      navigate("/home");
    } else {
      console.error("Signup failed", await response.text());
    }
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
