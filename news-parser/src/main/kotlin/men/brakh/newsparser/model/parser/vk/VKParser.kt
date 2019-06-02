package men.brakh.newsparser.model.parser.vk

import com.google.gson.Gson
import com.vk.api.sdk.client.VkApiClient
import com.vk.api.sdk.client.actors.ServiceActor
import com.vk.api.sdk.httpclient.HttpTransportClient
import com.vk.api.sdk.objects.wall.WallPost
import com.vk.api.sdk.objects.wall.WallPostFull
import com.vk.api.sdk.objects.wall.WallpostAttachmentType.*
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

    private fun parsePost(post: WallPost): String {
        val attachments =  post.attachments?.joinToString { attachement ->
            val replacement = when(attachement.type) {
                PHOTO -> "![Photo ${attachement.photo.id}](${attachement.photo.photo604})"
                POSTED_PHOTO -> "![Photo ${attachement.postedPhoto.id}](${attachement.postedPhoto.photo604})"
                AUDIO -> ""
                VIDEO -> "[${attachement.video.title}](${attachement.video.player})"
                DOC -> "[${attachement.doc.title}](${attachement.doc.url})"
                LINK -> "[${attachement.link.title}](${attachement.link.url})"
                GRAFFITI -> "![Graffiti ${attachement.graffiti.id}](${attachement.graffiti.photo586})"
                POLL -> "[Голосование доступно в источнике]"
                PAGE -> "[${attachement.page.title}](${attachement.page.viewUrl})"
                else -> ""
            }
            "\n$replacement\n"
        } ?: ""

        val content = post.text + attachments
        return content.toMd()
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
                    val photo: String? = wallPost.attachments?.firstOrNull{ it.photo != null }?.photo?.photo604
                    val title: String = wallPost.text.split("\n").first().split("\\.|!|\\?".toRegex()).first()

                    val content = parsePost(wallPost) + (wallPost.copyHistory?.joinToString { parsePost(it) + "\n\n\n" } ?: "")


                    News(
                            title = title,
                            content = content,
                            loadedAt = Date(),
                            publishedAt = wallPost.publicationDate,
                            source = source,
                            url = "https://vk.com/wall${wallPost.ownerId}_${wallPost.id}",
                            urlToImage = photo
                    )
                }

    }

}