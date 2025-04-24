import React, { useState, useEffect } from 'react';
import plus from './assets/plus.png';
import right from './assets/check-mark.png';
import userIcon from './assets/user.png';
import cvimg from './assets/cv.webp';
import strenghtsimg from './assets/strong.png';
import enhancementsimg from './assets/tool.png';
import ProfileMenu from './profile';
import "./cvdetails.css";
import axios from 'axios';

// Services API
export const getRolesByCvId = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/roles`);
    return response.data;
};

export const getUserCVs = async (userId) => {
    const response = await axios.get(`http://localhost:8080/profile/cvs/${userId}`);
    return response.data;
};

export const getCvDetails = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/details`);
    return response.data;
};

export const getJobsByCvId = async (cvId) => {
    const response = await axios.get(`http://localhost:8080/profile/cv/${cvId}/jobs`);
    return response.data;
};

const CVDetails = () => {
    const [selectedCv, setSelectedCv] = useState(0);
    const [activeTab, setActiveTab] = useState("details");
    const [roles, setRoles] = useState([]);
    const [cvs, setCvs] = useState([]);
    const [cvDetails, setCvDetails] = useState({ strengths: [], enhancements: [] });
    const [jobs, setJobs] = useState([]);

    useEffect(() => {
        const fetchCVs = async () => {
            try {
                const userId = 1;
                const data = await getUserCVs(userId);
                setCvs(Array.isArray(data) ? data : []);
            } catch (error) {
                console.error("Erreur lors du chargement des CVs :", error);
            }
        };
        fetchCVs();
    }, []);

    useEffect(() => {
        const cvId = selectedCv + 1;

        const fetchAll = async () => {
            try {
                const [fetchedRoles, fetchedDetails, fetchedJobs] = await Promise.all([
                    getRolesByCvId(cvId),
                    getCvDetails(cvId),
                    getJobsByCvId(cvId),
                ]);

                const strengths = fetchedDetails.strengths?.split(',').map(s => s.trim()) || [];
                const enhancements = fetchedDetails.enhancements?.split(',').map(e => e.trim()) || [];

                setRoles(fetchedRoles);
                setCvDetails({ strengths, enhancements });
                setJobs(Array.isArray(fetchedJobs) ? fetchedJobs : []);
            } catch (error) {
                console.error("Erreur lors du chargement des données :", error);
                setRoles([]);
                setCvDetails({ strengths: [], enhancements: [] });
                setJobs([]);
            }
        };

        fetchAll();
    }, [selectedCv]);

    return (
        <div className="main-layout">
            <aside className="cv-sidebar">
                <h2 className="cv-title">Mes CV</h2>
                <div className="cv-thumbnails-vertical">
                    {cvs.map((cv, idx) => (
                        <img
                            key={cv.cvId}
                            src={cvimg}
                            alt={`CV ${cv.cvId}`}
                            className={`cv-thumb-vertical ${selectedCv === idx ? "selected" : ""}`}
                            onClick={() => setSelectedCv(idx)}
                        />
                    ))}
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
                    <ProfileMenu userIcon={userIcon} />
                </div>

                {activeTab === "details" && (
                    <div className="CVDetails_content">
                        <section className="cv-info">
                            <p>Détails du CV sélectionné (CV {selectedCv + 1})</p>

                            <div className="roles-section">
                                <h3>My Roles</h3>
                                <div className="roles-box">
                                    <ul>
                                        {Array.isArray(roles) ? (
                                            roles.map((role) => (
                                                <li key={role.roleId}>{role.title}</li>
                                            ))
                                        ) : (
                                            <li>Aucun rôle trouvé.</li>
                                        )}
                                    </ul>
                                </div>
                            </div>

                            <div className="grid-container">
                                <div className="section">
                                    <img src={strenghtsimg} alt="Strengths Icon" className="strenghts-icon" />
                                    <h2 className="card-title">Strengths</h2>
                                    <div className="card strengths">
                                        <ul className="card-content">
                                            {cvDetails.strengths.map((item, i) => (
                                                <li key={i}>
                                                    <img src={right} alt="Check" className="icon" /> {item}
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
                                            {cvDetails.enhancements.map((item, i) => (
                                                <li key={i}>
                                                    <img src={plus} alt="Plus" className="icon" /> {item}
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
                    <div className="jobspage">
                        <div className="header-internal">
                            <h2>Emplois</h2>
                            <h2>Adaptés à votre CV</h2>
                        </div>
                        <div className="jobs-container">
                            <div className="left">
                                <img src={cvimg} alt="CV" />
                            </div>
                            <div className="right">
                                <div className="jobs">
                                    {jobs.length > 0 ? (
                                        jobs.map((job, index) => (
                                            <div className="job" key={index}>
                                                <div className="job_infos">
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
                                        <p>Aucun emploi disponible.</p>
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
