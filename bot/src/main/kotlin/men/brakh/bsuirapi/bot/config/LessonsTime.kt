package men.brakh.bsuirapi.bot.config

import men.brakh.bsuirapi.bot.ResourceLoader
import java.io.InputStream
import java.util.*

object LessonsTime {
    val times: List<String>

    init {
        val properties = Properties()
        val propertiesStream: InputStream = ResourceLoader.load("lessons.properties")!!
        properties.load(propertiesStream)

        val count = properties.getProperty("lessons.count").toInt()

        val timesTempList = mutableListOf<String>()

        for (i in 0 until count) {
            timesTempList.add(properties.getProperty("lessons.$i"))
        }

        times = timesTempList.toList()
    }
}