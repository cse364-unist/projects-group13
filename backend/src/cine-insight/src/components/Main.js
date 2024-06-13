import React from "react";
import Feature1 from "./feature1/feature1";
import Feature2 from "./feature2/feature2";
import Feature3 from "./feature3/feature3";

const Main = ({ activeFeature }) => {
    return (
        <main className="container mx-auto py-8 px-4">
            <section
                id="default"
                className={`mb-8 ${
                    activeFeature === "default" ? "" : "hidden"
                }`}
            >
                <h2 className="text-2xl font-bold mb-4">
                    Welcome to Cine Insight
                </h2>
                <p>Select a feature to get started.</p>
            </section>
            <Feature1 isActive={activeFeature === "feature1"} />
            <Feature2 isActive={activeFeature === "feature2"} />
            <Feature3 isActive={activeFeature === "feature3"} />
        </main>
    );
};

export default Main;
