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

Start by asking permission to join the project as Contributor and wait that your demand is accepted. Once done, you avec access to work on the repository of the project.

### Frontend
- Clone the repository and open it with Android Studio.
- Run this command, **./gradlew signingReport**, on your terminal in Android Studio and copy the SHA1 (will be needed to access Firebase later on the Backend part)

### Backend
- Go to to the [firebase website](https://firebase.google.com)
- Login with the credentials of the project (The credentials will be given to you once HR and Tech Department validated your application on the GameXMatch team ;) ). More seriously, ask a member of the team :).(Assistants and Professor will find the credentials on the last page of the Accéder_au_projet.pdf)
- Once logged in click on the console on the right corner and then on the **GameXMatch** Firebase Project.
- Then click on the application as seen in the image below and then on the little cogwhell that will appear : 

![CaptureL](https://user-images.githubusercontent.com/48253621/189080851-306d5c7d-acb6-47f6-b241-2e884b948ef1.PNG)

- In **Project settings -> General**, scroll to the bottom of the page, select the **GameXMatch** android app and click on **Add fingerprint**, then add your SHA1 you saved in the **Frontend** step. Once added, save.

![gamexmatch add sha to firebase](https://user-images.githubusercontent.com/45587342/189119461-6e6215ee-6a72-4c19-833a-ecbcd4853573.png)

Great, now you have your Android Studio environment for development. :)

## Developpement process

### Branches
There are 3 type of branches :


- **main** : production/release branch. Generates a new release. Never work directly on this branch! (Unless you edit the readme like me right now :) )
Once the **android_dev** branch is functionnal and has enough features for a new release, open a PR to merge **android_dev** to **main**. At least one member of the GameXMatch team has to review your PR to be merged. Once merged, github actions will automatically generate a signed .apk and .aab on the release tab of github.

- **android_dev** : The branch of developpment, the most updated, where all the **feature_branches** are merged into. Never work directly on this branch! You will create **features_branches** and merge into android_dev. When you create your **feature_branches** you will always take from **android_dev**. When you have a feature done, create PR to merge your **feature_branch** to **android_dev**. This will automatically start some unit tests to check that te code compiles etc...To be merged the tests need to pass. Furthemore, one member of the GameXmatch team has to review your PR to be able to merge.

- **feature_branches** : All other branches you will see and create. If you want to contribute, this is where it starts. You will have to create a **feature_branch** to start adding a feature. Once your feature is done, you create a PR to merge it to **android_dev**.

### Kanban
Please respect this developpement process for your developpement.

- **New** : New feature which should be implemented
- **Backlog** : Feature who need a review, modified
- **In Progress** : Feature who is in developpement
- **In Review** : Feature who is in review
- **Ready** : Feature whi is validated and put on **android_dev** branch
- **Done** : Feature is finished and released on **main** branch

![CaptureS](https://user-images.githubusercontent.com/48253621/189082332-081948ae-4e89-43be-9e77-6778cf90937f.PNG)

### Issues
As new member of the team, you might start by creating an issue. When creating an issue, make sure that the description of the issue you will work on is clear an understandable. Make also sure you assigned yourself to it (or others if you are a group working on it) and don't forget to select GameXMatch DevLog to add the issue to the kanban like in the image below :

![image](https://user-images.githubusercontent.com/45587342/189128245-a4d069d3-8cea-447e-8885-e2e2072bafdb.png)

Once created, the issue will be put automatically in the **NEW** column of the kanban.

Great! Now create your **feature_branch** for that issue get on your Android Studio IDE and have fun coding on the GameXMatch app ;)

## Developpement Documentations

**Developpment specification :** https://docs.google.com/document/d/191YHxBswy8hhTQdy3O6hfsaYRmU11xEqwqKFHRIIMS0/edit#

**Landing page :** https://docs.google.com/document/d/1hIhWbfQUu7bVAG6cm5ziwfVQErA8AcgApWPMfNcTUQ8/edit#heading=h.kbil6u49bvc9

**Mockup :** https://www.figma.com/file/LT0akmI2ndpGRNxqvKgZYB/GameXMatch?node-id=0%3A1
