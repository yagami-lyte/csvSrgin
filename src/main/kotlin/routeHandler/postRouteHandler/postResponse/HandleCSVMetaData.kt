package routeHandler.postRouteHandler.postResponse

import Extractor
import com.google.gson.Gson
import database.Connector
import database.DatabaseOperations
import jsonTemplate.ConfigurationTemplate
import routeHandler.ResponseHeader
import routeHandler.StatusCodes
import java.io.BufferedReader
import java.io.File

class HandleCSVMetaData(var fieldArray: Array<ConfigurationTemplate> = arrayOf()) : PostResponse {

    private val responseHeader = ResponseHeader()
    private val extractor = Extractor()

    override fun postResponse(configData : String): String {
        return getResponseForMetaData(configData)
    }

    private fun getResponseForMetaData(body: String): String {
        storeConfigDataInAFile(body)
        fieldArray = getMetaData(body)
        addConfigurationsToDatabase()
        val endOfHeader = "\r\n\r\n"
        val responseBody = "Successfully Added Configuration File"
        val contentLength = responseBody.length
        return responseHeader.getResponseHead(StatusCodes.TWOHUNDRED) + """Content-Type: text/plain; charset=utf-8
    |Content-Length: $contentLength""".trimMargin() + endOfHeader + responseBody
    }

    private fun addConfigurationsToDatabase() {
        val configName = fieldArray.first().configName
        val databaseOperations = DatabaseOperations(Connector())
        if (configName != null && configName != "") {
            databaseOperations.saveNewConfigurationInDatabase(configName)
            writeConfigurationsToDatabase(databaseOperations, configName)
        }
    }

    private fun writeConfigurationsToDatabase(databaseOperations: DatabaseOperations, configName: String) {
        fieldArray.map { databaseOperations.writeConfiguration(configName, it) }
    }

    fun getMetaData(body: String): Array<ConfigurationTemplate> {
        val gson = Gson()
        return gson.fromJson(body, Array<ConfigurationTemplate>::class.java)
    }

    private fun storeConfigDataInAFile(fieldArray: String) {
        val pathToProjectDirectory = System.getProperty("user.dir")
        File("$pathToProjectDirectory/src/main/kotlin/resources/config.json").writeText(fieldArray)
    }

}