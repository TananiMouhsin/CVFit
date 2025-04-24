import React, { useState, useRef, useEffect } from 'react';
import './profile.css';
import { Link } from 'react-router-dom';

const ProfileMenu = ({ userIcon }) => {
  const [isOpen, setIsOpen] = useState(false);
  const [showLogoutConfirmation, setShowLogoutConfirmation] = useState(false);
  const menuRef = useRef(null);

  const toggleMenu = () => {
    setIsOpen(!isOpen);
  };

  useEffect(() => {
    const handleClickOutside = (event) => {
      if (menuRef.current && !menuRef.current.contains(event.target)) {
        setIsOpen(false);
      }
    };
    document.addEventListener('mousedown', handleClickOutside);
    return () => {
      document.removeEventListener('mousedown', handleClickOutside);
    };
  }, []);

  const handleLogoutClick = () => {
    setShowLogoutConfirmation(true);
  };

  const handleConfirmLogout = () => {
    alert('You have logged out!');
    setShowLogoutConfirmation(false);
  };

  const handleCancelLogout = () => {
    setShowLogoutConfirmation(false);
  };

  return (
    <div className="profile-menu-container" ref={menuRef}>
      <div className="profile-icon" onClick={toggleMenu}>
        <img src={userIcon} alt="User Icon" />
      </div>

      {isOpen && (
        <div className="dropdown-menu">
          <ul>
            <li>
              <Link to="/my-jobs">My Jobs</Link>
            </li>
            <li>
              <Link to="/cv-details">CV Details</Link>
            </li>
            <li className="update">
              <Link to="/update-profile">Update Profile</Link>
            </li>
            <li className="logout" onClick={handleLogoutClick}>
              Logout
            </li>
          </ul>
        </div>
      )}

      {showLogoutConfirmation && (
        <div className="logout-confirmation">
          <div className="confirmation-dialog">
            <p>Are you sure you want to log out?</p>
            <div className="confirmation-actions">
              <button className="confirm-btn" onClick={handleConfirmLogout}>Yes</button>
              <button className="cancel-btn" onClick={handleCancelLogout}>No</button>
            </div>
          </div>
        </div>
      )}
    </div>
  );
};

export default ProfileMenu;
