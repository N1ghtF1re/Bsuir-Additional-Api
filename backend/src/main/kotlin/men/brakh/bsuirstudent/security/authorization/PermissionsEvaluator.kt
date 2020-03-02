package men.brakh.bsuirstudent.security.authorization

import org.springframework.security.access.PermissionEvaluator
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

interface EntityPermissionsEvaluator {
    val targetType: String

    fun hasPermission(authentication: Authentication?, targetDomainObject: Any, permission: String): Boolean
    fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        permission: String
    ): Boolean
}

@Component
open class ApplicationPermissionsEvaluator(
    entityPermissionsEvaluators : List<EntityPermissionsEvaluator>
) : PermissionEvaluator {
    private val entityPermissionsEvaluatorsMap: Map<String, EntityPermissionsEvaluator> = entityPermissionsEvaluators
        .map { it.targetType to it }
        .toMap()

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any?, permission: Any): Boolean {
        if ((authentication == null) || (targetDomainObject == null) || permission !is String){
            return false;
        }

        return entityPermissionsEvaluatorsMap[targetDomainObject.javaClass.simpleName]?.hasPermission(
            authentication, targetDomainObject, permission
        ) ?: false
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        targetType: String?,
        permission: Any
    ): Boolean {
        if ((authentication == null) || (targetType == null) || permission !is String) {
            return false;
        }
        return entityPermissionsEvaluatorsMap[targetType]?.hasPermission(
            authentication, targetId, permission
        ) ?: false
    }
}