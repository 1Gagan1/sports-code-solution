# Gracenote Sports Code Test - Solution

Spring Rest controller exposing sports data in CSV files

## Getting Started

Solution Create a RESTful application that exposes the sport data as a set of endpoints as below

1. Gets List of All Players based on team names

http://localhost:8080/team/players/{teamName}

[
    {
        "playerID": 24587,
        "firstName": "David",
        "lastName": "Abraham"
    },
    {
        "playerID": 41562,
        "firstName": "Enis",
        "lastName": "Bunjaki"
    },
    {
        "playerID": 32335,
        "firstName": "Luc",
        "lastName": "Castaignos"
    },
]


2. Gets Games Statics such as league Name, team Name, Goals Scored based on Game ID

http://localhost:8080/gamestats/{gameID}

[
    {
        "leagueId": 9,
        "leagueName": "Premier League",
        "goals": 4,
        "team": {
            "teamID": 564,
            "teamName": "Tottenham",
            "player": null
        }
    },
    {
        "leagueId": 9,
        "leagueName": "Premier League",
        "goals": 1,
        "team": {
            "teamID": 2275,
            "teamName": "Watford",
            "player": null
        }
    }
]


3. Gets Winner Team Name for particular game id

http://localhost:8080/gamestats/winner/{gameID}


[
    {
        "leagueId": 9,
        "leagueName": "Premier League",
        "goals": 4,
        "team": {
            "teamID": 564,
            "teamName": "Tottenham",
            "player": null
        }
    }
]
## Built With

* Spring Boot 2.0.5, Spring JDBC, Java 8
* Maven
* Spring in memory H2 database
* Embedded Tomcat 8
* PostMan
* Jackson JSON Mapper
