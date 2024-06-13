import React, { useState } from "react";
import Header from "./components/Header";
import Navigation from "./components/Navigation";
import Main from "./components/Main";
import "./index.css";

function App() {
    const [activeFeature, setActiveFeature] = useState("default");

    const handleFeatureChange = (feature) => {
        setActiveFeature(feature);
    };

    return (
        <div className="bg-gray-100 text-gray-900 font-roboto">
            <Header />
            <Navigation onFeatureChange={handleFeatureChange} />
            <Main activeFeature={activeFeature} />
        </div>
    );
}

export default App;
