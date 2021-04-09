import React, { useState } from 'react';
import { PairWine } from "./PairWine"
import { Results } from "./Results";
import type { WinePairing, Pairing } from "../winePairing"
import './Main.css';

export function Main() {
    const [ winePairing, setWinePairing ] = useState<WinePairing | undefined>(undefined);
    const [ pairing, setPairing] = useState<Pairing[] | undefined>(undefined);
    
    if (!winePairing) {
        return <PairWine setWinePairing={setWinePairing} />
    } else if (!pairing) {
        return <Results result={winePairing} setWinePairing={setWinePairing} setPairing={setPairing}/>
    }
    return (
        <div>
            <ul>
                {pairing.map((wine, index) => {
                    return <li key={index}>{wine.wineName}</li>
                })}
            </ul>
        </div>
    )

}