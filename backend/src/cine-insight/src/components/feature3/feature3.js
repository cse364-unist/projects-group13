import React, { useState } from "react";
import GenreSelection from "../selection/genreSelection";
import YearSelector from "../selection/yearSelection";

const Feature3 = () => {

    const minYear = 1919;
    const maxYear = new Date().getFullYear();

    const [selectedYear, setSelectedYear] = useState(maxYear + "");
    const [selectedGenres, setSelectedGenres] = useState([]);

    return (
        <div className="bg-gray-100 text-gray-900 font-roboto">
            <main className="container mx-auto py-8 px-4">
                <section id="feature3" className="mb-8">
                    <h2 className="text-2xl font-bold mb-4">Feature 3</h2>
                    <div className="flex">
                        <div className="w-1/3 p-2 space-y-2">
                            <h3 className="font-bold mb-2">Select Year</h3>
                            <YearSelector
                                value={selectedYear}
                                setValue={setSelectedYear}
                                minYear={minYear}
                                maxYear={maxYear}
                            />
                            <h3 className="font-bold mb-2">Select Genre</h3>
                            <GenreSelection
                                value={selectedGenres}
                                setValue={setSelectedGenres}
                                isSingle={true}
                            />
                        </div>
                        <div className="w-2/3">
                            <h3 className="font-bold mb-2">Graph</h3>
                            <div className="border p-4 h-64"></div>
                        </div>
                    </div>
                </section>
            </main>
        </div>
    );
};

export default Feature3;
