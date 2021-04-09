export interface WinePairing {
    foods: Food[];
}

interface Food {
    name: string;
    sameAsInput: boolean;
    gamechanger: boolean;
    pairings: Pairing[];
}

export interface Pairing {
    wineName: string;
    description: string;
}