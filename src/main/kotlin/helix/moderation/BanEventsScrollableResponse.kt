package helix.moderation

import helix.http.model.array.HelixArrayDTO
import helix.http.model.array.ScrollableResponse
import helix.moderation.model.BanEvent
import io.ktor.client.HttpClient
import io.ktor.client.call.receive
import io.ktor.client.statement.HttpResponse
import kotlinx.coroutines.runBlocking

class BanEventsScrollableResponse(
    httpResponse: HttpResponse,
    httpClient: HttpClient
) :
    ScrollableResponse<BanEvent>(httpResponse, httpClient) {
    override val helixArrayDTO: HelixArrayDTO<BanEvent> = runBlocking {
        httpResponse.receive<HelixArrayDTO<BanEvent>>()
    }

    override suspend fun nextPage(): BanEventsScrollableResponse? =
        nextPageHttpResponse(cursorKey = "after")?.let {
            BanEventsScrollableResponse(
                it, httpClient
            )
        }

}