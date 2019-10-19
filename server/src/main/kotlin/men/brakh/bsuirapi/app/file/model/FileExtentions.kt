package men.brakh.bsuirapi.app.file.model

import men.brakh.bsuirapi.AccessDeniedException
import men.brakh.bsuirapicore.model.data.User

/** EXTENTIONS **/
fun AbstractFile.isAvailable(user: User): Boolean {
    val isTheSameGroup = this.accessType == FileAccessType.GROUP
            && this.ownedGroup == user.education.group

    val isTheSameStream = this.accessType == FileAccessType.STREAM
            && this.ownedGroup.dropLast(1) == user.education.group.dropLast(1)

    return isTheSameGroup || isTheSameStream
}

inline fun AbstractFile.ifAvaiable(user:User, block: (file: AbstractFile) -> Unit) {
    if (!this.isAvailable(user)) {
        throw AccessDeniedException("You can't get access to file ${this.fileName}")
    }
    block(this)
}
