import React from 'react';

import { BrowserRouter as Router, Route, Routes } from 'react-router-dom';

  import './index.css'
  import Jobs from "./jobs.jsx"
  import Login from "./login.jsx"
  import SignUp from "./Signup.jsx"
  import Home from "./Home.jsx"
  import Details from "./Details.jsx"

  
  function App() {


    return (
      <Router>
      <Routes>
        <Route path="/" element={
          <>
          <Home/>
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
      </Routes>
    </Router>
    )
  }

  export default App
