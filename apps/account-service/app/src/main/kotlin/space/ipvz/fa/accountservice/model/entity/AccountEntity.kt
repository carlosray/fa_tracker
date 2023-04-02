package space.ipvz.fa.accountservice.model.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

@Table("account")
data class AccountEntity(
    @Id
    val id: Long? = null,
    val name: String,
    val config: AccountConfig,
    val groupId: Long,
    @CreatedDate
    var created: Instant = Instant.now(),
    @CreatedBy
    var createdBy: String = "default",
    @LastModifiedDate
    var modified: Instant = Instant.now(),
    @LastModifiedBy
    var modifiedBy: String = "default"
)