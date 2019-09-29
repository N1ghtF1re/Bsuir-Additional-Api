package men.brakh.bsuirapi.bot.vk

import com.google.gson.JsonObject
import com.vk.api.sdk.client.TransportClient
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.GroupActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.messages.*
import men.brakh.bsuirapi.bot.AbstractBot
import men.brakh.bsuirapi.bot.extentions.batch
import org.slf4j.LoggerFactory
import java.util.*

class VkBot(groupId: Int,
            token: String,
            threadsCount: Int) : AbstractBot(threadsCount) {
    private var vk: VkApiClient
    private var actor: GroupActor

    private val maxRowSize = 3



    init {
        val transportClient: TransportClient = HttpTransportClient.getInstance()
        vk = VkApiClient(transportClient)
        actor = GroupActor(groupId, token)

        LongPollListener(vk = vk, actor = actor, groupId = groupId)
                .addHandler("message_new") {
                    handleMessage(it)
                }
                .start()
    }

    private fun sendMessage(userId: Int, message: String, keyboard: Keyboard?) {
        val sendMessageRequest = vk.messages().send(actor)
                .randomId(Random().nextInt())
                .userId(userId)
                .message(message)

        keyboard?.let { sendMessageRequest.keyboard(keyboard) }

        sendMessageRequest.execute()
    }


    private fun handleMessage(json: JsonObject) {
        handleMessage(userId = json.get("from_id").asInt, msg = json.get("text").asString)
    }

    override fun sendMessage(userId: Int, message: String) = sendMessage(userId, message, null)

    override fun sendList(userId: Int, values: List<String>, message: String) {
        val keyboard = Keyboard()
                .setOneTime(false)
                .setButtons(
                        values.map { value ->
                            KeyboardButton()
                                    .setAction(KeyboardButtonAction()
                                            .setType(KeyboardButtonActionType.TEXT)
                                            .setLabel(value)
                                    )
                                    .setColor(KeyboardButtonColor.DEFAULT)
                        }.asSequence()
                                .batch(maxRowSize)
                                .toList()
                )

        sendMessage(userId, message, keyboard)
    }
}