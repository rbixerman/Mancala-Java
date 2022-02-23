import React from "react";
import "./Rows.css";

export function Rows(props: { children: boolean | React.ReactChild | React.ReactFragment | React.ReactPortal | null | undefined; }) {
    return (
        <div className="mancala-rows">
            {props.children}
        </div>
    )
}