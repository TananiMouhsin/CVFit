import React from "react";
import { useLocation } from "react-router-dom";
import cvimg from './assets/cv.webp';
import inhancmentimg from './assets/tool.png';
import strenghtsimg from './assets/strong.png';
import plus from './assets/plus.png';
import right from './assets/check-mark.png';

import "./details.css";

const Details = () => {
    const location = useLocation();
    const { strengths = [], suggestions = [] } = location.state || {};

    return (
        <div className="CVDetails_container">
            <div className="cv-picture">
                <img src={cvimg} alt="details" className="details-image" />
            </div>

            <div className="grid-container">
                <div className="section">
                    <img src={strenghtsimg} alt="strengths icon" className="strenghts-icon" />
                    <h2 className="card-title"> Strengths</h2>
                    <div className="card strengths">
                        <ul className="card-content">
                            {/* <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li>
                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li>

                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li> */}


                            {strengths.map((strength, index) => (
                                <li key={index}><img src={right} alt="" /> {strength}</li>
                            ))}
                        </ul>
                    </div>
                </div>

                <div className="section">
                    <img src={inhancmentimg} alt="enhancements icon" className="enhacements-icon" />
                    <h2 className="card-title"> Enhancements</h2>
                    <div className="card enhancements">
                        <ul className="card-content">
                        {/* <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li>
                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li>

                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li>

                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li> */}


                            {suggestions.map((suggestion, index) => (
                                <li key={index}><img src={plus} alt="" /> {suggestion}</li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default Details;
