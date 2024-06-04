import React, { useState } from "react";

const Feature3 = ({ isActive }) => {
    const [selectedYear, setSelectedYear] = useState("2023");

    const handleYearChange = (event) => {
        setSelectedYear(event.target.value);
    };

    return (
        <section id="feature3" className={`mb-8 ${isActive ? "" : "hidden"}`}>
            <h2 className="text-2xl font-bold mb-4">Feature 3</h2>
            <div className="flex">
                <div className="w-1/3">
                    <h3 className="font-bold mb-2">Select Year</h3>
                    <select
                        className="border p-2 w-full"
                        value={selectedYear}
                        onChange={handleYearChange}
                    >
                        <option value="2023">2023</option>
                        <option value="2022">2022</option>
                        <option value="2021">2021</option>
                    </select>
                </div>
                <div className="w-2/3">
                    <h3 className="font-bold mb-2">Graph</h3>
                    <div className="border p-4 h-64"></div>
                </div>
            </div>
        </section>
    );
};

export default Feature3;
