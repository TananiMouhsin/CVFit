import { useState } from "react";

const SignUp = () => {
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

    const response = await fetch("http://localhost:9090/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify(data),
    });

    if (response.ok) {
      console.log("User registered successfully");
    } else {
      console.error("Signup failed", await response.text());
    }
  };

  return (
    <div className="signup-container">
      <div className="left-section">
        <img src="/cv.png" alt="Signup Illustration" className="signup-image" />
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
            <span className="icon">👤</span>
            <input
              type="text"
              name="username"
              placeholder="Last Name"
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
            <span className="icon">📧</span>
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
            <span className="icon">🔒</span>
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
          <span>Or Sign Up with</span>
          <hr />
        </div>

        <button className="google-button">
          <span className="google-icon">🌍</span> Sign Up with Google
        </button>
      </div>
    </div>
  );
};

export default SignUp;
