import React from "react";
import cvimg from './assets/cv.webp'
import inhancmentimg from './assets/tool.png'
import strenghtsimg from './assets/strong.png'
import plus from './assets/plus.png'
import right from './assets/check-mark.png'

import "./details.css";

const Details = () => {
    return (
        <div className="container"> 
            {/* <h1 className="title">CvFit</h1> */}

            {/* Image principale en haut */}
            <div className="cv-picture">
                <img src={cvimg} alt="details" className="details-image" />
            </div>

            <div className="grid-container">
               
                <div className="section">
                    <img src={strenghtsimg} alt="strengths icon" className="strenghts-icon" />
                    <h2 className="card-title"> Strengths</h2>
                    <div className="card strengths">
                        <ul className="card-content">
                            <li><img src={right} alt="" /> Strong educational background in computer engineering with a focus on Big Data, Data Science, and AI.</li>
                            <li><img src={right} alt="" /> Diverse project experience in web and mobile development, showcasing practical application of skills.</li>
                            <li><img src={right} alt="" /> Relevant internships demonstrating hands-on experience in software development and project management.</li>
                            <li><img src={right} alt="" /> Strong educational background in computer engineering with a focus on Big Data, Data Science, and AI.</li>

                        </ul>
                    </div>
                </div>

                {/* Section Enhancements */}
                <div className="section">
                    <img src={inhancmentimg} alt="enhancements icon" className="enhacements-icon" />
                    <h2 className="card-title"> Enhancements</h2>
                    <div className="card enhancements">
                    <ul className="card-content">
    <li><img src={plus} alt="" /> Streamline the resume layout with clear section headings and bullet points for easy readability.</li>
    <li><img src={plus} alt="" /> Include quantifiable achievements and results for each project and internship to showcase impact and skills.</li>
    <li><img src={plus} alt="" /> Add a section for certifications, achievements, or extracurricular activities to highlight standout qualifications.</li>
    <li><img src={plus} alt="" /> Streamline the resume layout with clear section headings and bullet points for easy readability.</li>

</ul>

                    </div>
                </div>
            </div>
        </div>
    );
};

export default Details;
