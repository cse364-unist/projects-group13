import React, { useState } from "react";
import GenreSelection from "../selection/genreSelection";
import YearSelector from "../selection/yearSelection";
import Ranking from "./ranking";
import Loading from "../Loading";

const Feature3 = () => {
    const minYear = 1919;
    const maxYear = new Date().getFullYear();

    const [selectedYear, setSelectedYear] = useState(maxYear + "");
    const [selectedGenres, setSelectedGenres] = useState([]);
    const [data, setData] = useState(null);
    const [isLoading, setIsLoading] = useState(false);

    async function fetchData(e) {
        e.preventDefault();

        setIsLoading(true);
        let defaultLink = "http://localhost:8080/gfa";
        defaultLink += `/year/${selectedYear}`;
        defaultLink +=
            selectedGenres.length > 0 ? `/genre/${selectedGenres[0]}` : "";

        try {
            const response = await fetch(defaultLink, {
                method: "GET",
                headers: {
                    "Content-Type": "application/json",
                },
            });

            if (!response.ok) {
                throw new Error("Error fetching data");
            }

            const resdata = await response.json();
            if (!resdata._embedded) {
                setData(null);
                return;
            }
            setData(resdata._embedded);
        } catch (error) {
            alert("Error fetching data");
        } finally {
            setIsLoading(false);
        }
    }

    function handleClick(e) {
        fetchData(e);
    }

    return (
        <div className="bg-gray-100 text-gray-900 font-roboto">
            <main className="container mx-auto py-8 px-4">
                <section id="feature3" className="mb-8">
                    <h2 className="text-2xl font-bold mb-4">Feature 3</h2>
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
                                You have to choose only ONE genre.
                            </li>
                            <li>For test, choose Action and 1998.</li>
                        </ul>
                    </section>
                    <div className="flex">
                        <div className="w-1/3 p-2 space-y-2">
                            <h3 className="font-bold mb-2">Select Year</h3>
                            <div className="flex justify-center space-x-3">
                                <YearSelector
                                    value={selectedYear}
                                    setValue={setSelectedYear}
                                    minYear={minYear}
                                    maxYear={maxYear}
                                />
                                <button
                                    className="inline-flex justify-center text-center bg-blue-500 text-white p-2 rounded-md hover:shadow-md hover:scale-105 transition-all duration-300"
                                    onClick={handleClick}
                                    disabled={isLoading}
                                >
                                    {isLoading ? <Loading width="w-7" height="h-7" /> : "Search"}
                                </button>
                            </div>
                            <h3 className="font-bold mb-2">Select Genre</h3>
                            <GenreSelection
                                value={selectedGenres}
                                setValue={setSelectedGenres}
                                isSingle={true}
                            />
                        </div>
                        <div className="w-2/3">
                            <h3 className="font-bold mb-2">Ranking List</h3>
                            {isLoading ? (
                                <div className="border rounded-md h-64 p-4">
                                    <div className="inline-flex text-center items-center justify-center space-x-3">
                                        <Loading />
                                        <span>Loading...</span>
                                    </div>
                                </div>
                            ) : data && data.genreRateList ? (
                                <div className="border rounded-md p-4">
                                    <Ranking data={data.genreRateList} />
                                </div>
                            ) : (
                                <div className="border rounded-md h-64 p-4">
                                    No data
                                </div>
                            )}
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
};

export default Feature3;
