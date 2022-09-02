package ch.gamesxmatch.data


class SharedData private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SharedData
        val tempUUID = "dvBMyNwQHXSvcUrJw7Ak"
        val games = ArrayList<Game>()
        lateinit var mainUser : User
        val matches = ArrayList<User> ()

        fun getInstance(): SharedData {
            synchronized(this) {
                if (!Companion::instance.isInitialized) {
                    instance = SharedData()
                }
                return instance
            }
        }

    }

    fun setGames(gameList : ArrayList<Game>){
        games.addAll(gameList)
        for(game in games){
            game.convertStringToBitmap()
        }
    }

    fun getGames() : ArrayList<Game> {
        return games
    }
    fun getMainUserUUID() : String{
        //return mainUser.uid
        return tempUUID
    }

    fun setMainUser(user: User) {
        mainUser = user
    }

    fun getMainUser() : User{
        return mainUser
    }

    fun getInterestedGames(user : User) : ArrayList<Game> {
        var userGames = ArrayList<Game> ()
        for(game in games){
            for(userGame in user.gamesUIDs){
                if(gameUUIDisGame(userGame, game)){
                    userGames.add(game)
                }
            }
        }
        return userGames
    }

    fun gameUUIDisGame(gameuuid : String, game: Game) : Boolean{
        return gameuuid.contains(game.id)
    }
}