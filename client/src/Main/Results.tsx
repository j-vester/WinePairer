import React from "react";
import type { WinePairing, Pairing } from "../winePairing";

type ResultProps = {
    result: WinePairing;
    setWinePairing(newWinePairing: WinePairing | undefined): void;
    setPairing(newPairing: Pairing[]): void;
}

export function Results({result, setWinePairing, setPairing}: ResultProps) {

    return (
        <div>
            <p>Welke optie komt het meest overeen?</p>
            <ul>
                {result.foods.map((food, index) => {
                    return <li key={index}><button onClick={(e)=>setPairing(food.pairings)}>{food.name}</button></li>
                })}
            </ul>
            <p>Of staat je optie er niet tussen? <button onClick={(e)=>setWinePairing(undefined)}>Probeer opnieuw</button></p>
        </div>
    )
}