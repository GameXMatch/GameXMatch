// Get Games =============================
db.collection("Games")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }

// Get User (**UID**) =============================
val docRef = db.collection("Users").document("**UID**")
docRef.get()
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }

// Get Matches based on games only  (**UID**) =============================
val docRef = db.collection("Users").document("**UID**")
val games = docRef.get("games")
        .addOnSuccessListener { document ->
            if (document != null) {
                Log.d(TAG, "DocumentSnapshot data: ${document.data}")
            } else {
                Log.d(TAG, "No such document")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "get failed with ", exception)
        }


val docRef2 = db.collection("Users")
docRef2.whereArrayContainsAny("games", games).whereNotEqualTo("uid", "**UID**")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }

// (**UID**) Swipe Right on (**UID_SWIPED**) =============================
val uRef = db.collection("Users").document("**UID**")

uRef.update("likes", FieldValue.arrayUnion("**UID_SWIPED**"))
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

// (**UID**) Swipe Left on (**UID_SWIPED**) =============================
val uRef = db.collection("Users").document("**UID**")

uRef.update("dislikes", FieldValue.arrayUnion("**UID_SWIPED**"))
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

// (**UID**) Adds Game (**Gadd**) =============================
val uRef = db.collection("Users").document("**UID**")

uRef.update("games", FieldValue.arrayUnion("**Gadd**"))
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

// (**UID**) Removes Game (**Grem**) =============================
val uRef = db.collection("Users").document("**UID**")

uRef.update("games", FieldValue.arrayRemove("**Gadd**"))
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

// Has (**UID_SWIPED**) Liked (**UID**) =============================
val docRef = db.collection("Users").document("**UID_SWIPED**")

docRef.whereArrayContains("likes", "**UID**")
        .get()
        .addOnSuccessListener { result ->
            for (document in result) {
                Log.d(TAG, "${document.id} => ${document.data}")
            }
        }
        .addOnFailureListener { exception ->
            Log.d(TAG, "Error getting documents: ", exception)
        }

// (**UID**) has changed his description to (**DESC**) =============================
val uRef = db.collection("Users").document("**UID**")

uRef.update("desc", "**DESC**")
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully updated!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error updating document", e) }

// (**UID**) has logged in for the first time and needs to be created =============================
val user = hashMapOf(
        "uid" to "Los Angeles",
        "desc" to "",
        "imageURL" to "",
        "games" to [],
        "likes" to [],
        "dislikes" to []
)

db.collection("Users").document("**UID**")
        .set(user)
        .addOnSuccessListener { Log.d(TAG, "DocumentSnapshot successfully written!") }
        .addOnFailureListener { e -> Log.w(TAG, "Error writing document", e) }