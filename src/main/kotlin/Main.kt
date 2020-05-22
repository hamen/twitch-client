import helix.auth.AuthService
import helix.auth.model.AuthScope
import helix.auth.model.request.OauthAuthorizeRequestModel
import helix.clips.ClipService
import helix.games.GameService
import helix.http.credentials.DefaultApiSettings
import helix.http.credentials.OauthApiCredentials
import helix.streams.StreamService
import helix.users.UserService
import io.ktor.client.engine.apache.ApacheEngineConfig
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.ImplicitReflectionSerializer
import kotlinx.serialization.Properties
import kotlinx.serialization.UnstableDefault
import java.time.Instant
import java.time.temporal.ChronoUnit

@OptIn(UnstableDefault::class)
@ImplicitReflectionSerializer
fun main() {
//    authenticateUser()
    val apiSettings = DefaultApiSettings(
        Properties.store(
            OauthApiCredentials(
                "kyoim5wj84e8m9ujeqa04b8wlyizkg",
                "nyufzvu4k8h80iq0r7ya4zx1fsas7d"
            )
        )
    )
    val streamsService = StreamService(apiSettings, ApacheEngineConfig())
    val userService = UserService(apiSettings, ApacheEngineConfig())
    runBlocking {
        val user = userService.getUser("frozencure").resource
        user?.let {
            val streams =
                streamsService.createStreamMarker(user.id)
            println(streams.resource)
        }
    }

}


@ImplicitReflectionSerializer
fun authenticateUser() {
    val requestModel = OauthAuthorizeRequestModel(
        "nyufzvu4k8h80iq0r7ya4zx1fsas7d", // client-id
        "http://localhost", // redirect-URI
        "token", // response type
        AuthScope.values().toList()
    )
    val authService = AuthService(ApacheEngineConfig())
    runBlocking {
        val response = authService.authorizeAppForUser(requestModel)
        println(response)
    }
}

