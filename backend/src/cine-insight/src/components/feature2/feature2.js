import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import Feature2GenreSelection from "./feature2GenreSelection";

const genres = [
  "Drama",
  "Adventure",
  "Action",
  "Comedy",
  "Horror",
  "Biography",
  "Crime",
  "Fantasy",
  "Family",
  "Sci-Fi",
  "Animation",
  "Romance",
  "Music",
  "Western",
  "Thriller",
  "History",
  "Mystery",
  "Sport",
  "Musical",
];

const Feature2 = () => {
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [searchTerm, setSearchTerm] = useState("");
  const [choices, setChoices] = useState([]);
  const [loading, setLoading] = useState(false);

  const navigate = useNavigate();

  const handleSearchTermChange = (event) => {
    setSearchTerm(event.target.value);
  };

  const handleAddChoice = async (e) => {
    e.preventDefault();

    const url = `http://localhost:8080/gbar/find?name=${searchTerm}`;

    try {
      const res = await fetch(url, {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      });

      if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
      }

      setChoices((prevChoices) => [...prevChoices, searchTerm]);
    } catch (error) {
      console.error("Error adding actor:", error);
      alert(
        `"Failed to find actor that name is ${searchTerm}. Please try again with another name.`
      );
    }
    setSearchTerm("");
  };

  const handleResult = async (e) => {
    e.preventDefault();

    let genrekeys = [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0];

    selectedGenres.forEach(function (genre) {
      genrekeys[genres.findIndex((name) => name === genre)] = 1;
    });

    const url = `http://localhost:8080/gbar/recommend `;
    const data = {
      genre: genrekeys,
      supporter: choices, //exmaple ["Robert Hays", "John Belushi"]
      synergy: 20,
      plot: "",
    };

    try {
      const res = await fetch(url, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify(data),
      });

      if (!res.ok) {
        throw new Error(`HTTP error! status: ${res.status}`);
      }

      const response = await res.json();

      const dataToSend = { result: response, genres: genres };
      navigate("/gbar/result", { state: dataToSend });
    } catch (error) {
      console.error("Error fetching analysis data:", error);
      alert("Failed to fetch analysis data. Please try again later.");
    }
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
                <Feature2GenreSelection
                  value={selectedGenres}
                  setValue={setSelectedGenres}
                  genres={genres}
                />
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
                onClick={handleResult}>
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
