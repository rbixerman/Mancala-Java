import React from "react";
import type { GameState, Player } from "src/gameState";
import { Bowl } from "./Bowl";
import "./Row.css";

type RowProps = {
    owner: Player,
    setGameState(newGameState: GameState): void
}

export function Row({ owner, setGameState }: RowProps) {

    const pits = owner.pits;
    const bowls = pits.slice(0, -1);

    if (owner.type === "player2") {
        bowls.reverse();
    }

    return (
        <div className="mancala-row">
            {bowls.map(bowl => <Bowl owner={owner} pit={bowl} setGameState={setGameState} />)}
        </div>
    )
}