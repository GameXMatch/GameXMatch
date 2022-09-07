package ch.gamesxmatch.data

import com.google.firebase.firestore.FirebaseFirestore

/**
 * Singleton storing the different data that might be used between different fragments
 * and some utility functions
 */
class SharedData private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SharedData
        var tempUUID = "" // TODO, refactor
        val games = ArrayList<Game>()
        lateinit var mainUser : User

        fun getInstance(): SharedData {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = SharedData()
                }
                return instance
            }
        }

    }

    /**
     * Sets the supported games of the app. To be called once, at the start of the app
     */
    fun setGames(gameList : ArrayList<Game>){
        games.clear()
        games.addAll(gameList)
        for(game in games){
            game.convertStringToBitmap()
        }
    }

    /**
     * Returns all the games the app has to offer
     */
    fun getGames() : ArrayList<Game> {
        return games
    }

    /**
     * Gets the current user's uuid
     */
    fun getMainUserUUID() : String {
        return tempUUID
    }

    /**
     * Sets the user's uuid. To be called during the log in
     */
    fun setTempUUID(uuid : String){
        tempUUID = uuid
    }

    /**
     * Sets the main user 
     */
    fun setMainUser(user: User) {
        tempUUID = user.uid
        mainUser = user
    }

    /**
     * Returns the main user
     */
    fun getMainUser() : User{
        return mainUser
    }

    /**
     * Utility function that returns the list of games the user is interested in
     */
    fun getInterestedGames(user : User) : ArrayList<Game> {
        var userGames = ArrayList<Game> ()
        for(game in games){
            for(userGame in user.games){
                var dbGame = FirebaseFirestore.getInstance().document(userGame)
                if(gameUUIDisGame(dbGame.id, game)){
                    userGames.add(game)
                }
            }
        }
        return userGames
    }

    /**
     * Utility function that checks if a game object corresponds to the game uuid
     */
    fun gameUUIDisGame(gameuuid : String, game: Game) : Boolean{
        return gameuuid.contains(game.id)
    }
}