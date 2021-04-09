import React from "react";
import type { Pairing } from "../winePairing";

export function Pairings(wines: Pairing[]) {
    return (
        <div>
            <ul>
                {wines.map((wine, index) => {
                    return <li key={index}>{wine.wineName}</li>
                })}
            </ul>
        </div>
    )
}