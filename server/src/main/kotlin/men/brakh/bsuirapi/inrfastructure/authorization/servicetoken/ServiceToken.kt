package men.brakh.bsuirapi.inrfastructure.authorization.servicetoken

import men.brakh.bsuirapi.inrfastructure.authorization.Permission
import men.brakh.bsuirapicore.model.data.Identifiable

data class ServiceToken(@Transient override val id: Long = -1,
                 val token: String,
                 val description: String,
                 val permissions: List<Permission>) : Identifiable