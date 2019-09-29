package men.brakh.bsuirapi.bot.vk

import com.google.gson.JsonObject
import com.vk.api.sdk.callback.longpoll.responses.GetLongPollEventsResponse
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.Actor
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.exceptions.ClientException
import com.vk.api.sdk.exceptions.LongPollServerKeyExpiredException
import org.slf4j.LoggerFactory


class LongPollListener(private val vk: VkApiClient,
                       private val actor: Actor,
                       private val groupId: Int) : Thread() {

    private val logger = LoggerFactory.getLogger(LongPollListener::class.java)

    private val handlers: MutableMap<String, (JsonObject) -> Unit> = mutableMapOf()

    private var server: String = ""
    private var key: String = ""
    private var ts: Int = 0

    protected fun connect() {
        val longPollServer = vk.groups()
                .getLongPollServer(actor as GroupActor, groupId)
                .execute()

        server = longPollServer.server
        key = longPollServer.key
        ts = longPollServer.ts
    }

    override fun run() {
        connect()
        while (true) {
            try {
                val response: GetLongPollEventsResponse = vk.longPoll()
                        .getEvents(server, key, ts)
                        .waitTime(30)
                        .execute()

                ts = response.ts
                val updates = response.updates


                updates.forEach {
                    val type = it.get("type").asString

                    logger.info(it.toString())

                    handlers[type]?.invoke(it.get("object").asJsonObject)
                }
            } catch (e: LongPollServerKeyExpiredException) {
                logger.info("Key expired. Regeneration...")
                connect()
            } catch (e: ClientException) {
                logger.error("Long poll error", e)
                connect()
            }

        }
    }

    fun addHandler(event: String, handler: (JsonObject) -> Unit): LongPollListener {
        handlers[event] = handler
        return this
    }
}