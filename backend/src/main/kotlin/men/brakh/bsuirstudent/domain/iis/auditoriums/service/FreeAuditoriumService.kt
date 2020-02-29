package men.brakh.bsuirstudent.domain.iis.auditoriums.service

import men.brakh.bsuirstudent.domain.iis.auditoriums.AuditoriumDto

interface FreeAuditoriumService {
    fun getFreeAuditoriums(building: Int, floor: Int?, time: String?, date: String?): List<AuditoriumDto>
}