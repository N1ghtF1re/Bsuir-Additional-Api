package men.brakh.bsuirstudent.domain.files.mapping

import men.brakh.bsuirstudent.application.mapping.presenter.EntityPresenter
import men.brakh.bsuirstudent.domain.files.AbstractFile
import men.brakh.bsuirstudent.domain.files.FileDto
import men.brakh.bsuirstudent.domain.files.Link
import org.springframework.stereotype.Component

@Component
class FilePresenter : EntityPresenter<AbstractFile, FileDto> {
    override fun mapToDto(entity: AbstractFile, dtoClass: Class<out FileDto>): FileDto {
        val dto = FileDto(
            fileName = entity.fileName,
            groupOwner = entity.groupOwner,
            mimeType = entity.mimeType,
            type = entity.type.toString(),
            accessType = entity.accessType.toString(),
            id = entity.id!!,
            parentFileId = entity.parent?.id,
            studentIisId = entity.student.iisId,
            studentName = "${entity.student.firstName} ${entity.student.lastName}"
        )

        if (entity is Link) {
            dto.link = entity.link
        }

        return dto;
    }
}