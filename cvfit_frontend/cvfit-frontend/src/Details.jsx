import React from "react";
import { useLocation } from "react-router-dom";
import cvimg from './assets/cv.webp';
import inhancmentimg from './assets/tool.png';
import strenghtsimg from './assets/strong.png';
import plus from './assets/plus.png';
import right from './assets/check-mark.png';

import "./details.css";
import { useEffect, useState } from "react";
import { getDocument, GlobalWorkerOptions } from "pdfjs-dist";
import workerSrc from 'pdfjs-dist/build/pdf.worker?url';

GlobalWorkerOptions.workerSrc = workerSrc;




const Details = () => {
    const location = useLocation();
    const { strengths = [], suggestions = [], file } = location.state || {};
    const [cvPreview, setCvPreview] = useState(null);

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
    
    const uploadCV = async () => {
        console.log("Uploading CV infos "); 
        const Strenghts = strengths.join('%');
        const Suggestions = suggestions.join('%');
        console.log(Strenghts)
        console.log(Suggestions)
        if (!file) {
            alert("Veuillez sélectionner un fichier avant d'envoyer !");
            return ;
        } 
    
        const formData = new FormData();
        formData.append("file", file);
        formData.append("strengths", Strenghts);
        formData.append("enhancements", Suggestions);
        
        
        try {
            const response = await fetch("http://localhost:8080/cv/upload", {
                method: "POST",
                body: formData,
                credentials: "include",
            });
    
            if (!response.ok) {
                throw new Error("Erreur lors de l'analyse du CV.");
            }
    
            const data = await response.json();
            console.log("Response Data:", data);
    
            // Extract strengths & suggestions
            const strengths = data.strengths || [];
            const suggestions = data.suggestions || [];
    
            // Find the minimum length between the two arrays
            const minLength = Math.min(strengths.length, suggestions.length);
    
            // // Pad the shorter array with empty strings (or any placeholder value)
            // if (strengths.length < minLength) {
            //     strengths.push(...Array(minLength - strengths.length).fill(''));
            // }
            // if (suggestions.length < minLength) {
            //     suggestions.push(...Array(minLength - suggestions.length).fill(''));
            // } 
    
            console.log("Strengths:", strengths);
            console.log("Suggestions:", suggestions);
    
            // Navigate with the padded arrays
            navigate("/details", { state: { strengths, suggestions, file } });
    
    
        } catch (error) {
            console.error("Erreur:", error);
        }
    };

    return (
        <div className="CVDetails_container">
    <div className="cv-picture">
        <img src={cvPreview} alt="details" className="details-image" />

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

                            <li>Streamline the resume layout with clear section headings and bullet points fror easy readability.</li>

                            <li>Streamline the resume layout with clear section headings and bullet points for easy readability.</li> */}


                            {suggestions.map((suggestion, index) => (
                                <li key={index}><img src={plus} alt="" /> {suggestion}</li>
                            ))}
                        </ul>
                    </div>
                </div>
            </div>

            <button className="save_cv_details" onClick={uploadCV}>Save CV</button>
        </div>
    );
};

export default Details;
