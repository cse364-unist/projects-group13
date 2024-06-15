import React, { useState } from "react";
import { useNavigate } from "react-router-dom";

const Feature2 = () => {
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [choices, setChoices] = useState([]);

  const handleGenreToggle = (genre) => {
    setSelectedGenres((prevGenres) => {
      if (prevGenres.includes(genre)) {
        return prevGenres.filter((g) => g !== genre);
      } else {
        return [...prevGenres, genre];
      }
    });
  };

  const navigate = useNavigate();

  const handleSearchTermChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleAddChoice = () => {
    setChoices((prevChoices) => [...prevChoices, searchTerm]);
    setSearchTerm("");
  };

  const handleShowActorScreen = () => {
    navigate("/gbar/result");
  };

  return (
    <div className="min-h-screen bg-gray-100 text-gray-900 font-roboto">
      <main className="container mx-auto py-8 px-4">
        <section id="feature2" className={"mb-8"}>
          <h2 className="text-2xl font-bold mb-4">Feature 2</h2>
          <div className="flex">
            <div className="w-1/3">
              <h3 className="font-bold mb-2">Genre</h3>
              <div className="border p-4">
                <button
                  className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                    selectedGenres.includes("Action") ? "active" : ""
                  }`}
                  onClick={() => handleGenreToggle("Action")}>
                  Action
                </button>
                <button
                  className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                    selectedGenres.includes("Animation") ? "active" : ""
                  }`}
                  onClick={() => handleGenreToggle("Animation")}>
                  Animation
                </button>
                <button
                  className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                    selectedGenres.includes("Thriller") ? "active" : ""
                  }`}
                  onClick={() => handleGenreToggle("Thriller")}>
                  Thriller
                </button>
                <button
                  className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                    selectedGenres.includes("Comedy") ? "active" : ""
                  }`}
                  onClick={() => handleGenreToggle("Comedy")}>
                  Comedy
                </button>
                <button
                  className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                    selectedGenres.includes("More") ? "active" : ""
                  }`}
                  onClick={() => handleGenreToggle("More")}>
                  More
                </button>
              </div>
            </div>
            <div className="w-2/3">
              <div className="flex mb-4">
                <input
                  type="text"
                  className="border p-2 flex-grow"
                  placeholder="Search for actors..."
                  value={searchTerm}
                  onChange={handleSearchTermChange}
                />
                <button
                  className="bg-blue-500 text-white py-2 px-4 ml-2"
                  onClick={handleAddChoice}>
                  Add
                </button>
              </div>
              <div className="border p-4 mb-4">
                <p>Choice List</p>
                {choices.map((choice, index) => (
                  <p key={index}>{choice}</p>
                ))}
              </div>
              <button
                className="bg-blue-500 text-white py-2 px-4"
                onClick={handleShowActorScreen}>
                Show me!
              </button>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default Feature2;