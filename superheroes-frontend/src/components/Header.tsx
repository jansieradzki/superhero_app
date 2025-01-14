import React from "react";

const Header: React.FC = () => {
    return (
        <header className="bg-blue-600 text-white p-4 shadow-lg">
            <div className="container mx-auto text-center">
                <h1 className="text-4xl font-bold">Superheroes Battle App</h1>
                <p className="text-lg mt-2">
                    Dynamic battles between superheroes and supervillains!
                </p>
            </div>
        </header>
    );
};

export default Header;
