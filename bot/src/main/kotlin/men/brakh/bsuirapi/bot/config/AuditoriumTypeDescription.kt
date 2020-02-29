package men.brakh.bsuirapi.bot.config

object AuditoriumTypeDescription {
    fun get(type: String): String {
        return when(type) {
            "LESSON_LECTURE" -> StringsConfig.LECTURE_AUDITORIUM_DESCRIPTION
            "LESSON_LAB" -> StringsConfig.LABS_AUDITORIUM_DESCRIPTION
            "LESSON_PRACTICE" -> StringsConfig.PRACTICE_AUDITORIUM_DESCRIPTION
            else -> ""
        }
    }
}