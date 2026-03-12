package openspace.bones.db

import openspace.bones.generated.tables.references.BOBS_TABLE
import openspace.bones.objects.domain.Data
import org.jooq.DSLContext
import org.springframework.stereotype.Repository
import java.util.UUID

@Repository
data class DataDao(private val dsl: DSLContext) {
    private val domainSelect = listOf(
        BOBS_TABLE.ID,
        BOBS_TABLE.TEXT_FIELD
    )

    fun selectById(id: UUID): Data? = dsl
        .select(domainSelect)
        .from(BOBS_TABLE)
        .where(BOBS_TABLE.ID.eq(id))
        .fetchOneInto(Data::class.java)
}