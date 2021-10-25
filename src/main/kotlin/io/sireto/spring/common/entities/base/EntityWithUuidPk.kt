package io.sireto.spring.common.entities.base

import org.hibernate.annotations.GenericGenerator
import java.util.*
import javax.persistence.*

@MappedSuperclass
abstract class EntityWithUuidPk{
    @Id
    @GenericGenerator(name = "UUIDGenerator", strategy = "uuid2")
    @GeneratedValue(generator = "UUIDGenerator")
//    @Column(columnDefinition = "BINARY(16)")
    var uuid: UUID = UUID.randomUUID()
}