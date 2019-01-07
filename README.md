# Programmeerproject
Eindvak van de minor 'Programmeren'

# Project Proposal
#### Calorie teller app – Menno Lont (11061200)

### Probleem
Mensen weten niet hoeveel ze eten op een dag, en/of vinden het moeilijk om af te vallen.

### Doelgroep
Afvallers, health freaks, sporters

### Functionaliteit
Tellen aantal calorieën per dag, een streefgewicht en tijdsperiode geven om de benodigde hoeveelheid calorieën per dag te berekenen, voortgang bekijken over langere periode. (Als er nog tijd is: bijhouden hoeveel vitamines je binnen krijgt)

### Applicatieschermen
#### Calorielijst
Standaard op ‘huidige datum’ (checkt in telefoon). Komt op een pagina met een lijst waar je naar verschillende dagdelen kan gaan om geconsumeerd voedsel in te vullen. Som van huidige calorietelling van die dag onderin. Kan ook naar links swipen om op voorgaande dagen te komen 
#### Datumkiezer	
Pop-up op scherm ‘Vandaaglijst’. De gebruiker kan een datum kiezen om voor die dag de geconsumeerde calorieën in te zien of aan te passen.
#### Voedingsproduct
De gebruiker kan een gegeten product intypen en hiervan de hoeveelheid aangeven (als deze bestaat in de database).
#### Hoeveelheid
Pop-up op scherm ‘Voedsel’. De gebruiker geeft de hoeveelheid aan die zij van het product gegeten hebben.
#### Grafiek
Een grafiek die de calorieaantallen en gewicht laat zien van de afgelopen maand. De gebruiker kan een ‘goal’ instellen om max per dag te eten, rood als de dag er overheen gaat. (Goal mag niet lager dan een bepaalde hoeveelheid, gezondheidsredenen)
#### Gegevens
De gebruiker kan zijn/haar goal aanpassen en gegevens bekijken/aanpassen. Gewicht regelmatig updaten wordt aangeraden.
#### BONUS 
Vitamines	inzien in hoeverre je aan de minimale vitaminehoeveelheid doet per dag. Mogelijkheid hangt af van de beschikbare API's.

### External libraries 
SQLite

### API’s
Veel keuze, voor nu Foodfacts en Food van https://www.programmableweb.com/category/nutrition/api

### Similar apps 
Calorie counter –MyNetDiary.com
Lose it! Calorie counter

### Hardest parts
De API correct laten werken op wat de gebruiker invult, en de porties gemakkelijk invulbaar maken, dit kan voorkomen worden met een goede search engine binnen in de app.
