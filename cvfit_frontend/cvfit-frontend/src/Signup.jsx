import React, { useState } from "react";
import cvimg from './assets/cvimg.png'
import gmailimg from './assets/envelope.png'
import lockimg from './assets/padlock.png'
import googleimg from './assets/google.png'
import userimg from './assets/user.png'

import "./signup.css";
import { useNavigate } from "react-router-dom";

const SignUp = () => {
    const navigate = useNavigate();
  
  const [formData, setFormData] = useState({
    username: "",
    email: "",
    password: "",
  });

  const handleChange = (e) => {
    setFormData({ ...formData, [e.target.name]: e.target.value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    const data = {
      userName: formData.username,
      userEmail: formData.email,
      userPassword: formData.password, // Vérifier que ce champ a bien une valeur
    };

    console.log("Sending data:", data); // Vérifie le contenu envoyé

    const response = await fetch("http://localhost:8080/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (response.ok) {
      console.log("User registered successfully");
      const data1 = {

        email: formData.email,
        password: formData.password, // Vérifier que ce champ a bien une valeur
      };
  
      console.log("Sending data:", data1); // Vérifie le contenu envoyé
  
      const response = await fetch("http://localhost:8080/auth/login", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        credentials: "include",
        body: JSON.stringify(data1),
      });
  
      if (response.ok) {
        console.log(document.cookie); 
        console.log("User registered successfully");
        navigate("/home");
      } else {
        console.error("login failed", await response.text());
      }
    } else {
      console.error("Signup failed", await response.text());
    }
  };

  return (
    <div className="signup-container">
      <div className="left-section">
        <img src={cvimg} alt="Signup Illustration" className="signup-image" />
      </div>

      <div className="right-section">
        <h2 className="title">Welcome to CvFit</h2>
        <p className="subtitle">Create your account in a few steps</p>

        <form className="signup-form" onSubmit={handleSubmit}>
          {/* <div className="input-group">
            <span className="icon">👤</span>
            <input
              type="text"
              name="username"
              placeholder="First Name"
              onChange={handleChange}
              required
            />
          </div> */}
          <div className="input-group">
            {/* <span className="icon">👤</span> */}
            <img src={userimg} alt="" />
            <input
              type="text"
              name="username"
              placeholder="UserName"
              value={formData.username}
              onChange={handleChange}
              required
            />
          </div>
          {/* <div className="input-group">
              <span className="icon">📞</span>
              <input
                type="phone"
                name="username"
                placeholder="Phone Number"
                onChange={handleChange}
                required
              />
            </div> */}

          <div className="input-group">
            {/* <span className="icon">📧</span> */}
            <img src={gmailimg} alt="" />
            <input
              type="email"
              name="email"
              placeholder="Email"
              value={formData.email}
              onChange={handleChange}
              required
            />
          </div>

          <div className="input-group">
            {/* <span className="icon">🔒</span> */}
            <img src={lockimg} alt="" />
            <input
              type="password"
              name="password"
              placeholder="Password"
              value={formData.password}
              onChange={handleChange}
              required
            />
          </div>

          <button type="submit" className="signup-button">
            Sign Up
          </button>
        </form>

        <div className="separator">
          <hr />
          <span>
          already have an account ?  <span style={{ color: 'blue', cursor: 'pointer' }} onClick={() => window.location.href = '/login'}>Login</span>
</span>
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

export default SignUp;
