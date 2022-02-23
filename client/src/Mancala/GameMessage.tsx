import React from "react";
import type { GameState } from "src/gameState";
import "./GameMessage.css";

type GameMessageProps = {
    gameState: GameState
}

export function GameMessage({ gameState }: GameMessageProps) {
    let message = "";

    if (gameState.gameStatus.endOfGame) {
        message = `Winner: ${gameState.gameStatus.winner}`
    } else {
        message = `Current turn: ${gameState.players[0].hasTurn ? gameState.players[0].name : gameState.players[1].name}`
    }

    return (
        <div className="gamemessage">
            {message}
        </div >
    )
}