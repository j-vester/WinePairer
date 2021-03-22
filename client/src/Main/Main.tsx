import React, { useState } from 'react';
import { PairWine } from "./PairWine"
import type { WinePairing } from "../winePairing"
import './Main.css';

export function Main() {
    const [ winePairing, setWinePairing ] = useState<WinePairing | undefined>(undefined);
    console.log(winePairing);

    if (!winePairing) {
        return <PairWine setWinePairing={setWinePairing} />
    }

    return (<div>Bij {winePairing.meal} past {winePairing.wine}.</div>)
}