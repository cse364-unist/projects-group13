import React from "react";

const Navigation = ({ onFeatureChange }) => {
    const handleFeatureClick = (feature) => {
        onFeatureChange(feature);
    };

    return (
        <nav className="bg-gray-200 py-4">
            <div className="container mx-auto flex justify-center space-x-4">
                <button
                    className="feature-button bg-white py-2 px-4 rounded shadow"
                    onClick={() => handleFeatureClick("feature1")}
                >
                    Feature 1
                </button>
                <button
                    className="feature-button bg-white py-2 px-4 rounded shadow"
                    onClick={() => handleFeatureClick("feature2")}
                >
                    Feature 2
                </button>
                <button
                    className="feature-button bg-white py-2 px-4 rounded shadow"
                    onClick={() => handleFeatureClick("feature3")}
                >
                    Feature 3
                </button>
            </div>
        </nav>
    );
};

export default Navigation;
