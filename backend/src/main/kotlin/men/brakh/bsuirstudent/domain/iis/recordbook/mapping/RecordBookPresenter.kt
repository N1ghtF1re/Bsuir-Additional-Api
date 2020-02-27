package men.brakh.bsuirstudent.domain.iis.student.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.iis.recordbook.*
import org.springframework.stereotype.Component

@Component
class RecordBookPresenter : EntityPresenter<RecordBook, RecordBookDto>{
    override fun mapToDto(entity: RecordBook, dtoClass: Class<out RecordBookDto>): RecordBookDto {
        return RecordBookDto(
            number = entity.number,
            averageMark = entity.averageMark,
            diploma = if (entity.diploma.teacher != null && entity.diploma.topic != null)
                DiplomaInfoDto(teacher = entity.diploma.teacher!!, topic = entity.diploma.topic!!)
            else
                null,
            semesters = entity.semesters.map { semester ->
                RecordBookSemesterDto(
                    number = semester.number,
                    averageMark = semester.averageMark,
                    marks = semester.marks.map { mark ->
                        RecordBookMarkDto(
                            mark = mark.mark,
                            teacher = mark.teacher,
                            date = mark.date,
                            formOfControl = mark.formOfControl,
                            hours = mark.hours,
                            retakesCount = mark.retakesCount,
                            statistic = if(mark.statistic.retakeProbability != null || mark.statistic.averageMark != null) SubjectStatisticDto(
                                retakeProbability = mark.statistic.retakeProbability ?: 0.0,
                                averageMark = mark.statistic.averageMark ?: 0.0
                            ) else null,
                            subject = mark.subject
                        )
                    }
                )
            }
        )
    }


}