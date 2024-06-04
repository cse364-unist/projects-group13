import React, { useState } from "react";

const Feature1 = ({ isActive }) => {
    const [selectedGenres, setSelectedGenres] = useState([]);

    const handleGenreToggle = (genre) => {
        setSelectedGenres((prevGenres) => {
            if (prevGenres.includes(genre)) {
                return prevGenres.filter((g) => g !== genre);
            } else {
                return [...prevGenres, genre];
            }
        });
    };

    return (
        <section id="feature1" className={`mb-8 ${isActive ? "" : "hidden"}`}>
            <h2 className="text-2xl font-bold mb-4">Feature 1</h2>
            <div className="flex">
                <div className="w-1/3">
                    <h3 className="font-bold mb-2">Genre</h3>
                    <div className="border p-4">
                        <button
                            className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                                selectedGenres.includes("Action")
                                    ? "active"
                                    : ""
                            }`}
                            onClick={() => handleGenreToggle("Action")}
                        >
                            Action
                        </button>
                        <button
                            className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                                selectedGenres.includes("Animation")
                                    ? "active"
                                    : ""
                            }`}
                            onClick={() => handleGenreToggle("Animation")}
                        >
                            Animation
                        </button>
                        <button
                            className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                                selectedGenres.includes("Comedy")
                                    ? "active"
                                    : ""
                            }`}
                            onClick={() => handleGenreToggle("Comedy")}
                        >
                            Comedy
                        </button>
                        <button
                            className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                                selectedGenres.includes("Thriller")
                                    ? "active"
                                    : ""
                            }`}
                            onClick={() => handleGenreToggle("Thriller")}
                        >
                            Thriller
                        </button>
                    </div>
                </div>
                <div className="w-2/3 grid grid-cols-3 gap-4">
                    <div className="bg-gray-300 h-32"></div>
                    <div className="bg-gray-300 h-32"></div>
                    <div className="bg-gray-300 h-32"></div>
                    <div className="bg-gray-300 h-32"></div>
                    <div className="bg-gray-300 h-32"></div>
                    <div className="bg-gray-300 h-32"></div>
                </div>
            </div>
        </section>
    );
};

export default Feature1;
