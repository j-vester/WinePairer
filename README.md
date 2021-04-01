# Wine Pairer

Deze applicatie is in staat om wijn-spijs combinaties te adviseren. De applicatie heeft de volgende functionaliteiten:
- Het vergaren van wijn-spijs combinaties uit een boek hierover en het opbouwen van een Database hiervan
- Op basis van een aantal ingrediënten een advies geven over de soort wijn die daar goed bij te serveren valt. 

## Instructies voor installatie
De frontend is gebouwd met React. Om de React applicatie te kunnen runnen moeten eerst de benodigde dependencies geïnstalleerd worden. Deze zijn gedefinieerd in het package.json bestand. Run het command `npm install` vanuit de `/client` directory.

De applicatie zelf en het domein zijn gebouwd met behulp van Gradle. Je kunt of Gradle installeren op je eigen machine en die installatie gebruiken om deze applicatie op te starten of de Gradle wrapper files gebruiken die in deze repository te vinden zijn. Als je Gradle zelf globaal hebt geinstalleerd vervang dan de `./gradlew` in de onderstaande commands met `gradle`. Of als je de Windows batch script runt vervang het dan door `.\gradlew.bat`.

De database is gebouwd in MySQL. Om deze database lokaal te creëren moet eerst MySQL geïnstalleerd zijn, zie de website van [MySQL](https://dev.mysql.com/doc/mysql-installation-excerpt/8.0/en/general-installation-issues.html) voor de uitleg voor installatie op verschillende platforms. Run vervolgens in de command line `mysql -u root -p` en voer het ingestelde wachtwoord in om de mysql command line client op te starten. In de mysql command line client run je de command `source ./database/create_db.sql;` en vervolgens `quit` om de mysql command line client weer te sluiten.

## Instructies voor het runnen van de applicatie
De frontend en de backend moeten apart worden opgestart, maar zijn allebei nodig om de applicatie te kunnen gebruiken. 
### Frontend
In het package.json bestand staan verscheidene commands om die vanuit de `/client` directory gerund kunnen worden, altijd voorafgegaan door het command `npm run`:

```bash
# Start een developer server op in http://localhost:8080
npm run start
# Check code voor fouten en style gebruiken
npm run lint
# Bouw een static copy van de site in de build map voor deployment
npm run build
# Start de test runner van de applicatie, run met --watch om in interactieve modus te runnen
npm run test
```

### Backend
Als je het programma runt in de developer mode zal de build "progress" blijven hangen op 87%. Dit houdt in dat de applicatie runt en Gradle wacht tot die slaagt. Deze progress bar kan worden genegeerd als de applicatie wordt gerunt. 

```bash
# Start de applicatie op om te kunnen gebruiken in developer mode
./gradlew run
# Bouw een static copy van de applicatie in de build map voor deployment
./gradlew build
# Run alle tests gedefinieerd in de test directories van /domain en /api
./gradlew test
```

