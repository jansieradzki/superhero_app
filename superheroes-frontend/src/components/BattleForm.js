import { jsx as _jsx, jsxs as _jsxs } from "react/jsx-runtime";
import { useState } from "react";
var BattleForm = function (_a) {
    var onBattle = _a.onBattle;
    var _b = useState(""), character = _b[0], setCharacter = _b[1];
    var _c = useState(""), rival = _c[0], setRival = _c[1];
    var _d = useState(null), error = _d[0], setError = _d[1];
    var isValidInput = function (input) { return /^[a-zA-Z\s]+$/.test(input); };
    var handleSubmit = function (e) {
        e.preventDefault();
        if (!isValidInput(character) || !isValidInput(rival)) {
            setError("Both character and rival names must only contain letters and spaces.");
            return;
        }
        setError(null);
        onBattle(character.trim(), rival.trim());
    };
    return (_jsxs("form", { onSubmit: handleSubmit, className: "bg-white shadow p-6 rounded-lg", children: [_jsx("h2", { className: "text-xl font-bold mb-4", children: "Start a Battle" }), error && _jsx("div", { className: "bg-red-100 text-red-700 border border-red-300 rounded p-4 mb-4", children: error }), _jsxs("div", { className: "mb-4", children: [_jsx("label", { className: "block text-gray-700", children: "Character:" }), _jsx("input", { type: "text", className: "w-full border border-gray-300 rounded px-4 py-2", value: character, onChange: function (e) { return setCharacter(e.target.value); } })] }), _jsxs("div", { className: "mb-4", children: [_jsx("label", { className: "block text-gray-700", children: "Rival:" }), _jsx("input", { type: "text", className: "w-full border border-gray-300 rounded px-4 py-2", value: rival, onChange: function (e) { return setRival(e.target.value); } })] }), _jsx("button", { type: "submit", className: "bg-blue-500 text-white px-4 py-2 rounded hover:bg-blue-600 transition duration-200", children: "Battle!" })] }));
};
export default BattleForm;
