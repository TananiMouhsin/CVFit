import React from 'react';
import { BrowserRouter as Router, Routes, Route } from 'react-router-dom';
import CvDetails from './cvdetails';
import UpdateProfile from './UpdateProfile'; // Importe la nouvelle page
import ProfileMenu from './profile'; // Si tu veux utiliser le menu

function App() {

  return (
    <Router>
      <Routes>
        {/* Page d'accueil avec le menu et les détails du CV */}
        <Route
          path="/"
          element={
            <>
              <CvDetails />
              
            </>
          }
        />

        {/* Page pour mettre à jour le profil */}
        <Route path="/update-profile" element={<UpdateProfile />} />
        
      </Routes>
    </Router>
  );
}

export default App;
