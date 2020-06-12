package dev.jonaz.cloud.components.auth

import com.corundumstudio.socketio.SocketIOClient
import dev.jonaz.cloud.model.DatabaseModel
import dev.jonaz.cloud.util.session.SessionManager
import org.jetbrains.exposed.sql.select
import org.jetbrains.exposed.sql.transactions.transaction
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder

class AuthLogin(private val client: SocketIOClient) : DatabaseModel() {
    private val validateCredentials = ValidateCredentials()
    private val passwordEncoder = BCryptPasswordEncoder()

    fun validateCredentialsAndAddSession(username: String?, password: String?): Pair<Boolean, String> {
        if (username.isNullOrBlank() ||
            password.isNullOrBlank()
        ) return Pair(false, "Invalid credentials")

        val isUsernameValid = validateCredentials.validateUsername(username).first
        val isPasswordValid = validateCredentials.validatePassword(password).first

        if (!isUsernameValid ||
            !isPasswordValid
        ) return Pair(false, "Invalid credentials")

        val user = transaction {
            Users.select { Users.username eq username }.toList()
        }

        val valid = when (user.size) {
            1 -> passwordEncoder.matches(password, user[0][Users.password])
            else -> false
        }

        return when (valid) {
            true -> {
                val sessionToken = SessionManager().create(user[0][Users.username], client)
                Pair(true, sessionToken)
            }
            else -> Pair(false, "Invalid credentials")
        }
    }
}
