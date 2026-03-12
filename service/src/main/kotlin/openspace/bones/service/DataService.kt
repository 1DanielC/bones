package openspace.bones.service

import openspace.bones.db.DataDao
import openspace.bones.objects.domain.Data
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException
import java.util.UUID

@Service
data class DataService(val dataDao: DataDao) {
    fun getAllData() = dataDao.selectAll()

    fun getDataOrThrow(id: UUID): Data =
        dataDao.selectById(id)
            ?: throw ResponseStatusException(HttpStatus.NOT_FOUND)

    fun getData(id: UUID): Data? = dataDao.selectById(id)
}
