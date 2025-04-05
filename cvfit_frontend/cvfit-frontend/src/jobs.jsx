import './App.css';
import cvimg from './assets/cv.webp';
import { useLocation } from 'react-router-dom'; // Import useLocation to access passed state

function Jobs() {
  // Get the jobs state passed from the previous page
  const location = useLocation();
  const jobs = location.state?.jobs || []; // Fallback to empty array if no jobs are passed

  return (
    <div className="jobspage">
      <div className="header">
        <h2>Emplois</h2>
        <h2>Adapetes a votre Cv</h2>
      </div>
      <div className="jobs-container">
        <div className="left">
          <img src={cvimg} alt="CV" />
        </div>
        <div className="right">
          <div className="jobs">
            {/* Map over the jobs array and render them */}
            {jobs.length > 0 ? (
              jobs.map((job, index) => (
                <div className="job" key={index}>
                  <div className="job_infos">
                    <h2>{job.title}</h2>
                    <p>{job.city}</p>

                  </div>
                  <div className="button">
                    <button onClick={() => window.open(job.link, '_blank')}>Details</button>
                  </div>
                </div>
              ))
            ) : (
              <p>No jobs available</p>
            )}
          </div>
        </div>
      </div>
    </div>
  );
}

export default Jobs;
