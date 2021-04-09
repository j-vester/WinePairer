import React, { useState } from "react";
import type { WinePairing } from "../winePairing";
import "./PairWine.css";

type PairWineProps = {
    setWinePairing(newWinePairing: WinePairing): void;
}

export function PairWine({ setWinePairing }: PairWineProps) {


    const [meal, setMeal] = useState("");
    const [errorMessage, setErrorMessage] = useState("");
    
    async function tryObtainWine(e: React.FormEvent) {
        e.preventDefault();
        if (!meal) {
            setErrorMessage("Er moet eerst een hoofdingredient ingevuld worden!")
            return;
        }
        setErrorMessage("");

        try {
            const response = await fetch('winepairer/api/pairwine', {
                method: 'POST',
                headers: {
                    'Accept': 'application/json',
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({ mealToPair: meal })
            });

            if (response.ok) {
                const winePairing = await response.json();
                setWinePairing(winePairing);
            } else {
                console.error(response.statusText);
            }
        } catch (error) {
            console.error(error.toString());
        }
    }

    return (
        <div className="App-main">
            Hallo Jeroen!
            <form onSubmit={(e) => tryObtainWine(e)}>
                <input value={meal}
                    placeholder="Hoofdingrediënt"
                    onChange={(e) => setMeal(e.target.value)}
                />

                <p className="errormessage">{errorMessage}</p>

                <button className="obtainWineButton" type="submit">
                    Vind een passende wijn!
                </button>
            </form>
        </div>
  );
}