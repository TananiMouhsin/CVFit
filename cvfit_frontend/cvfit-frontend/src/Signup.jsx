import React, { useState } from "react";
import cvimg from './assets/cvimg.png'
import gmailimg from './assets/envelope.png'
import lockimg from './assets/padlock.png'
import googleimg from './assets/google.png'
import phoneimg from './assets/telephone.png'
import userimg from './assets/user.png'

import "./Signup.css";

const SignUp = () => {
  const [formData, setFormData] = useState({
    username: "",
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
    <div className="signup-container">
      <div className="left-section">
        <img src={cvimg} alt="Signup Illustration" className="signup-image" />
      </div>

      <div className="right-section">
        <div className="header">
        <h2 className="title">Welcome to CvFit</h2>
        <p className="subtitle">Create your account in a few steps</p>  
        </div>
        <div className="form">


        <form className="signup-form" onSubmit={handleSubmit}>
          <div className="input-group">
          <img src={userimg} alt="" />            
            <input type="text" name="username" placeholder="First Name" onChange={handleChange} required />
          </div>
          <div className="input-group">
          <img src={userimg} alt="" />            <input type="text" name="username" placeholder="Last Name" onChange={handleChange} required />
          </div>
          <div className="input-group">
          <img src={phoneimg} alt="" />            <input type="phone" name="username" placeholder="Phone Number" onChange={handleChange} required />
          </div>

          <div className="input-group">
          <img src={gmailimg} alt="" />            <input type="email" name="email" placeholder="Email" onChange={handleChange} required />
          </div>

          <div className="input-group">
          <img src={lockimg} alt="" />            <input type="password" name="password" placeholder="Password" onChange={handleChange} required />
          </div>

          <button type="submit" className="signup-button">Sign Up</button>
        </form>

        </div>

        <div className="separator">
          <hr />
          <span>Or Sign Up with</span>
          <hr />
        </div>

        <button className="google-button">
          <img src={googleimg} alt="" />Sign Up with Google
        </button>
      </div>
    </div>
  );
};

export default SignUp;
