package validation

import jsonTemplate.ConfigurationTemplate
import org.json.JSONArray
import org.json.JSONObject

class LengthValidation : Validation {

    private val mapOfLengthErrors = mutableMapOf<String, MutableList<String>>()

    override fun validate(jsonArrayData: JSONArray,fieldArray: Array<ConfigurationTemplate>): MutableMap<String, MutableList<String>> {
        val lengthErrors = JSONArray()
        mapOfLengthErrors.clear()
        jsonArrayData.forEachIndexed { index, element ->
            validateLengthInEachRow(element as JSONObject, fieldArray, index, lengthErrors)
        }
        return mapOfLengthErrors
    }

    private fun validateLengthInEachRow(element: JSONObject,fieldArray: Array<ConfigurationTemplate>,index: Int,lengthErrors: JSONArray) {
        val (fieldElement, keys) = getFieldElementsAndKeys(element)
        for (key in keys) {
            val (field, value) = getFieldAndValue(fieldArray, key, fieldElement)
            var flag = true
            when { field.length != "" -> flag = checkLengthForRow(field,value)}
            getErrorMessages(index, field, lengthErrors, flag)
        }
    }

    private fun getErrorMessages(index: Int, field: ConfigurationTemplate, lengthErrors: JSONArray, flag: Boolean) {
        if (!flag) {
            val jsonObject = errorMessage(index, field)
            lengthErrors.put(jsonObject)
        }
    }

    private fun getFieldElementsAndKeys(element: Any?): Pair<JSONObject, MutableSet<String>> {
        val fieldElement = (element as JSONObject)
        val keys = fieldElement.keySet()
        return Pair(fieldElement, keys)
    }

    private fun getFieldAndValue( fieldArray: Array<ConfigurationTemplate>,key: String?,fieldElement: JSONObject): Pair<ConfigurationTemplate, String> {
        val field = fieldArray.first { it.fieldName == key }
        val value = fieldElement.get(key) as String
        return Pair(field, value)
    }

    private fun checkLengthForRow(field: ConfigurationTemplate, value: String): Boolean {
        val fieldLength = Integer.parseInt(field.length)
        return checkIfLengthIsIncorrect(fieldLength, value)
    }

    private fun lengthCheck(data: String, length: Int): Boolean {
        return length == data.length
    }

    private fun checkIfLengthIsIncorrect(fieldLength: Int, value: String): Boolean {
        if (value != "") {
            return (lengthCheck(value, fieldLength))
        }
        return true
    }

    private fun errorMessage(index: Int, field: ConfigurationTemplate): MutableMap<String, MutableList<String>> {
        when(mapOfLengthErrors[field.fieldName]){null -> mapOfLengthErrors[field.fieldName] = mutableListOf() }
        mapOfLengthErrors[field.fieldName]?.add((index + 2).toString())
        return mapOfLengthErrors
    }
}