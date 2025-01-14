var __spreadArray = (this && this.__spreadArray) || function (to, from, pack) {
    if (pack || arguments.length === 2) for (var i = 0, l = from.length, ar; i < l; i++) {
        if (ar || !(i in from)) {
            if (!ar) ar = Array.prototype.slice.call(from, 0, i);
            ar[i] = from[i];
        }
    }
    return to.concat(ar || Array.prototype.slice.call(from));
};
import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { useEffect, useState } from "react";
import { Client } from "@stomp/stompjs";
import SockJS from "sockjs-client";
var BattleHistory = function () {
    var _a = useState([]), history = _a[0], setHistory = _a[1];
    var _b = useState(""), search = _b[0], setSearch = _b[1];
    var _c = useState("asc"), sortOrder = _c[0], setSortOrder = _c[1];
    useEffect(function () {
        var websocketUrl = process.env.REACT_APP_WEBSOCKET_URL || "http://localhost:8080/websocket";
        var client = new Client({
            webSocketFactory: function () { return new SockJS(websocketUrl); },
            onConnect: function () {
                client.subscribe("/topic/battle-results", function (message) {
                    var battle = JSON.parse(message.body);
                    setHistory(function (prev) { return __spreadArray([battle], prev, true); });
                });
            },
            onStompError: function (error) {
                console.error("WebSocket Error:", error);
            },
        });
        client.activate();
        return function () {
            client.deactivate();
        };
    }, []);
    var filteredHistory = history.filter(function (battle) {
        return battle.character.toLowerCase().includes(search.toLowerCase()) ||
            battle.rival.toLowerCase().includes(search.toLowerCase());
    });
    var sortedHistory = __spreadArray([], filteredHistory, true).sort(function (a, b) {
        var dateA = new Date(a.timestamp).getTime();
        var dateB = new Date(b.timestamp).getTime();
        return sortOrder === "asc" ? dateA - dateB : dateB - dateA;
    });
    var toggleSortOrder = function () {
        setSortOrder(sortOrder === "asc" ? "desc" : "asc");
    };
    return (_jsxs("div", { className: "p-6 bg-white shadow rounded-lg", children: [_jsx("h2", { className: "text-2xl font-bold mb-4", children: "Battle History" }), _jsxs("div", { className: "flex justify-between items-center mb-4", children: [_jsx("input", { type: "text", placeholder: "Search by character or rival...", className: "border border-gray-300 rounded px-4 py-2 w-full max-w-md", value: search, onChange: function (e) { return setSearch(e.target.value); } }), _jsxs("button", { onClick: toggleSortOrder, className: "ml-4 bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition duration-200", children: ["Sort by Date (", sortOrder === "asc" ? "Ascending" : "Descending", ")"] })] }), _jsxs("table", { className: "min-w-full bg-white border border-gray-300", children: [_jsx("thead", { children: _jsxs("tr", { className: "bg-gray-100 text-left", children: [_jsx("th", { className: "border px-4 py-2", children: "Character" }), _jsx("th", { className: "border px-4 py-2", children: "Rival" }), _jsx("th", { className: "border px-4 py-2", children: "Winner" }), _jsx("th", { className: "border px-4 py-2", children: "Timestamp" })] }) }), _jsx("tbody", { children: sortedHistory.map(function (battle) { return (_jsxs("tr", { className: "hover:bg-gray-50 transition duration-150", children: [_jsx("td", { className: "border px-4 py-2", children: battle.character }), _jsx("td", { className: "border px-4 py-2", children: battle.rival }), _jsx("td", { className: "border px-4 py-2 font-bold", children: battle.winner }), _jsx("td", { className: "border px-4 py-2", children: new Date(battle.timestamp).toLocaleString() })] }, battle.id)); }) })] }), sortedHistory.length === 0 && (_jsx("p", { className: "text-center text-gray-500 mt-4", children: "No battles found." }))] }));
};
export default BattleHistory;
