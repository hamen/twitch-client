package users

import helix.http.model.HelixResponse
import helix.users.UserService
import helix.users.model.User
import kotlinx.coroutines.runBlocking
import kotlinx.serialization.UnstableDefault
import util.HttpClientMockBuilder

@OptIn(UnstableDefault::class)
object UsersStubEndpoints {

    private const val BASE_URL = "https://api.twitch.tv/helix/users"

    fun getUserWithEmail(function: (HelixResponse<User>) -> Unit) {
        function(
            runBlocking {
                UserService(
                    HttpClientMockBuilder.withUrl(
                        BASE_URL,
                        """
            {
              "data": [{
                "id": "44322889",
                "login": "dallas",
                "display_name": "dallas",
                "type": "staff",
                "broadcaster_type": "",
                "description": "Just a gamer playing games and chatting. :)",
                "profile_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-profile_image-1a2c906ee2c35f12-300x300.png",
                "offline_image_url": "https://static-cdn.jtvnw.net/jtv_user_pictures/dallas-channel_offline_image-1a2c906ee2c35f12-1920x1080.png",
                "view_count": 191836881,
                "email": "login@provider.com"
              }]
            }
        """.trimIndent()
                    )
                ).getUser()
            })
    }

}