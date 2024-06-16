import React from "react";
import { useNavigate } from "react-router-dom";
import Textbox from "./buttons/Textbox";

const Header = () => {
    const navi = useNavigate();

    function handleMainCleck() {
        navi("/");
    }

    return (
        <header className="bg-white shadow">
            <div className="container mx-auto py-6 px-4">
                <div className="flex justify-center content-center">
                    <Textbox
                        onClick={handleMainCleck}
                    >
                        Cine Insight
                    </Textbox>
                </div>
                <p className="text-center mt-2">
                    The tool for movie lovers and directors
                </p>
            </div>
        </header>
    );
};

export default Header;
