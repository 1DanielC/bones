package openspace.bones.objects.api

data class ErrorResponse(
    val status: Int,
    val error: String,
    val message: String,
)
