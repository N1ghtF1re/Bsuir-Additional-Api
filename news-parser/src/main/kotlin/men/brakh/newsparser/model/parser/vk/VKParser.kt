package men.brakh.newsparser.model.parser.vk

import com.google.gson.Gson
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.wall.WallPostFull
import men.brakh.bsuirapicore.model.data.News
import men.brakh.newsparser.config.Config
import men.brakh.newsparser.model.parser.Parser
import java.util.*
import kotlin.math.absoluteValue


abstract class VKParser : Parser {
    abstract val groupId: Int

    private val WallPostFull.publicationDate: Date
        get() = Date(this.date * 1000L)

    private fun String.toMd(): String {
        return this.replace("(\\[)(.*)(\\|)(.*)(\\])".toRegex()) { matchResult ->
            "[${matchResult.groupValues[4]}](https://vk.com/${matchResult.groupValues[2]})"
        }
    }

    override fun parse(lastUpdate: Date): List<News> {
        val transportClient = HttpTransportClient.getInstance()

        val serviceActor = ServiceActor(Config.vkAppId, Config.vkAppToken)
        val vk = VkApiClient(transportClient, Gson(), Int.MAX_VALUE)
        val getResponse = vk.wall().get(serviceActor)
                .ownerId( - groupId.absoluteValue)
                .count(15)
                .execute()


        return getResponse.items
                .filter { wallPostFull -> wallPostFull.publicationDate > lastUpdate  }
                .map { wallPost: WallPostFull ->
                    val photo: String? = wallPost.attachments?.firstOrNull{ it.photo != null }?.photo?.photo1280
                    val title: String = wallPost.text.split("\n").first().split("\\.|!|\\?".toRegex()).first()


                    News(
                            title = title,
                            content = wallPost.text.toMd(),
                            loadedAt = Date(),
                            publishedAt = wallPost.publicationDate,
                            source = source,
                            url = "https://vk.com/wall${wallPost.ownerId}_${wallPost.id}",
                            urlToImage = photo
                    )
                }

    }

}