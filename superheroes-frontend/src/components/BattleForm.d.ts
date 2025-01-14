import React from "react";
interface Props {
    onBattle: (character: string, rival: string) => void;
}
declare const BattleForm: React.FC<Props>;
export default BattleForm;
