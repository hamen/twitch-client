package games

import helix.games.GameHelixResponse
import helix.games.GameService
import helix.games.GamesHelixResponse
import helix.games.ScrollableGamesResponse
import io.ktor.client.statement.request
import kotlinx.coroutines.runBlocking
import org.junit.Test
import util.HttpClientMockBuilder

class `Given GET game with id is called` {

    private val id: Long = 493057

    private val gameResponse = runBlocking<GameHelixResponse> {
        GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.SINGLE_GAME))
            .getGame(id)
    }

    @Test
    fun `then request has id as parameter`() =
        assert(gameResponse.httpResponse.request.url.parameters["id"] == id.toString())


    @Test
    fun `then game is returned`() =
        assert(gameResponse.resource != null)
}

class `Given GET game with name is called` {

    private val name: String = "PLAYERUNKNOWN'S BATTLEGROUNDS"

    private val gameResponse = runBlocking<GameHelixResponse> {
        GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.SINGLE_GAME))
            .getGame(name)
    }

    @Test
    fun `then request has name as parameter`() =
        assert(gameResponse.httpResponse.request.url.parameters["name"] == name)


    @Test
    fun `then game is returned`() =
        assert(gameResponse.resource != null)

}

class `Given GET games with ids is called` {

    private val ids = listOf<Long>(493057, 493242)

    private val gamesResponse = runBlocking<GamesHelixResponse> {
        GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.MULTIPLE_GAMES))
            .getGames(ids = ids)
    }

    @Test
    fun `then request has user ids as parameters`() {
        gamesResponse.httpResponse.request.url.parameters.getAll("id")?.let {
            assert(
                it == ids.map { v -> v.toString() }
            )
        }
    }

    @Test
    fun `then users are returned`() =
        assert(gamesResponse.data.size == 2)
}

class `Given GET games with names is called` {

    private val names = listOf<String>("PLAYERUNKNOWN'S BATTLEGROUNDS", "League of Legends")

    private val gamesResponse = runBlocking<GamesHelixResponse> {
        GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.MULTIPLE_GAMES))
            .getGames(names = names)
    }

    @Test
    fun `then request has user ids as parameters`() {
        gamesResponse.httpResponse.request.url.parameters.getAll("name")?.let {
            assert(
                it == names
            )
        }
    }

    @Test
    fun `then users are returned`() =
        assert(gamesResponse.data.size == 2)

}


class `Given GET top games is called` {

    private val gamesResponse = runBlocking<ScrollableGamesResponse> {
        GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.MULTIPLE_GAMES_WITH_PAGINATION))
            .getTopGames()
    }

    @Test
    fun `then request first as parameter`() =
        assert(gamesResponse.httpResponse.request.url.parameters["first"] != null)

    @Test
    fun `then games are returned`() =
        assert(gamesResponse.data.size == 2)

    @Test
    fun `then pagination exists`() =
        assert(gamesResponse.pagination != null)


    class `And next paged is retrieved` {
        private val gamesResponse = runBlocking<ScrollableGamesResponse?> {
            GameService(HttpClientMockBuilder.withJsonContent(GamesTestData.MULTIPLE_GAMES_WITH_PAGINATION))
                .getTopGames().nextPage()
        }

        @Test
        fun `then request has after as parameter`() =
            assert(gamesResponse?.httpResponse?.request?.url?.parameters?.get("after") != null)

        @Test
        fun `then games are returned`() =
            assert(gamesResponse?.data?.size == 2)

    }

}

