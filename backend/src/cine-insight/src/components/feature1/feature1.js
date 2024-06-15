import React, { useState } from "react";

const Feature1 = () => {
  const [selectedGenres, setSelectedGenres] = useState([]);
  const [showAnalysis, setShowAnalysis] = useState(false);
  const [analysisData, setAnalysisData] = useState(null);
  const [loading, setLoading] = useState(false);

  const handleGenreToggle = (genre) => {
    setSelectedGenres((prevGenres) => {
      if (prevGenres.includes(genre)) {
        return prevGenres.filter((g) => g !== genre);
      } else {
        return [...prevGenres, genre];
      }
    });
  };

  const handleAnalysis = async (e) => {
    e.preventDefault();

    if (selectedGenres.length === 0) {
      alert("Please select at least one genre.");
      return;
    }

    setLoading(true);
    const genresQuery = selectedGenres.join(",");
    const url = `http://localhost:8080/pua/rating-analysis?genres=${genresQuery}`;

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

      const data = await res.json();
      setAnalysisData(data);
      setShowAnalysis(true);
    } catch (error) {
      console.error("Error fetching analysis data:", error);
      alert("Failed to fetch analysis data. Please try again later.");
    } finally {
      setLoading(false);
    }
  };

  const genres = [
    "Action", "Adventure", "Animation", "Children's", "Comedy", "Crime",
    "Documentary", "Drama", "Fantasy", "Film-Noir", "Horror", "Musical",
    "Mystery", "Romance", "Sci-Fi", "Thriller", "War", "Western"
  ];

  return (
      <div className="bg-gray-100 text-gray-900 font-roboto">
        <main className="container mx-auto py-8 px-4">
          <section id="feature1" className="mb-8">
            <h2 className="text-2xl font-bold mb-4">Feature 1</h2>
            <div>
              <h3 className="font-bold mb-2">Genre</h3>
              <div className="border p-4 flex flex-wrap">
                {genres.map((genre) => (
                    <button
                        key={genre}
                        className={`genre-button bg-gray-200 py-1 px-2 rounded mr-2 mb-2 ${
                            selectedGenres.includes(genre) ? "active" : ""
                        }`}
                        onClick={() => handleGenreToggle(genre)}
                    >
                      {genre}
                    </button>
                ))}
              </div>
            </div>
            <div className="flex mt-4 justify-between">
              <div className="w-2/3">
                <h3 className="font-bold mb-2">Selected Genres</h3>
                <div className="border p-4 bg-white">
                  {selectedGenres.length > 0 ? (
                      selectedGenres.join(", ")
                  ) : (
                      <span className="text-gray-500">No genres selected</span>
                  )}
                </div>
              </div>
              <div className="flex items-end">
                <button
                    className="analysis-button bg-blue-500 text-white py-2 px-4 rounded"
                    onClick={handleAnalysis}
                    disabled={loading}
                >
                  {loading ? "Loading..." : "Analysis"}
                </button>
              </div>
            </div>
          </section>

          {showAnalysis && analysisData && (
              <section id="analysis" className="mt-8">
                <h2 className="text-2xl font-bold mb-4">Analysis Results</h2>
                <div className="mb-8">
                  <p>Average Rating: {analysisData.averageRating.toFixed(2)}</p>
                  <div className="flex mt-4">
                    <div className="w-1/2 bg-gray-300 h-auto p-4">
                      <h4 className="font-bold mb-2">High Rating Users (4-5 stars)</h4>
                      <div>Gender: {JSON.stringify(analysisData.highRatingUsers.genderCount)}</div>
                      <div>Age: {JSON.stringify(analysisData.highRatingUsers.ageCount)}</div>
                      <div>Occupation: {JSON.stringify(analysisData.highRatingUsers.occupationCount)}</div>
                    </div>
                    <div className="w-1/2 bg-gray-300 h-auto p-4">
                      <h4 className="font-bold mb-2">Low Rating Users (1-3 stars)</h4>
                      <div>Gender: {JSON.stringify(analysisData.lowRatingUsers.genderCount)}</div>
                      <div>Age: {JSON.stringify(analysisData.lowRatingUsers.ageCount)}</div>
                      <div>Occupation: {JSON.stringify(analysisData.lowRatingUsers.occupationCount)}</div>
                    </div>
                  </div>
                </div>
              </section>
          )}
        </main>
      </div>
  );
};

export default Feature1;
