import React, { useState } from "react";
import { Link } from "react-router-dom";
import "./Home.css";

const Home = () => {
  const [file, setFile] = useState(null);
  const [error, setError] = useState("");

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
      setError(""); // Réinitialiser l'erreur si le bon fichier est déposé
    } else {
      setFile(null);
      setError("Seuls les fichiers PDF sont autorisés !");
    }
  };

  const handleDragOver = (event) => {
    event.preventDefault();
  };

  return (
    <div className="cvfit-container">
      {/* Navigation Buttons */}
      <div className="auth-buttons">
        {/* <Link to="/signup"> */}
          {/* <button className="btn signup">Sign Up</button> */}
        {/* </Link> */}
        {/* <Link to="/login"> */}
          {/* <button className="btn login">Login</button> */}
        {/* </Link> */}
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
        <button className="CVbutton" disabled={!file}>
          Évaluer mon CV
        </button>
        <button className="CVbutton">Trouver un emploi</button>
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
