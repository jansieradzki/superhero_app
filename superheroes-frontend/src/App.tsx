import React, { useState } from "react";
import Header from "./components/Header";
import BattleForm from "./components/BattleForm";
import BattleHistory from "./components/BattleHistory";

const App: React.FC = () => {
    const [error, setError] = useState<string | null>(null);

    const handleBattle = async (character: string, rival: string) => {
        const backendUrl = process.env.REACT_APP_BACKEND_URL || "http://localhost:8080";
        setError(null);

        try {
            const response = await fetch(`${backendUrl}/api/battle?character=${character}&rival=${rival}`);

            if (response.status === 404) {
                setError("One of the characters was not found. Please check the names and try again.");
                return;
            }

            if (response.status === 400) {
                setError("Characters of the same type cannot fight.");
                return;
            }

            if (!response.ok) {
                const errorData = await response.json();
                setError(errorData.error || "An unknown error occurred.");
                return;
            }

            console.log("Battle completed successfully.");
        } catch (error) {
            console.error("Error during battle:", error);
            setError("Failed to connect to the server. Please try again later.");
        }
    };


    return (
        <div>
            <Header />
            <main className="p-8">
                <h1 className="text-3xl font-bold mb-6 text-center">
                    Welcome to the Superheroes Battle Arena
                </h1>
                {error && (
                    <div className="bg-red-100 text-red-700 border border-red-300 rounded p-4 mb-4">
                        {error}
                    </div>
                )}
                <BattleForm onBattle={handleBattle} />
                <BattleHistory />
            </main>
        </div>
    );
};

export default App;
