  import { useState } from 'react'
  import reactLogo from './assets/react.svg'
  import viteLogo from '/vite.svg'
  import './App.css'
  import Jobs from "./jobs.jsx"

  function App() {
    const [count, setCount] = useState(0)

    return (
      <>
        <Jobs/>
      </>
    )
  }

  export default App
