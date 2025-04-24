import React, { useState } from "react";

import { Link } from "react-router-dom";
import "./Home.css";
import { useNavigate } from "react-router-dom";
const Home = () => {
  const navigate = useNavigate();
  const [file, setFile] = useState(null);
  const [error, setError] = useState("");
  const [Strengths, setStrengths] = useState("");
  const [Suggestions, setSuggestions] = useState("");

  const uploadCV = async () => {
    console.log("Uploading CV..."); 
    if (!file) {
        alert("Veuillez sélectionner un fichier avant d'envoyer !");
        return;
    } 

    const formData = new FormData();
    formData.append("file", file);

    try {
        const response = await fetch("http://localhost:8080/CVReview/CV", {
            method: "POST",
            body: formData,
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
        navigate("/details", { state: { strengths, suggestions } });

    } catch (error) {
        console.error("Erreur:", error);
    }
};


const GetJobs = async () => {
  console.log("Uploading CV..."); 
  if (!file) {
      alert("Veuillez sélectionner un fichier avant d'envoyer !");
      return;
  } 

  const formData = new FormData();
  formData.append("file", file);

  try {
      const response = await fetch("http://localhost:8080/CVRoles/Scrap", {
          method: "POST",
          body: formData,
      });

      if (!response.ok) {
          throw new Error("Erreur lors de l'analyse du CV.");
      }

      const data = await response.json();
      console.log("Response Data:", data);

      // Assuming data is an array of job objects with City, Link, Title
      const jobs = data.map(item => ({
          city: item.City,
          link: item.Link,
          title: item.Title
      }));

      console.log("List of Jobs:", jobs);

      // If using React, you can save it in a state (if needed)
      // setJobs(jobs); // Assuming you have a `jobs` state to store the jobs.

      // If you want to navigate to another page and pass the list of jobs:
      navigate("/Jobs", { state: { jobs } });

  } catch (error) {
      console.error("Erreur:", error);
  }
};

  

  const handleFileChange = (event) => {
    const selectedFile = event.target.files[0];
    if (selectedFile && selectedFile.type === "application/pdf") {
      setFile(selectedFile);
      setError("");
    } else {
      setFile(null);
      setError("Seuls les fichiers PDF sont autorisés !");
    }
  };

  const handleDrop = (event) => {
    event.preventDefault();
    const droppedFile = event.dataTransfer.files[0];

    if (droppedFile && droppedFile.type === "application/pdf") {
      setFile(droppedFile);
      setError("");
    } else {
      setFile(null);
      setError("Seuls les fichiers PDF sont autorisés !");
    }
  };

  const handleDragOver = (event) => {
    event.preventDefault();
  };

  return (
    <div className="home-container">

      <div className="auth-buttons">

      </div>

      <header className="cvfit-header">
        <h1 className="cvfit-title">CvFit</h1>
        <h2 className="cvfit-subtitle">
          JobFit AI : Analyse de CV par IA & Emplois Personnalisés
        </h2>
      </header>

      <p className="cvfit-description">
        Optimisez votre CV avec une évaluation gratuite par IA et découvrez des offres
        d'emploi adaptées à votre profil. Conseils instantanés et pertinents.
      </p>

     
      <div
        className="upload-section"
        onDrop={handleDrop}
        onDragOver={handleDragOver}
      >
        <label className="upload-box">
          <input
            type="file"
            accept=".pdf"
            onChange={handleFileChange}
            style={{ display: "none" }}
          />
          +
        </label>
        <p>{file ? `Fichier sélectionné : ${file.name}` : "Sélectionner ou déposer un fichier PDF"}</p>
        {error && <p className="error-message">{error}</p>}
      </div>

      {/* Buttons */}
      <div className="buttons">
        <button className="CVbutton" onClick={uploadCV} disabled={!file}>
          Évaluer mon CV
        </button>
        <button className="CVbutton" onClick={GetJobs}>Trouver un emploi</button>
      </div>

      <p className="cvfit-result-text">
        Grâce à notre évaluation de CV, vous obtiendrez :
      </p>

      {/* Features Section */}
      <div className="features">
        <div className="feature-box box1">
          <h3>1. Analyse Complète</h3>
          <p>
            Une évaluation approfondie de votre CV identifiant les points forts,
            les réussites et les zones à améliorer.
          </p>
        </div>
        <div className="feature-box box2">
          <h3>2. Feedback Personnalisé</h3>
          <p>
            Des conseils détaillés sur la structure, la clarté et la pertinence de
            votre CV pour maximiser son impact.
          </p>
        </div>
        <div className="feature-box box3">
          <h3>3. Suggestions d'Emploi</h3>
          <p>
            Offres d'emploi personnalisées, correspondant à votre éducation et à
            vos compétences, pour cibler les meilleures opportunités.
          </p>
        </div>
      </div>
    </div>
  );
};

export default Home;
