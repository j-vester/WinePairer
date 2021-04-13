# Project structuur

- Main folder (deze map): bevat alle bestanden relevant voor het gehele project.
- api/: bevat de bestanden voor de API of service laag van de applicatie.
    - api/src/main/java/winepairer: bevat de web endpoints.
    - api/src/main/java/winepairer/api/dto: bevat Data Transfer Objects die worden gecommuniceerd naar de client
- domain/: bevat de bestanden die het domein modelleren, zowel het inlezen van het ebook als de objecten.
- client/: bevat de bestanden voor de client-side/frontend.

## Software stack en externe dependencies
Het domein en de API laag zijn gebouwd met behulp van de [Gradle](https://gradle.org) build tool. In het domein staat een Nederlands ebook over wijn-spijs combinaties die ingelezen wordt met behulp van [epublib](http://www.siegmann.nl/epublib), een externe Java library voor het schrijven en lezen van epub files. Een overzicht van alle packages en classes die deze bevat vindt je [hier](https://appdoc.app/artifact/com.positiondev.epublib/epublib-core/3.1/). Ook is er gebruik gemaakt van een apache library om de zogeheten Levenshtein distance te berekenen tussen twee Strings, meer informatie vind je [hier](https://commons.apache.org/proper/commons-text/apidocs/org/apache/commons/text/similarity/LevenshteinDistance.html).

De database is gebouwd in [MySQL](https://dev.mysql.com) en om vanuit Java verbinding te maken met de database is gebruik gemaakt van de [java.sql](https://docs.oracle.com/javase/8/docs/api/java/sql/package-summary.html) package. 

De website van de applicatie is gebouwd met behulp van de [Snowpack](https://www.snowpack.dev) build tool en voor de functionaliteit van de website is gebruik gemaakt van de [React](https://reactjs.org) library voor JavaScript. 