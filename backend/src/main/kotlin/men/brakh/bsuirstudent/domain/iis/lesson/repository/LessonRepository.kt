package men.brakh.bsuirstudent.domain.iis.lesson.repository

import men.brakh.bsuirstudent.domain.iis.lesson.Lesson
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.JpaSpecificationExecutor

interface LessonRepository : JpaRepository<Lesson, Int>, JpaSpecificationExecutor<Lesson> {
}