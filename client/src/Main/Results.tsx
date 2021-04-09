import React, { useState } from "react";
import type { WinePairing, Pairing } from "../winePairing";

type ResultProps = {
    result: WinePairing;
    setWinePairing(newWinePairing: WinePairing | undefined): void;
}

export function Results({result, setWinePairing}: ResultProps) {

    function printPairings(pairings: Pairing[], e:React.MouseEvent) {
        return (
            <ul>
                {pairings.map((value, index) => {
                    return <li key={index}>{value.wineName}</li>
                })}
            </ul>
        )
    }

    function emptyWinePairing(e:React.MouseEvent) {
        setWinePairing(undefined);            
    }

    return (
        <div>
            <p>Welke optie komt het meest overeen?</p>
            {result.foods.map(function(food){
                return <button onClick={(e)=>printPairings(food.pairings, e)}>{food.name}</button>
            })}
            <p>Of staat je optie er niet tussen? <button onClick={(e)=>emptyWinePairing(e)}>Probeer opnieuw</button></p>
        </div>
    )
}