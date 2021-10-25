package io.sireto.spring.common.entities.base

import com.fasterxml.jackson.annotation.JsonIgnore
import org.hibernate.annotations.UpdateTimestamp
import java.util.*
import javax.persistence.MappedSuperclass

@MappedSuperclass
abstract class EntityWithTimestampsAndUuidPk: EntityWithUuidPk() {
    @JsonIgnore
    val createdAt: Date = Date()

    @JsonIgnore
    @UpdateTimestamp
    var updatedAt: Date = Date()
}