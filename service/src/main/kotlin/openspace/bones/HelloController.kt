package openspace.bones

import openspace.bones.db.DataDao
import openspace.bones.objects.api.DataModel
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
class HelloController(private val dao: DataDao) {
    @GetMapping("/")
    fun root(): Nothing {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/hello")
    fun hello(): Map<String, String> {
        return mapOf("message" to "hello")
    }

    @GetMapping("/bob/{id}")
    fun bob(
        @PathVariable id: UUID,
    ): DataModel {
        val obj = dao.selectById(id) ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
        return obj.toModel()
    }
}
