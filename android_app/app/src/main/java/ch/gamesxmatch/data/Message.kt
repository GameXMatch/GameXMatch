package ch.gamesxmatch.data

/**
 * Stores the data of a message in the chat
 */
data class Message(
    var message: String? = "",
    var name: String? = "",
    var timestamp: Long = 0
)