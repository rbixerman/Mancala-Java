import React from "react";
import type { GameState } from "../gameState";
import { Board } from "./Board";
import "./Play.css";

type PlayProps = {
    gameState: GameState;
    setGameState(newGameState: GameState): void;
}

export function Play({ gameState, setGameState }: PlayProps) {
    return (
        <div className="play-div">
            <Board gameState={gameState} setGameState={setGameState} />
        </div>
    )
}