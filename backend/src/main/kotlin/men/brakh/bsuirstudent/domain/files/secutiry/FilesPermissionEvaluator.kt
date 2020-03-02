package men.brakh.bsuirstudent.domain.files.secutiry

import men.brakh.bsuirstudent.application.exception.ResourceNotFoundException
import men.brakh.bsuirstudent.domain.files.AbstractFile
import men.brakh.bsuirstudent.domain.files.FileAccessType
import men.brakh.bsuirstudent.domain.files.repository.FilesRepository
import men.brakh.bsuirstudent.domain.iis.student.StudentDto
import men.brakh.bsuirstudent.domain.iis.student.service.StudentService
import men.brakh.bsuirstudent.security.authorization.EntityPermissionsEvaluator
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component
import java.io.Serializable

@Component
class FilesPermissionEvaluator(
    private val repository: FilesRepository,
    private val studentService: StudentService
) : EntityPermissionsEvaluator {
    override val targetType: String = "File"

    private fun AbstractFile.isAvailable(student: StudentDto): Boolean {
        val isTheSameGroup = this.accessType == FileAccessType.GROUP
                && this.groupOwner == student.educationInfo.group

        val isTheSameStream = this.accessType == FileAccessType.STREAM
                && this.groupOwner.dropLast(1) == student.educationInfo.group.dropLast(1)

        return isTheSameGroup || isTheSameStream
    }

    private fun AbstractFile.isCanUpdate(student: StudentDto): Boolean {
        var currFile: AbstractFile? = this;

        while (currFile != null) {
            if (currFile.student.id == student.id) {
                return true;
            }

            currFile = currFile.parent;
        }

        return false
    }

    override fun hasPermission(authentication: Authentication?, targetDomainObject: Any, permission: String): Boolean {
        val currentUser = studentService.getMe()
        val file = targetDomainObject as AbstractFile

        return when (permission) {
            "READ",  "UPLOAD_FILE" -> file.isAvailable(student = currentUser)
            "UPDATE" -> file.isCanUpdate(student = currentUser)
            else -> false
        }
    }

    override fun hasPermission(
        authentication: Authentication?,
        targetId: Serializable?,
        permission: String
    ): Boolean {
        if (targetId == null) {
            return true;
        }

        val file = repository.findByIdOrNull(targetId as Int)
            ?: throw ResourceNotFoundException("File with id $targetId is not found")

        return hasPermission(authentication = authentication, targetDomainObject =  file, permission =  permission)
    }
}