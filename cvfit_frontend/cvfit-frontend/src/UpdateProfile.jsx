import React, { useState, useEffect } from 'react';
import './UpdateProfile.css';
import axios from 'axios'; // make sure you install axios if not already

const UpdateProfile = () => {
  const [username, setUsername] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');

  useEffect(() => {
    // Fetch user details when component mounts
    const fetchUserDetails = async () => {
      try {
        const response = await axios.get('http://localhost:8080/user/me', { withCredentials: true });
        const user = response.data;

        if (user && typeof user === 'object') {
          setUsername(user.userName || '');
          setEmail(user.userEmail || '');
        }
      } catch (error) {
        console.error('Failed to fetch user details', error);
      }
    };

    fetchUserDetails();
  }, []);

  const handleSubmit = async (e) => {
    e.preventDefault();
    try {
      const updatedUser = {
        userName: username,
        userEmail: email,
        userPassword: password // Send password only if user entered it
      };

      await axios.put('http://localhost:8080/user/update', updatedUser, { withCredentials: true });
      alert('Profile updated successfully!');
      setPassword(''); // Clear password field after updating
    } catch (error) {
      console.error('Failed to update profile', error);
      alert('Failed to update profile');
    }
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
            placeholder="Enter new password"
          />
        </label>

        <button type="submit">Save Changes</button>
      </form>
    </div>
  );
};

export default UpdateProfile;
