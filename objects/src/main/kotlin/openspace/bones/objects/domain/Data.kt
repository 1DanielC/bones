package openspace.bones.objects.domain

import openspace.bones.objects.api.DataModel
import java.util.UUID

data class Data(
    val id: UUID,
    val textField: String,
) {
    fun toModel() = DataModel(id, textField)
}
