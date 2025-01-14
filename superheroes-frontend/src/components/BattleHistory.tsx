import React, { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";

interface BattleHistory {
    id: string;
    character: string;
    rival: string;
    winner: string;
    timestamp: string;
}

const BattleHistory: React.FC = () => {
    const [history, setHistory] = useState<BattleHistory[]>([]);
    const [search, setSearch] = useState("");
    const [sortOrder, setSortOrder] = useState<"asc" | "desc">("asc");

    useEffect(() => {
        const websocketUrl = process.env.REACT_APP_WEBSOCKET_URL || "http://localhost:8080/websocket";
        const client = new Client({
            webSocketFactory: () => new SockJS(websocketUrl),
            onConnect: () => {
                client.subscribe("/topic/battle-results", (message) => {
                    const battle: BattleHistory = JSON.parse(message.body);
                    setHistory((prev) => [battle, ...prev]);
                });
            },
            onStompError: (error) => {
                console.error("WebSocket Error:", error);
            },
        });

        client.activate();

        return () => {
            client.deactivate();
        };
    }, []);

    const filteredHistory = history.filter(
        (battle) =>
            battle.character.toLowerCase().includes(search.toLowerCase()) ||
            battle.rival.toLowerCase().includes(search.toLowerCase())
    );

    const sortedHistory = [...filteredHistory].sort((a, b) => {
        const dateA = new Date(a.timestamp).getTime();
        const dateB = new Date(b.timestamp).getTime();
        return sortOrder === "asc" ? dateA - dateB : dateB - dateA;
    });

    const toggleSortOrder = () => {
        setSortOrder(sortOrder === "asc" ? "desc" : "asc");
    };

    return (
        <div className="p-6 bg-white shadow rounded-lg">
            <h2 className="text-2xl font-bold mb-4">Battle History</h2>

            <div className="flex justify-between items-center mb-4">
                <input
                    type="text"
                    placeholder="Search by character or rival..."
                    className="border border-gray-300 rounded px-4 py-2 w-full max-w-md"
                    value={search}
                    onChange={(e) => setSearch(e.target.value)}
                />
                <button
                    onClick={toggleSortOrder}
                    className="ml-4 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition duration-200"
                >
                    Sort by Date ({sortOrder === "asc" ? "Ascending" : "Descending"})
                </button>
            </div>

            <table className="min-w-full bg-white border border-gray-300">
                <thead>
                <tr className="bg-gray-100 text-left">
                    <th className="border px-4 py-2">Character</th>
                    <th className="border px-4 py-2">Rival</th>
                    <th className="border px-4 py-2">Winner</th>
                    <th className="border px-4 py-2">Timestamp</th>
                </tr>
                </thead>
                <tbody>
                {sortedHistory.map((battle) => (
                    <tr key={battle.id} className="hover:bg-gray-50 transition duration-150">
                        <td className="border px-4 py-2">{battle.character}</td>
                        <td className="border px-4 py-2">{battle.rival}</td>
                        <td className="border px-4 py-2 font-bold">{battle.winner}</td>
                        <td className="border px-4 py-2">
                            {new Date(battle.timestamp).toLocaleString()}
                        </td>
                    </tr>
                ))}
                </tbody>
            </table>

            {sortedHistory.length === 0 && (
                <p className="text-center text-gray-500 mt-4">No battles found.</p>
            )}
        </div>
    );
};

export default BattleHistory;
