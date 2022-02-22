import React from "react";
import type { GameState } from "src/gameState";
import { Pit } from "./Pit";
import "./Board.css";

type BoardProps = {
    gameState: GameState,
    setGameState(newGameState: GameState): void
}

export function Board({ gameState, setGameState }: BoardProps) {
    return (
        <div>
            <div className="side">
                {gameState.players[1].name}
                <div className="board-row">
                    {gameState.players[1].pits.slice().reverse().map(pit => <Pit key={"p2pit" + pit.index} gameState={gameState} setGameState={setGameState} pit={pit} />)}
                    <Pit pit={undefined} gameState={gameState} setGameState={setGameState} />
                </div>
            </div>
            <div className="side">
                <div className="board-row">
                    <Pit pit={undefined} gameState={gameState} setGameState={setGameState} />
                    {gameState.players[0].pits.map(pit => <Pit key={"p1pit" + pit.index} pit={pit} setGameState={setGameState} gameState={gameState} />)}
                </div>
                {gameState.players[0].name}
            </div>
        </div>
    )
}