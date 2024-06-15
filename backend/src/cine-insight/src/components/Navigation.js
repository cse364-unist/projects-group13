import React from "react";
import { useNavigate } from "react-router-dom";

const Navigation = ({ onFeatureChange }) => {
  const navigate = useNavigate();

  const handleFeatureClick = (feature) => {
    navigate(`/${feature}`);
  };

  return (
    <nav className="bg-gray-200 py-4">
      <div className="container mx-auto flex justify-center space-x-4">
        <button
          className="feature-button bg-white py-2 px-4 rounded shadow"
          onClick={() => handleFeatureClick("pua")}>
          Feature 1
        </button>
        <button
          className="feature-button bg-white py-2 px-4 rounded shadow"
          onClick={() => handleFeatureClick("gbar")}>
          Feature 2
        </button>
        <button
          className="feature-button bg-white py-2 px-4 rounded shadow"
          onClick={() => handleFeatureClick("gfl")}>
          Feature 3
        </button>
      </div>
    </nav>
  );
};

export default Navigation;
