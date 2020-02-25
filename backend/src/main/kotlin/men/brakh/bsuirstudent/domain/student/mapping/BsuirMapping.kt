package men.brakh.bsuirstudent.domain.student.mapping

import men.brakh.bsuirstudent.domain.bsuir.PersonalCVDto
import men.brakh.bsuirstudent.domain.student.*
import org.springframework.stereotype.Component
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*


@Component
class StudentBsuirMapping() {
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

    fun mapPersonalCvToStudent(bsuirDto: PersonalCVDto): Student {
        val student =  Student(
            firstName = bsuirDto.firstName,
            lastName = bsuirDto.lastName,
            middleName = bsuirDto.middleName,
            birthDay = bsuirDto.birthDate.toDate()!!,
            photo = bsuirDto.photoUrl,
            iisId = bsuirDto.id,
            rating = bsuirDto.rating,
            summary = bsuirDto.summary,
            updatedAt = Date(),
            recordBookNumber = bsuirDto.username
        )

        student.educationInfo = EducationInformation(
            student = student,
            course = bsuirDto.cource,
            faculty = bsuirDto.faculty,
            group = bsuirDto.studentGroup,
            speciality = bsuirDto.speciality
        )

        student.settings = UserSettings(
            student = student,
            isPublicProfile = bsuirDto.published,
            isSearchJob = bsuirDto.searchJob,
            isShowRating =  bsuirDto.showRating
        )

        student.references = bsuirDto.references.map {
            StudentReference(
                iisId = it.id,
                name = it.name,
                reference = it.reference,
                student = student
            )
        }

        student.skills = bsuirDto.skills.map {
            StudentSkill(
                iisId = it.id,
                name = it.name,
                student = student
            )
        }

        return student
    }
}