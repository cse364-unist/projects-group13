import React, { useState } from "react";
import GenreSelection from "../selection/genreSelection";

const Feature3 = () => {

    const minYear = 1919;
    const maxYear = new Date().getFullYear();

    const [selectedYear, setSelectedYear] = useState(maxYear + "");

    const handleYearChange = (event) => {
        setSelectedYear(event.target.value);
    };

    return (
        <div className="bg-gray-100 text-gray-900 font-roboto">
            <main className="container mx-auto py-8 px-4">
                <section id="feature3" className="mb-8">
                    <h2 className="text-2xl font-bold mb-4">Feature 3</h2>
                    <div className="flex">
                        <div className="w-1/3 p-2 space-y-2">
                            <h3 className="font-bold mb-2">Select Year</h3>
                            <select
                                className="border p-2 w-full"
                                value={selectedYear}
                                onChange={handleYearChange}
                            >
                                {Array.from(
                                    { length: maxYear - minYear + 1 },
                                    (_, i) => (
                                        <option key={i} value={maxYear - i}>
                                            {maxYear - i}
                                        </option>
                                    )
                                )}
                            </select>
                            <h3 className="font-bold mb-2">Select Genre</h3>
                            <GenreSelection 
                                selectedGenres={[]} 
                                setSelectedGenres={() => {}} 
                                isSingle={false}
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
