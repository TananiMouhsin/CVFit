import './App.css'
import cvimg from './assets/cv.webp'

function Jobs() {

  return (

    <div className="jobspage">
    <div className="header">
    <h2>Emplois</h2>
    <h2>Adapetes a votre Cv</h2>
    </div>
    <div className="container">
        <div className="left">
        <img src={cvimg} alt="" />
        </div>
        <div className="right">

            <div className="jobs">
                <div className="job">
                  <div className="job_infos">
                    <h2>Offre de stage en development web </h2>
                    <p>Maroc-oriental group</p>
                    <p>Marrakech</p>
                  </div>
                  <div className="button">
                    <button>Details</button>
                  </div>

                </div>

                <div className="job">
                  <div className="job_infos">
                    <h2>Offre de stage en development web </h2>
                    <p>Maroc-oriental group</p>
                    <p>Marrakech</p>
                  </div>
                  <div className="button">
                    <button>Details</button>
                  </div>

                </div>

                <div className="job">
                  <div className="job_infos">
                    <h2>Offre de stage en development web </h2>
                    <p>Maroc-oriental group</p>
                    <p>Marrakech</p>
                  </div>
                  <div className="button">
                    <button>Details</button>
                  </div>

                </div>

                <div className="job">
                  <div className="job_infos">
                    <h2>Offre de stage en development web </h2>
                    <p>Maroc-oriental group</p>
                    <p>Marrakech</p>
                  </div>
                  <div className="button">
                    <button>Details</button>
                  </div>

                </div>

                <div className="job">
                  <div className="job_infos">
                    <h2>Offre de stage en development web </h2>
                    <p>Maroc-oriental group</p>
                    <p>Marrakech</p>
                  </div>
                  <div className="button">
                    <button>Details</button>
                </div>

                

                  
                  

                </div>
            </div>
        </div>

    </div>

    </div>

  )
}

export default Jobs
