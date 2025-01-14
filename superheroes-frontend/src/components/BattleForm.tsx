import React, { useState } from "react";

interface Props {
    onBattle: (character: string, rival: string) => void;
}

const BattleForm: React.FC<Props> = ({ onBattle }) => {
    const [character, setCharacter] = useState("");
    const [rival, setRival] = useState("");
    const [error, setError] = useState<string | null>(null);

    const isValidInput = (input: string) => /^[a-zA-Z\s]+$/.test(input);

    const handleSubmit = (e: React.FormEvent) => {
        e.preventDefault();

        if (!isValidInput(character) || !isValidInput(rival)) {
            setError("Both character and rival names must only contain letters and spaces.");
            return;
        }

        setError(null);
        onBattle(character.trim(), rival.trim());
    };

    return (
        <form onSubmit={handleSubmit} className="bg-white shadow p-6 rounded-lg">
            <h2 className="text-xl font-bold mb-4">Start a Battle</h2>
            {error && <div className="bg-red-100 text-red-700 border border-red-300 rounded p-4 mb-4">{error}</div>}
            <div className="mb-4">
                <label className="block text-gray-700">Character:</label>
                <input
                    type="text"
                    className="w-full border border-gray-300 rounded px-4 py-2"
                    value={character}
                    onChange={(e) => setCharacter(e.target.value)}
                />
            </div>
            <div className="mb-4">
                <label className="block text-gray-700">Rival:</label>
                <input
                    type="text"
                    className="w-full border border-gray-300 rounded px-4 py-2"
                    value={rival}
                    onChange={(e) => setRival(e.target.value)}
                />
            </div>
            <button
                type="submit"
                className="bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition duration-200"
            >
                Battle!
            </button>
        </form>
    );
};

export default BattleForm;
