import React from "react";
import type { GameState, Pit, Player } from "src/gameState";

import "./Bowl.css";

type BowlProps = {
    owner: Player,
    pit: Pit,
    setGameState(newGameState: GameState): void
}


type MoveResponse = {
    success: boolean,
    newGameState: GameState,
    errorMessage: String
}

export function Bowl({ owner, pit, setGameState }: BowlProps) {

    const backgroundClass = owner.type === "player1" ? "player-one" : "player-two";

    async function tryDoMove(e: React.MouseEvent<HTMLButtonElement>) {
        const index = pit.index;
        e.preventDefault();
        console.log(`Button ${index} pressed.`);

        try {
            const response = await fetch(`mancala/api/move?index=${index}`, {
                method: 'POST',
                headers: {
                    'Accept': 'application/json'
                }
            });

            if (response.ok) {
                console.log("Succeed moving: " + index);
                const moveResult: MoveResponse = await response.json();

                if (moveResult.success) {
                    setGameState(moveResult.newGameState);
                } else {
                    alert(moveResult.errorMessage);
                }
            }
        } catch (error: any) {
            console.log(error);
        }
    }

    return (
        <button className={`mancala-bowl ${backgroundClass}`} onClick={tryDoMove}>
            {pit.nrOfStones}
        </button>
    )
}