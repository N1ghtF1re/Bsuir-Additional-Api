package men.brakh.bsuirstudent.domain.iis.recordbook.mapping

import men.brakh.bsuirstudent.application.bsuir.DiplomaBsuirDto
import men.brakh.bsuirstudent.application.bsuir.MarkBookBsuirDto
import men.brakh.bsuirstudent.application.bsuir.MarkPageBsuirDto
import men.brakh.bsuirstudent.domain.iis.recordbook.*
import org.springframework.stereotype.Component
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.roundToInt


@Component
class RecordBookBsuirMapping() {
    private fun String.toDate(): Date? {
        return try {
            val date = SimpleDateFormat("dd.MM.yyyy").parse(this)
            val calendar = Calendar.getInstance()
            calendar.time = date
            calendar.set(Calendar.HOUR_OF_DAY, 12)
            calendar.time
        } catch (e: ParseException) {
            null
        }
    }

    fun mapToRecordBook(markBookBsuirDto: MarkBookBsuirDto, diplomaBsuirDto: DiplomaBsuirDto, id: Int? = null): RecordBook {
        val recordBook = RecordBook(
            id =  id,
            number = markBookBsuirDto.number,
            averageMark = markBookBsuirDto.averageMark
        )

        recordBook.diploma = DiplomaInfo(
            recordBook = recordBook,
            teacher = diplomaBsuirDto.name,
            topic = diplomaBsuirDto.theme
        )

        val semesters = mapSemesters(markBookBsuirDto, recordBook)

        recordBook.semesters = semesters

        return recordBook
    }

    private fun mapSemesters(
        markBookBsuirDto: MarkBookBsuirDto,
        recordBook: RecordBook
    ): MutableList<RecordBookSemester> {
        return markBookBsuirDto.markPages.mapNotNull { (semester, marksPage) ->
            val recordBookSemester = RecordBookSemester(
                number = semester.toInt(),
                averageMark = marksPage.averageMark,
                recordBook = recordBook
            )

            val marks = mapMarks(marksPage, recordBookSemester)
            recordBookSemester.marks = marks

            if (marksPage.isEmpty())
                null
            else
                recordBookSemester

        }.toMutableList()
    }

    private fun mapMarks(
        marksPage: MarkPageBsuirDto,
        semester: RecordBookSemester
    ): List<RecordBookMark> {
        return marksPage.marks.mapNotNull {
            if (it.formOfControl != null) {
                val mark = RecordBookMark(
                    subject = it.subject,
                    date = it.date.toDate(),
                    formOfControl = it.formOfControl,
                    hours = it.hours.toDoubleOrNull()?.roundToInt(),
                    mark = if (it.mark == "") null else it.mark,
                    retakesCount = it.retakesCount,
                    teacher = it.teacher,
                    semester = semester
                )

                val statistic =
                    SubjectStatistic(retakeProbability = it.commonRetakes, averageMark = it.commonMark, mark = mark)
                mark.statistic = statistic
                mark
            } else {
                null
            }
        }
    }
}