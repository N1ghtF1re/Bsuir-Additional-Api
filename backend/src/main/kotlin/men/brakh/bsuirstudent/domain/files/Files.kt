package men.brakh.bsuirstudent.domain.files

import men.brakh.bsuirstudent.domain.BaseEntity
import men.brakh.bsuirstudent.domain.iis.student.Student
import javax.persistence.*

enum class FileAccessType {
    GROUP,
    STREAM
}

enum class FileType {
    FILE,
    DIRECTORY,
    LINK
}

@Entity(name = "file")
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "file_type", discriminatorType = DiscriminatorType.STRING)
abstract class AbstractFile(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @Column(name = "file_type", insertable = false, updatable = false)
    @Enumerated(EnumType.STRING)
    open val type: FileType,

    @ManyToOne
    @JoinColumn(name = "student_id", referencedColumnName = "id")
    open val student: Student,

    open var fileName: String,
    open val fileExternalId: String,
    open var accessType: FileAccessType,
    open val groupOwner: String,
    open val mimeType: String,

    @ManyToOne
    @JoinColumn(name = "parent_id", referencedColumnName = "id")
    open var parent: Directory?
): BaseEntity<Int>

@Entity
@DiscriminatorValue("FILE")
class File(
    id: Int?,
    student: Student,
    fileName: String,
    fileExternalId: String,
    accessType: FileAccessType,
    groupOwner: String,
    mimeType: String,
    parent: Directory?
) : AbstractFile(id, FileType.FILE, student, fileName, fileExternalId, accessType, groupOwner, mimeType, parent)


@Entity
@DiscriminatorValue("DIRECTORY")
class Directory(
    id: Int?,
    student: Student,
    fileName: String,
    fileExternalId: String,
    accessType: FileAccessType,
    groupOwner: String,
    mimeType: String,
    parent: Directory?
) : AbstractFile(id,  FileType.DIRECTORY, student, fileName, fileExternalId, accessType, groupOwner, mimeType, parent)

@Entity
@DiscriminatorValue("LINK")
class Link(
    id: Int?,
    student: Student,
    fileName: String,
    fileExternalId: String,
    accessType: FileAccessType,
    groupOwner: String,
    parent: Directory?,
    mimeType: String,
    val link: String
) : AbstractFile(id,  FileType.LINK, student, fileName, fileExternalId, accessType, groupOwner, mimeType, parent)