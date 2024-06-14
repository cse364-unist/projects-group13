import React, { useState } from "react";
import { BrowserRouter, Routes, Route } from "react-router-dom";

import Header from "./components/Header";
import Navigation from "./components/Navigation";
import Main from "./components/Main";
import Feature1 from "./components/feature1/feature1";
import Feature2 from "./components/feature2/feature2";
import Feature3 from "./components/feature3/feature3";
import Feature2Actor from "./components/feature2/feature2actor";
import "./index.css";

function App() {
  return (
    <>
      <BrowserRouter>
        <Header />
        <Navigation />
        <Routes>
          <Route path="/" element={<Main />}></Route>
          <Route path="/pua" element={<Feature1 />}></Route>
          <Route path="/gbar" element={<Feature2 />}></Route>
          <Route path="/gfl" element={<Feature3 />}></Route>
          <Route path="/gbar/result" element={<Feature2Actor />}></Route>
        </Routes>
      </BrowserRouter>
    </>
  );
}

export default App;
