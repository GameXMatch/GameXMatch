package ch.gamesxmatch.data


class SharedData private constructor() {
    companion object {
        @Volatile
        private lateinit var instance: SharedData
        var tempUUIDSet = false
        var tempUUID = ""
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
    fun getMainUserUUID() : String {
        if(tempUUIDSet){
            return mainUser.uid
        }
        return tempUUID
    }

    fun setTempUUID(uuid : String){
        tempUUID = uuid

    }

    fun setMainUser(user: User) {
        tempUUID = ""
        tempUUIDSet = true
        mainUser = user
    }

    fun getMainUser() : User{
        return mainUser
    }

    fun getInterestedGames(user : User) : ArrayList<Game> {
        var userGames = ArrayList<Game> ()
        for(game in games){
            for(userGame in user.games){
                if(gameUUIDisGame(userGame.id, game)){
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