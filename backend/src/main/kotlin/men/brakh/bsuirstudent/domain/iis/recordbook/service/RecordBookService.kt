package men.brakh.bsuirstudent.domain.iis.recordbook.service

import men.brakh.bsuirstudent.domain.iis.recordbook.RecordBookDto

interface RecordBookService {
    fun getMyRecordBook(): RecordBookDto
}