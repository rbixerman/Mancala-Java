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
            <p>{gameState.players[0].name} vs {gameState.players[1].name}</p>

            <p>Current Turn: {gameState.players[0].hasTurn ? gameState.players[0].name : gameState.players[1].name}</p>
            <p>Is game over: {gameState.gameStatus.endOfGame ? "Yes" : "No"}</p>
            <p>Winner: {gameState.gameStatus.winner}</p>
            <Board gameState={gameState} setGameState={setGameState} />
        </div>
    )
}