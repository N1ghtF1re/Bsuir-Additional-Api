package men.brakh.bsuirstudent.domain.iis.group

import men.brakh.bsuirstudent.domain.BaseEntity
import javax.persistence.*

@Entity(name = "group_member")
data class GroupMember(
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    @OneToOne
    @JoinColumn(name = "group_id", referencedColumnName = "id")
    val group: Group,

    val role: String?,
    val name: String,
    val email: String?,
    val phone: String?
): BaseEntity<Int>

@Entity(name = "student_group")
data class Group (
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    override var id: Int? = null,

    val number: String
): BaseEntity<Int> {
    @OneToMany(
        mappedBy = "group",
        cascade = [CascadeType.ALL],
        orphanRemoval = true,
        fetch = FetchType.EAGER
    )
    lateinit var members: List<GroupMember>
}