import React from "react";
import type { GameState } from "src/gameState";
import "./Board.css";

import { Kalaha } from "./gameboard/Kalaha";
import { Row } from "./gameboard/Row";
import { Rows } from "./gameboard/Rows";
import { PlayerName } from "./PlayerName";

type BoardProps = {
    gameState: GameState,
    setGameState(newGameState: GameState): void
}

export function Board({ gameState, setGameState }: BoardProps) {

    const playerOne = gameState.players[0];
    const playerTwo = gameState.players[1];
    return (
        <div className="board-wrapper">
            <PlayerName player={playerTwo} />

            <div className="mancala-board">
                <Kalaha owner={playerTwo} />
                <Rows>
                    <Row owner={playerTwo} setGameState={setGameState} />
                    <Row owner={playerOne} setGameState={setGameState} />
                </Rows>
                <Kalaha owner={playerOne} />
            </div>

            <PlayerName player={playerOne} />
        </div>
    )
}