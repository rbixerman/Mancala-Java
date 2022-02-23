import React from "react";
import type { Player } from "src/gameState";
import "./PlayerName.css";

type PlayerNameProps = {
    player: Player;
}
export function PlayerName({ player }: PlayerNameProps) {
    const playerOne = player.type === "player1";
    const color = playerOne ? "var(--player-one-color)" : "var(--player-two-color)";
    return (
        <span className="player-name" style={{ color: color }}>{player.name}</span>
    )
}