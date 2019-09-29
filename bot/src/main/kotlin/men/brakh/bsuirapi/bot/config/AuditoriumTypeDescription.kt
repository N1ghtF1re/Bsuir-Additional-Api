package men.brakh.bsuirapi.bot.config

import men.brakh.bsuirapicore.model.data.LessonType

object AuditoriumTypeDescription {
    fun get(type: LessonType): String {
        return when(type) {
            LessonType.LESSON_LECTURE -> StringsConfig.LECTURE_AUDITORIUM_DESCRIPTION
            LessonType.LESSON_LAB -> StringsConfig.LABS_AUDITORIUM_DESCRIPTION
            LessonType.LESSON_PRACTICE -> StringsConfig.PRACTICE_AUDITORIUM_DESCRIPTION
        }
    }
}