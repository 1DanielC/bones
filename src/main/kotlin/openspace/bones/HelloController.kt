package openspace.bones

import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.server.ResponseStatusException

@RestController
class HelloController {

    @GetMapping("/")
    fun root(): Nothing {
        throw ResponseStatusException(HttpStatus.NOT_FOUND)
    }

    @GetMapping("/hello")
    fun hello(): Map<String, String> {
        return mapOf("message" to "hello")
    }
}
