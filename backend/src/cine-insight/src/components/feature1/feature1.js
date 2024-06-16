import React, { useState } from "react";
import PieChartComponent from "./PieChartComponent";
import AreaChartComponent from "./AreaChartComponent";
import BarChartComponent from "./BarChartComponent";
import GenreSelection from "../selection/genreSelection";
import Loading from "../Loading";

const Feature1 = () => {
    const [selectedGenres, setSelectedGenres] = useState([]);
    const [showAnalysis, setShowAnalysis] = useState(false);
    const [analysisData, setAnalysisData] = useState(null);
    const [loading, setLoading] = useState(false);

    const handleAnalysis = async (e) => {
        e.preventDefault();

        if (selectedGenres.length < 3) {
            alert("You should choose at least 3 genres.");
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

    const prepareChartData = (data) => {
        return Object.keys(data).map((key) => ({
            name: key,
            value: data[key],
        }));
    };

    return (
        <div className="bg-gray-100 text-gray-900 font-roboto">
            <main className="container mx-auto py-8 px-4">
                <section id="feature1" className="mb-8">
                    <h2 className="text-2xl font-bold mb-4">Feature 1</h2>

                    <section className="mb-6 p-4 bg-blue-100 border border-blue-300 rounded">
                        <h4 className="font-bold mb-2 text-lg text-blue-700">
                            Instructions
                        </h4>
                        <ul className="list-disc list-inside text-blue-700">
                            <li className="mb-1">
                                It might take more than a minute. Please don't
                                worry; this is not an error.
                            </li>
                            <li className="mb-1">
                                You have to choose at least 3 genres.
                            </li>
                            <li>For test: Animation, Sci-Fi, Thriller</li>
                        </ul>
                    </section>

                    <div>
                        <h3 className="font-bold mb-2">Genre</h3>
                        <GenreSelection
                            value={selectedGenres}
                            setValue={setSelectedGenres}
                        />
                    </div>
                    <div className="flex mt-4 justify-between">
                        <div className="w-full">
                            <h3 className="font-bold mb-2">Selected Genres</h3>
                            <div className="border p-4 bg-white">
                                {selectedGenres.length > 0 ? (
                                    selectedGenres.join(", ")
                                ) : (
                                    <span className="text-gray-500">
                                        No genres selected
                                    </span>
                                )}
                            </div>
                        </div>
                        <div className="flex items-end ml-4">
                            <button
                                className="analysis-button bg-blue-500 text-white py-4 px-8 rounded text-lg font-bold" // 버튼을 크게 만듦
                                onClick={handleAnalysis}
                                disabled={loading}
                            >   
                                <div className="inline-flex items-center text-center justify-center space-x-3">
                                    {loading && <Loading />}
                                    {loading ? <span>Analyzing...</span> : <span>Analysis</span>}
                                </div>
                            </button>
                        </div>
                    </div>
                </section>

                {showAnalysis && analysisData && (
                    <section id="analysis" className="mt-8">
                        {analysisData.averageRating === 0 ? (
                            <p>No suitable movies found.</p>
                        ) : (
                            <div className="mt-8 p-4 bg-white border">
                                {" "}
                                {/* Analysis results 배경 흰색 */}
                                <h2 className="text-2xl font-bold mb-4">
                                    Analysis Results
                                </h2>
                                <div className="mb-8">
                                    <p>
                                        Average Rating:{" "}
                                        {analysisData.averageRating.toFixed(2)}
                                    </p>
                                    <div className="flex flex-wrap mt-4 -mx-2">
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                High Rating Users (4-5 stars) -
                                                Age Distribution
                                            </h4>
                                            <AreaChartComponent
                                                data={prepareChartData(
                                                    analysisData.highRatingUsers
                                                        .ageCount
                                                )}
                                                title="Age Distribution"
                                                color="#31AFD4"
                                            />
                                        </div>
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4 flex items-center justify-center flex-col">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                High Rating Users (4-5 stars) -
                                                Gender Distribution
                                            </h4>
                                            <PieChartComponent
                                                data={prepareChartData(
                                                    analysisData.highRatingUsers
                                                        .genderCount
                                                )}
                                                title="Gender Distribution"
                                                color="#31AFD4"
                                            />
                                        </div>
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                High Rating Users (4-5 stars) -
                                                Occupation Distribution
                                            </h4>
                                            <BarChartComponent
                                                data={prepareChartData(
                                                    analysisData.highRatingUsers
                                                        .occupationCount
                                                )}
                                                title="Occupation Distribution"
                                                color="#31AFD4"
                                            />
                                        </div>
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                Low Rating Users (1-3 stars) -
                                                Age Distribution
                                            </h4>
                                            <AreaChartComponent
                                                data={prepareChartData(
                                                    analysisData.lowRatingUsers
                                                        .ageCount
                                                )}
                                                title="Age Distribution"
                                                color="#902D41"
                                            />
                                        </div>
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4 flex items-center justify-center flex-col">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                Low Rating Users (1-3 stars) -
                                                Gender Distribution
                                            </h4>
                                            <PieChartComponent
                                                data={prepareChartData(
                                                    analysisData.lowRatingUsers
                                                        .genderCount
                                                )}
                                                title="Gender Distribution"
                                                color="#902D41"
                                            />
                                        </div>
                                        <div className="w-full md:w-1/2 lg:w-1/3 bg-white h-auto p-4">
                                            {" "}
                                            {/* 그래프 배경 흰색 */}
                                            <h4 className="font-bold mb-2">
                                                Low Rating Users (1-3 stars) -
                                                Occupation Distribution
                                            </h4>
                                            <BarChartComponent
                                                data={prepareChartData(
                                                    analysisData.lowRatingUsers
                                                        .occupationCount
                                                )}
                                                title="Occupation Distribution"
                                                color="#902D41"
                                            />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        )}
                    </section>
                )}
            </main>
        </div>
    );
};

export default Feature1;
