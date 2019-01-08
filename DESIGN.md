# Menno Lont (11061200) – Programmeerproject Apps

### Advanced UI sketches
Zie ‘UI_sketches.png’

### Diagrams
 
insert image>
Deze database slaat informatie op over de gebruiker die nodig is voor het gebruik van de app. De gebruiker vult deze informatie bij het eerste gebruik van de applicatie in. Velden zoals lengte, gewicht, geslacht en leeftijd zijn nodig omdat deze de noodzakelijke hoeveelheid calorieën per dag beïnvloeden. Omdat er maar 1 gebruiker per applicatie kan zijn is alleen een aanpassingsfunctie nodig. De gebruiker kan zelf zijn/haar gegevens aanpassen wanneer zij dit nodig achten. Regelmatig aanpassen van het gewicht is aangeraden om voortgang bij afvallen bij te houden.

insert image>
Deze database slaat informatie op over de door de gebruiker ingevulde geconsumeerde producten. Deze producten kunnen worden toegevoegd, aangepast en/of worden verwijderd. 

Er worden ook een Request functie en drie adapters gebruikt in de applicatie, hiervoor is echter geen heel diagram nodig dus ze staan hier op een rijtje:

#### FoodInfoRequest
Haalt informatie op uit de API’s. Dit moet waarschijnlijk live gebeuren omdat de hele API lokaal opslaan enorm veel ruimte in beslag neemt.
#### DayAdapter
Zet data uit de database om in informatie op de MainActivity. Deze informatie is de calorieaantallen per maaltijd en opgeteld over de hele dag.
#### MealAdapter
Zet data uit de database om in informatie op de MealActivity. Deze informatie is de gegeten producten in dat dagdeel en hun calorieaantallen plus de totale caloriehoeveelheid van dat dagdeel. 
#### UserAdapter
Zet data uit de database om in informatie op de UserActivity. Deze informatie bestaat uit de gegevens van de gebruiker zoals naam, lengte en gewicht.

### API’s
Foodfacts en Food van https://www.programmableweb.com/category/nutrition/api
Dit zijn API’s die allerlei informatie bevatten over voedselproducten. Uit de API’s wordt informatie gehaald over de calorieaantallen, deze zijn noodzakelijk voor een calorieteller app. De API’s test ik morgen op werking tijdens het initialiseren van de applicatie! Kan nog veranderen als ze niet werken.
Frameworks
SQLite voor het opslaan van data over de gebruiker, en om de data op te slaan over de geconsumeerde voedselproducten.

### External data
De informatie over caloriehoeveelheden per product komen uit de API. Voor de rest wordt alle informatie door de gebruiker zelf ingevoerd.

### Database tables
#### User
String Name, Integer Age, Date Birthday, Integer Length, String Gender, Integer Weight, Integer WeightGoal, Integer GoalTime

#### FoodItem
String Name, Integer Amount, Integer Calories, Date Date, String Meal
