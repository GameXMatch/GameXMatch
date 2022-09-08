# GameXMatch

Welcome to our github repository of our PDG project 2022 !

**Students :** *Janis Chiffelle, Yanik Lange, Mario Tomic, Ivan Vecerina*

**Professor :** *Bertil Chapuis*

**Assistants :** *Adrien Allemand, James Smith, Didier Page*


## Information about GameXMatch

GameXMatch is a mobile app that lets you search for your brother/sister in arms for multiplayer and co-op games.
The purpose is to match with a player that has the same games/interests/skill level as you and therefore removing the frustation and long waiting hours spend by finding a player that fits you.

## Structure

[.github/](.github) Contains all the worflows.

[android_app/](android_app) Contains the sources of the app.

[android_app/.../adaptor/](android_app/app/src/main/java/ch/gamesxmatch/adaptor) Contains all the adaptor for our classes

[android_app/.../authentication/](android_app/app/src/main/java/ch/gamesxmatch/authentication) Contains all the classes for the authentication with third aprty providers

[android_app/.../data/](android_app/app/src/main/java/ch/gamesxmatch/data) Conatins all the structures that contains data for our classes

[android_app/.../main/](android_app/app/src/main/java/ch/gamesxmatch/main) Contains all the classes for the differents activities

## Setup

- Clone the repo and open it with Android Studio.
- Ask for the Firebase Account credentials to a member of the project.
- Run this command, **./gradlew signingReport**, on your terminal in Android Studio and copy the SHA1.
- Go to the Firebase Account, click on the project **GameXMatch** and click on the application and then the cogwhell : 

![CaptureL](https://user-images.githubusercontent.com/48253621/189080851-306d5c7d-acb6-47f6-b241-2e884b948ef1.PNG)

- Got to the bottom of the page and click on **Add fingerprint**, then add your SHA1 and save.

![Capture](https://user-images.githubusercontent.com/48253621/189080842-8e1cc9ab-4574-4761-97cf-26c65742e634.PNG)

## Developpement process

Please respect this developpement process for your developpement.

- **New** : New feature which should be implemented
- **Backlog** : Feature who need a review, modified
- **In Progress** : Feature who is in developpement
- **In Review** : Feature who is in review
- **Ready** : Feature whi is validated and put on **android_dev** branch
- **Done** : Feature is finished and released on **main** branch

![CaptureS](https://user-images.githubusercontent.com/48253621/189082332-081948ae-4e89-43be-9e77-6778cf90937f.PNG)

## Developpement Documentations

**Developpment specification :** https://docs.google.com/document/d/191YHxBswy8hhTQdy3O6hfsaYRmU11xEqwqKFHRIIMS0/edit#

**Landing page :** https://docs.google.com/document/d/1hIhWbfQUu7bVAG6cm5ziwfVQErA8AcgApWPMfNcTUQ8/edit#heading=h.kbil6u49bvc9

**Mockup :** https://www.figma.com/file/LT0akmI2ndpGRNxqvKgZYB/GameXMatch?node-id=0%3A1
