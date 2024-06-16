import React from "react";
import { useNavigate, useLocation } from "react-router-dom";

const Navigation = ({ onFeatureChange }) => {
  const navigate = useNavigate();
  const location = useLocation();

  const handleFeatureClick = (feature) => {
    navigate(`/${feature}`);
  };

  const buttonStyle =
    "py-2 px-4 rounded border-2 transition-all duration-300 hover:shadow-xl hover:shadow-slate-300";

  return (
    <nav className="bg-gray-200 py-4">
      <div className="container mx-auto flex justify-center space-x-4">
        <button
          className={`${buttonStyle} ${location.pathname.includes("/pua") ? "bg-gray-400 border-gray-500 text-white" : "bg-white border-white text-gray-800"}`}
          onClick={() => handleFeatureClick("pua")}>
          Feature 1
        </button>
        <button
          className={`${buttonStyle} ${location.pathname.includes("/gbar") ? "bg-gray-400 border-gray-500 text-white" : "bg-white border-white text-gray-800"}`}
          onClick={() => handleFeatureClick("gbar")}>
          Feature 2
        </button>
        <button
          className={`${buttonStyle} ${location.pathname.includes("/gfa") ? "bg-gray-400 border-gray-500 text-white" : "bg-white border-white text-gray-800"}`}
          onClick={() => handleFeatureClick("gfa")}>
          Feature 3
        </button>
      </div>
    </nav>
  );
};

export default Navigation;
