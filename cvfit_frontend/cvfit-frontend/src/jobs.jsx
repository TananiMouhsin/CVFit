import './App.css';
import cvimg from './assets/cv.webp';
import whiteSaveIcon from './assets/unsavedicon.png';  // your white icon
import blackSaveIcon from './assets/savedicon.png';  // your black icon
import { useLocation } from 'react-router-dom';
import { useState } from 'react';
import { useEffect} from "react";
import { getDocument, GlobalWorkerOptions } from "pdfjs-dist";
import workerSrc from 'pdfjs-dist/build/pdf.worker?url';

GlobalWorkerOptions.workerSrc = workerSrc;

function Jobs() {
  const location = useLocation();
  const {jobs,file} = location.state || [];
  const [cvPreview, setCvPreview] = useState(null);
  // Store which jobs are saved in state
  const [savedJobs, setSavedJobs] = useState({});

  useEffect(() => {
          const renderPDF = async () => {
              if (!file) return;
      
              const arrayBuffer = await file.arrayBuffer();
              const loadingTask = getDocument({ data: arrayBuffer });
              const pdf = await loadingTask.promise;
              const page = await pdf.getPage(1);
      
              const viewport = page.getViewport({ scale: 2 });
              const canvas = document.createElement('canvas');
              const context = canvas.getContext('2d');
              canvas.width = viewport.width;
              canvas.height = viewport.height;
      
              const renderContext = {
                  canvasContext: context,
                  viewport: viewport,
              };
      
              await page.render(renderContext).promise;
              const imgData = canvas.toDataURL('image/png');
              setCvPreview(imgData);
          };
      
          renderPDF();
      }, [file]);

  const handleToggleSave = async (title, link) => {
    const isSaved = savedJobs[title];
    const cvName = file.name
    console.log(cvName)
    
    try {
      if (isSaved) {
        // DELETE request to unsave
        const url = new URL('http://localhost:8080/job-offers/delete');  // Use the actual URL
        url.searchParams.append('cvTitle', cvName);
        url.searchParams.append('jobTitle', title);
        console.log(url)
        
        // Send the DELETE request
        await fetch(url, {
          method: 'DELETE',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          credentials: "include",
          // No body needed, since parameters are in the URL
        });
        alert('Offre supprimée des favoris');
      } else {
        // POST request to save
        await fetch('http://localhost:8080/job-offers/save', {
          method: 'POST',
          headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
          },
          body: new URLSearchParams({ title, link,cvName}),
          credentials: "include",
        });

      }

      // Toggle state
      setSavedJobs(prev => ({ ...prev, [title]: !isSaved }));
    } catch (error) {
      alert('Erreur : ' + error.message);
    }
  };

  return (
    <div className="jobspage">
      <div className="Jobs-header">
        <h2>Emplois</h2>
        <h2>Adaptées à votre CV</h2>
      </div>
      <div className="jobs-container">
        <div className="left">
          <img src={cvPreview} alt="CV" />
        </div>
        <div className="right">
          <div className="jobs">
            {jobs.length > 0 ? (
              jobs.map((job, index) => (
                <div className="job" key={index}>
                  <div className="job_infos">
                    <h2>{job.title}</h2>
                    <p>{job.city}</p>
                  </div>
                  <div className="button">
                    <button onClick={() => window.open(job.link, '_blank')}>Details</button>
                    <img
                      src={savedJobs[job.title] ? blackSaveIcon : whiteSaveIcon}
                      alt="Save job"
                      onClick={() => handleToggleSave(job.title, job.link)}
                      style={{
                        width: '24px',
                        height: '24px',
                        marginLeft: '10px',
                        cursor: 'pointer',
                      }}
                    />
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
