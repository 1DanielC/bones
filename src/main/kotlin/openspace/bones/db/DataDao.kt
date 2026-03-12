package openspace.bones.db

import openspace.bones.generated.tables.references.BOBS_TABLE
import openspace.bones.objects.Data
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
        .fetchOneInto(Data::class.java)
}