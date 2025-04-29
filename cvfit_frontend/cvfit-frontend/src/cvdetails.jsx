import React, { useState, useEffect } from 'react';
import plus from './assets/plus.png';
import right from './assets/check-mark.png';
import userIcon from './assets/user.png';
import cvimg from './assets/cv.webp';
import strengthsimg from './assets/strong.png';
import enhancementsimg from './assets/tool.png';
import ProfileMenu from './profile';
import "./cvdetails.css";
import axios from 'axios';
import { getDocument, GlobalWorkerOptions } from "pdfjs-dist";
import workerSrc from 'pdfjs-dist/build/pdf.worker?url';

GlobalWorkerOptions.workerSrc = workerSrc;
// Services API
export const getRolesByCvId = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/roles`);
    return response.data;
};

export const getUserCVs = async () => {
    const response = await axios.get('http://localhost:8080/profile/cvs', {
        withCredentials: true
    });
    return response.data;
};

export const getCvDetails = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/details`, { withCredentials: true });
    return response.data;
};

export const getJobsByCvId = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/jobs`, { withCredentials: true });
    return response.data;
};

const CVDetails = () => {
    const [selectedCvIndex, setSelectedCvIndex] = useState(0);
    const [activeTab, setActiveTab] = useState("details");
    const [roles, setRoles] = useState([]);
    const [cvs, setCvs] = useState([]);
    const [cvDetails, setCvDetails] = useState({});
    const [jobs, setJobs] = useState({});
    const [cvPreviews, setCvPreviews] = useState([]);

    const [imageSrc, setImageSrc] = useState(null);
    const [cvImages, setCvImages] = useState([]); // new: store images



   
    const pdfToImage = async (pdfBlob) => {
        const pdf = await pdfjsLib.getDocument({ data: await pdfBlob.arrayBuffer() }).promise;
        const page = await pdf.getPage(1); // first page
        const viewport = page.getViewport({ scale: 2 });
        const canvas = document.createElement('canvas');
        const context = canvas.getContext('2d');
        canvas.width = viewport.width;
        canvas.height = viewport.height;
      
        await page.render({ canvasContext: context, viewport }).promise;
      
        return canvas.toDataURL(); // returns a base64 image URL
      };
      const fetchAndConvertPdf = async (pdfUrl) => {
        const response = await fetch(pdfUrl);
        const blob = await response.blob();
        const imgDataUrl = await pdfToImage(blob);
        return imgDataUrl; // return the image
      };

      const fetchAllPdfImages = async () => {
        try {
          console.log("fetchAllPdfImages");
          const images = await Promise.all(
            cvs.map(async (cv) => {
              console.log(cv.pdfCv);
      
              // ✨ Fix the path here
              const cleanedFilename = cv.pdfCv
                .replace('uploads\\', '') // Windows slashes
                .replace('uploads/', ''); // Unix slashes (just in case)
      
              const pdfUrl = `http://localhost:8080/files/${cleanedFilename}`;
              const imgDataUrl = await fetchAndConvertPdf(pdfUrl);
              return { id: cv.cv_id, image: imgDataUrl }; // use cv_id if that's your real ID
            })
          );
          setCvImages(images);
        } catch (error) {
          console.error('Error fetching or converting multiple PDFs:', error);
        }
      };
      
      
    
      useEffect(() => {

          
          
        const fetchCVs = async () => {
          try {
            // const pdfUrl = 'http://localhost:8080/files/ss.pdf'; // replace dynamically if needed
            // fetchAndConvertPdf(pdfUrl);
            // console.log("pdfffffffffffff ::::" +imageSrc)
            const data = await getUserCVs();
            const cvsArray = Array.isArray(data) ? data : [];

            setCvs(cvsArray);
       
            const detailsData = {};
            const jobsData = {};
      
            for (const cv of cvsArray) {
              const [details, jobsList] = await Promise.all([
                getCvDetails(cv.cv_id),
                getJobsByCvId(cv.cv_id),
              ]);
      
              detailsData[cv.cv_id] = details;
              jobsData[cv.cv_id] = jobsList;
            }
      
            setCvDetails(detailsData);
            setJobs(jobsData);
      
            // 🛠 Important: use the correct property now: pdfCv
            // const previews = await renderAllPDFs(cvsArray.map(cv => cv.pdfCv));
            // console.log(previews)
            // setCvPreviews(previews);
      
            // console.log('Generated previews:', previews);
          } catch (error) {
            console.error('Erreur lors du chargement des données :', error);
          }
        };
      
        fetchCVs();


      }, []);
      

      useEffect(() => {
        console.log("Cvs updated:", cvs);
        if (cvs.length > 0) {
          fetchAllPdfImages(); // now it will work correctly
        }
      }, [cvs]);

    const selectedCv = cvs[selectedCvIndex];
    const selectedCvId = selectedCv?.cv_id;
    const selectedDetails = selectedCvId ? cvDetails[selectedCvId] : null;
    const selectedJobs = selectedCvId ? jobs[selectedCvId] : [];

    return (
        <div className="main-layout">
            <aside className="cv-sidebar">
                <h2 className="cv-title">Mes CV</h2>
                <div className="cv-thumbnails-vertical">
                {cvs.map((cv, idx) => {
                    const matchingImage = cvImages.find(img => img.id === cv.cv_id); // find the matching generated image
                    return (
                    <div key={cv.cv_id}>
                        {matchingImage ? (
                        <img
                            src={matchingImage.image} // use the dynamic image
                            alt={`CV ${cv.cv_id}`}
                            className={`cv-thumb-vertical ${selectedCvIndex === idx ? "selected" : ""}`}
                            onClick={() => setSelectedCvIndex(idx)}
                        />
                        ) : (
                        <div className="cv-thumb-vertical placeholder">
                            Loading...
                        </div> // if image not ready yet
                        )}
                    </div>
                    );
                })}
                </div>

            </aside>
            
            <div className="content-area">
                <div className="tabs-wrapper">
                    <div className="tabs">
                        <span className={activeTab === "details" ? "active-tab" : ""} onClick={() => setActiveTab("details")}>
                            CV Details
                        </span>
                        <span className={activeTab === "jobs" ? "active-tab" : ""} onClick={() => setActiveTab("jobs")}>
                            My Jobs
                        </span>
                    </div>
                    
                </div>
                <div className="profilebar">
                <ProfileMenu userIcon={userIcon} />
                </div>
                {activeTab === "details" && (
                    <div className="CVDetails_content">
                        <section className="cv-info">
                            {/* <p>Détails du CV sélectionné (CV {selectedCvIndex + 1})</p> */}

                            <div className="grid-container">
                                <div className="section">
                                    <img src={strengthsimg} alt="Strengths Icon" className="strengths-icon" />
                                    <h2 className="card-title">Strengths</h2>
                                    <div className="card strengths">
                                        <ul className="card-content">
                                            {(selectedDetails?.strengths?.split("%") || []).map((strength, idx) => (
                                                <li key={idx}>
                                                    <img src={right} alt="Check" className="icon" /> {strength}
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                </div>

                                <div className="section">
                                    <img src={enhancementsimg} alt="Enhancements Icon" className="enhancements-icon" />
                                    <h2 className="card-title">Enhancements</h2>
                                    <div className="card enhancements">
                                        <ul className="card-content">
                                            {(selectedDetails?.enhancements?.split("%") || []).map((enhancement, idx) => (
                                                <li key={idx}>
                                                    <img src={plus} alt="Plus" className="icon" /> {enhancement}
                                                </li>
                                            ))}
                                        </ul>
                                    </div>
                                </div>
                            </div>
                        </section>
                    </div>
                )}

                {activeTab === "jobs" && (
                    <div className="jobspage-profile">
                        {/* <div className="header-internal">
                            <h2>Emplois</h2>
                            <h2>Adaptés à votre CV</h2>
                        </div> */}
                        <div className="jobs-container-profile">
                            <div className="right-profile">
                                <div className="jobs-profile">
                                                                {(selectedJobs || []).length > 0 ? (
                                    (selectedJobs || []).map((job, index) => (
                                        <div className="job-profile" key={`${job.title}-${index}`}>
                                            <div className="job_infos-profile">
                                                <h2>{job.title}</h2>
                                            </div>
                                            <div className="button">
                                                <button onClick={() => window.open(job.link, '_blank')}>
                                                    Détails
                                                </button>
                                            </div>
                                        </div>
                                    ))
                                ) : (
                                    <p>No jobs are saved yet.</p>
                                )}

                                </div>
                            </div>
                        </div>
                    </div>
                )}
            </div>
        </div>
    );
};

export default CVDetails;
