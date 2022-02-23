import React from "react";
import type { Player } from "src/gameState";
import "./Kalaha.css";

type KalahaProps = {
    owner: Player
}

export function Kalaha({ owner }: KalahaProps) {

    const backgroundClass = owner.type === "player1" ? "player-one" : "player-two";

    return (
        <div className={"mancala-kalaha " + backgroundClass}>
            {owner.pits[6].nrOfStones}
        </div>
    )
}