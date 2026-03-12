package openspace.bones.controller

import openspace.bones.objects.api.DataModel
import openspace.bones.service.DataService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@RestController
class HelloController(private val service: DataService) {
    @GetMapping("/")
    fun root(): Nothing {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/api/hello")
    fun hello(): Map<String, String> {
        return mapOf("message" to "hello")
    }

    @GetMapping("/api/data/{id}")
    fun getData(
        @PathVariable id: UUID,
    ): DataModel {
        return service.getDataOrThrow(id).toModel()
    }
}
