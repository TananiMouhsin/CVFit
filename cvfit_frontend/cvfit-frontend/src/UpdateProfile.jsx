import React, { useState } from 'react';
import './UpdateProfile.css';

const UpdateProfile = () => {
  const [username, setUsername] = useState('JohnDoe');
  const [email, setEmail] = useState('johndoe@example.com');
  const [password, setPassword] = useState('');
  const [birthdate, setBirthdate] = useState('');
  const [gender, setGender] = useState('');

  const handleSubmit = (e) => {
    e.preventDefault();
    // Tu peux envoyer les données au backend ici
    console.log({ username, email, password, birthdate, gender });
  };

  return (
    <div className="update-profile-container">
      <h2>Hello {username}</h2>
      <form onSubmit={handleSubmit}>
        <label>
          Username:
          <input
            type="text"
            value={username}
            onChange={(e) => setUsername(e.target.value)}
          />
        </label>

        <label>
          Email:
          <input
            type="email"
            value={email}
            onChange={(e) => setEmail(e.target.value)}
          />
        </label>

        <label>
          Password:
          <input
            type="password"
            value={password}
            onChange={(e) => setPassword(e.target.value)}
          />
        </label>

        <label>
          Birthdate:
          <input
            type="date"
            value={birthdate}
            onChange={(e) => setBirthdate(e.target.value)}
          />
        </label>

        <label>
          Gender:
          <select value={gender} onChange={(e) => setGender(e.target.value)}>
            <option value="">-- Select Gender --</option>
            <option value="female">Female</option>
            <option value="male">Male</option>
          </select>
        </label>

        <button type="submit">Save Changes</button>
      </form>
    </div>
  );
};

export default UpdateProfile;
