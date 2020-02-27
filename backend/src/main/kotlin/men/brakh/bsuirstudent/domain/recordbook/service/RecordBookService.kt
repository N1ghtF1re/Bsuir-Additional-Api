package men.brakh.bsuirstudent.domain.recordbook.service

import men.brakh.bsuirstudent.domain.recordbook.RecordBookDto

interface RecordBookService {
    fun getMyRecordBook(): RecordBookDto
}