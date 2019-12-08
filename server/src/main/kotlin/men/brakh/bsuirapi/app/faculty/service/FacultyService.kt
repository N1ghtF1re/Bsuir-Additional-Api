package men.brakh.bsuirapi.app.faculty.service

import men.brakh.bsuirapi.app.faculty.mapping.toEntitiesList
import men.brakh.bsuirapi.inrfastructure.bsuirapi.BsuirApi
import men.brakh.bsuirapicore.model.data.Faculty

class FacultyService (val bsuirApi: BsuirApi) {
    fun getFaculties(): List<Faculty> {
        return bsuirApi.getFaculties().toEntitiesList(bsuirApi.getSpecialities())
    }
}