import React from "react";
import type { GameState, Pit } from "src/gameState";
import "./Pit.css";

type PitProps = {
    pit: Pit | undefined,
    gameState: GameState,
    setGameState(newGameState: GameState): void
}

type MoveResponse = {
    success: boolean,
    newGameState: GameState,
    errorMessage: String
}

export function Pit({ pit, gameState, setGameState }: PitProps) {
    const index = pit?.index;


    async function tryDoMove(e: React.MouseEvent<HTMLButtonElement>) {
        e.preventDefault();
        console.log(`Button ${pit?.index} pressed.`);

        if (!pit) return;

        /*let newGameState: GameState = JSON.parse(JSON.stringify(gameState));
        let nstones = newGameState.players[Math.floor(pit?.index / 7)].pits[pit.index % 7].nrOfStones
        newGameState.players[Math.floor(pit?.index / 7)].pits[pit.index % 7].nrOfStones = nstones + 1;

        setGameState(newGameState);*/

        try {
            const response = await fetch(`mancala/api/move?index=${pit.index}`, {
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
        <button className="pit" onClick={tryDoMove}>
            {pit && pit.nrOfStones}
        </button>
    )
}