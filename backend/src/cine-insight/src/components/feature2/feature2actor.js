import React from "react";
import { useLocation } from "react-router-dom";

import RaderChart from "../graph/RaderChart";

const Feature2Actor = () => {
  const location = useLocation();
  const { result, genres } = location.state || {}; // 전달받은 상태 접근

  console.log(result);

  const genreConverter = (genreVector, count) => {
    let data = [];
    for (let i = 0; i < genres.length; i++) {
      if (count[i] !== 0) {
        data.push({
          subject: genres[i],
          score: (genreVector[i] / count[i]).toFixed(2),
          fullMark: 10,
        });
      }
      // data.push({
      //   subject: genres[i],
      //   score: count[i] !== 0 ? (genreVector[i] / count[i]).toFixed(2) : 0,
      //   fullMark: 10,
      // });
    }

    return data;
  };

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
            {result.map((elem) => (
              <div className="flex border w-full">
                <div className="w-1/2 p-4">
                  <h3 className="py-2 text-xl font-bold">{elem.name}</h3>
                  <ul class="divide-y">
                    {elem.titles.map((title) => (
                      <li className="p-1 hover:bg-white">{title}</li>
                    ))}
                  </ul>
                </div>
                <div className="w-1/2 h-96">
                  <RaderChart
                    name={elem.name}
                    data={genreConverter(elem.genre, elem.count)}
                  />
                </div>
              </div>
            ))}
          </div>
        </section>
      </main>
    </div>
  );
};

export default Feature2Actor;
