package dev.jonaz.cloud.components.auth

class ValidateCredentials {
    operator fun Regex.contains(username: CharSequence): Boolean = this.matches(username)

    fun validateUsername(username: String): Pair<Boolean, String?> {
        when (username) {
            !in Regex("[a-zA-Z0-9 ]*") -> return Pair(false, "The user name can only be alphanumeric")
        }

        when (username.length) {
            in 16..Int.MAX_VALUE -> return Pair(false, "Your username is too long")
            in Int.MIN_VALUE..3 -> return Pair(false, "Your username is too short")
        }

        return Pair(true, null)
    }

    fun validatePassword(password: String): Pair<Boolean, String?> {
        when (password.length) {
            in 200..Int.MAX_VALUE -> return Pair(false, "Your password is too long")
            in Int.MIN_VALUE..5 -> return Pair(false, "Your password is too short")
        }

        return Pair(true, null)
    }
}
