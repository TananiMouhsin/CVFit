import React from 'react';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

  import './index.css'
  import Jobs from "./jobs.jsx"
  import Login from "./login.jsx"
  import SignUp from "./Signup.jsx"
  import Home from "./Home.jsx"
  import Details from "./Details.jsx"
  import ProfileMenu from "./profile.jsx"
  import CvDetails from './cvdetails.jsx';
  import UpdateProfile from './UpdateProfile.jsx';
  
  function App() {


    return (
      <Router>
      <Routes>
        <Route path="/home" element={
          <>
          <Home/>
          </>
        } />

        <Route path="/login" element={
          <>
          <Login/>
          </>
        } />

        <Route path="/signup" element={
          <>
          <SignUp/>
          </>
        } />
        <Route path="/details" element={<>
        <Details/>
        </>
        } />

        <Route path="/Jobs" element={<>
        <Jobs/>
        </>
        } />

        
        <Route path="/Profile" element={<>
        <CvDetails/>
        </>
        } />

        <Route path="/update-profile" element={<>
        <UpdateProfile/>
        </>
        } />
      </Routes>
    </Router>
    )
  }

  export default App
