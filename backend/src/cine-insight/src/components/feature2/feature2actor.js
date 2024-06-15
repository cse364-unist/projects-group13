import React from "react";

import BarGraph from "../graph/BarGraph";
import RaderChart from "../graph/RaderChart";

const data = [
  { name: "Option A", person: 10 },
  { name: "Option B", person: 52 },
  { name: "Option C", person: 42 },
  { name: "Option D", person: 23 },
  { name: "Option E", person: 12 },
  { name: "Option F", person: 46 },
  { name: "Option G", person: 34 },
];

const genreArray = [
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

const data2 = [
  {
    subject: "Drama",
    A: 120,
    B: 110,
    fullMark: 150,
  },
  {
    subject: "Adventure",
    A: 98,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Action",
    A: 86,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Comedy",
    A: 99,
    B: 100,
    fullMark: 150,
  },
  {
    subject: "Horror",
    A: 85,
    B: 90,
    fullMark: 150,
  },
  {
    subject: "Biography",
    A: 65,
    B: 85,
    fullMark: 150,
  },
  {
    subject: "Crime",
    A: 120,
    B: 110,
    fullMark: 150,
  },
  {
    subject: "Fantasy",
    A: 98,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Family",
    A: 86,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Sci-Fi",
    A: 99,
    B: 100,
    fullMark: 150,
  },
  {
    subject: "Animation",
    A: 85,
    B: 90,
    fullMark: 150,
  },
  {
    subject: "Romance",
    A: 65,
    B: 85,
    fullMark: 150,
  },
  {
    subject: "Music",
    A: 86,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Western",
    A: 99,
    B: 100,
    fullMark: 150,
  },
  {
    subject: "Thriller",
    A: 85,
    B: 90,
    fullMark: 150,
  },
  {
    subject: "History",
    A: 65,
    B: 85,
    fullMark: 150,
  },
  {
    subject: "Mystery",
    A: 86,
    B: 130,
    fullMark: 150,
  },
  {
    subject: "Sport",
    A: 99,
    B: 100,
    fullMark: 150,
  },
  {
    subject: "Musical",
    A: 85,
    B: 90,
    fullMark: 150,
  },
];
const Feature2Actor = () => {
  return (
    <div className="min-h-screen bg-gray-100 text-gray-900 font-roboto">
      <main className="container mx-auto py-8 px-4">
        <section id="feature2-actor" className="mb-8">
          <h2 className="text-2xl font-bold mb-4">Feature 2 - Actor Name</h2>
          <div className="flex">
            <div className="w-1/2">
              <h3 className="font-bold mb-2">Actor List</h3>
            </div>
            <div className="w-1/2">
              <h3 className="font-bold mb-2">Graph</h3>
            </div>
          </div>
          <div>
            <div className="flex border w-full">
              <div className="w-1/2 p-4">
                <h3 className="py-2 text-xl font-bold">Actor 1</h3>
                <ul class="divide-y">
                  <li class="p-1 hover:bg-white">Movie 1</li>
                  <li class="p-1 hover:bg-white">Movie 2</li>
                  <li class="p-1 hover:bg-white">Movie 3</li>
                </ul>
              </div>
              <div className="w-1/2 h-96">
                <RaderChart data={data2} />
              </div>
            </div>
            <div className="flex border w-full">
              <div className="w-1/2 p-4">
                <h3 className="py-2 text-xl font-bold">Actor 2</h3>
                <ul class="divide-y">
                  <li class="p-1 hover:bg-white">Movie 1</li>
                  <li class="p-1 hover:bg-white">Movie 2</li>
                  <li class="p-1 hover:bg-white">Movie 3</li>
                </ul>
              </div>
              <div className="w-1/2 h-96">
                <BarGraph data={data} />
              </div>
            </div>
          </div>
        </section>
      </main>
    </div>
  );
};

export default Feature2Actor;
