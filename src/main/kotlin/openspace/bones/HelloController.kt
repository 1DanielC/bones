package openspace.bones

import openspace.bones.db.DataDao
import openspace.bones.objects.Data
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/bob")
    fun bob(): Data = dao.selectById(UUID.fromString("2c27e161-7796-4592-86a2-14cf1bd287e4"))
        ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)
}
