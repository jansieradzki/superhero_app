import React from "react";
interface BattleHistory {
    id: string;
    character: string;
    rival: string;
    winner: string;
    timestamp: string;
}
declare const BattleHistory: React.FC;
export default BattleHistory;
