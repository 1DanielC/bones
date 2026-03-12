package openspace.bones.client

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import openspace.bones.objects.domain.Data
import org.springframework.web.client.RestClient
import java.util.UUID

class BonesClient(
    private val baseUrl: String,
    private val restClient: RestClient = RestClient.create(),
    private val objectMapper: ObjectMapper = jacksonObjectMapper(),
) {
    fun hello(): Map<String, String> {
        val response =
            restClient.get()
                .uri("$baseUrl/hello")
                .retrieve()
                .body(String::class.java)

        @Suppress("UNCHECKED_CAST")
        return objectMapper.readValue(response, Map::class.java) as Map<String, String>
    }

    fun getBob(): Data {
        return restClient.get()
            .uri("$baseUrl/bob")
            .retrieve()
            .body(Data::class.java)!!
    }

    fun getDataById(id: UUID): Data? {
        return try {
            restClient.get()
                .uri("$baseUrl/data/$id")
                .retrieve()
                .body(Data::class.java)
        } catch (e: Exception) {
            null
        }
    }
}
