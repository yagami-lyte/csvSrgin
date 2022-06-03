package validation

import TypeEnum
import jsonTemplate.ConfigurationTemplate
import org.json.JSONArray
import org.json.JSONObject



class TypeValidation : Validation {

    private val mapOfTypeErrors = mutableMapOf<String, MutableList<String>>()

    private fun validateTypeInEachRow(
        field: ConfigurationTemplate,
        value: String,
    ): Boolean {
        var isTypeValid = true
        val typeMap = createDateTimeMap(field)
        if (value.isNotEmpty() && !TypeEnum.valueOf(field.type.toString())
                .typeCheck(value, typeMap.getOrDefault(field.type.toString(), ""))
        ) {
            isTypeValid = false
        }
        return isTypeValid
    }

    override fun validate(
        jsonArrayData: JSONArray,
        fieldArray: Array<ConfigurationTemplate>
    ): MutableMap<String, MutableList<String>> {
        mapOfTypeErrors.clear()
        jsonArrayData.forEachIndexed { index, element ->
            val (ele, keys) = getElementKeys(element as JSONObject)
            for (key in keys) {
                val (field, value) = getFieldValues(fieldArray, key, ele)
                val isLengthValid = validateTypeInEachRow(field, value)
                getErrorMessages(isLengthValid, index, field)
            }
        }
        return mapOfTypeErrors
    }

    private fun getFieldValues(
        fieldArray: Array<ConfigurationTemplate>,
        key: String,
        ele: JSONObject,
    ): Pair<ConfigurationTemplate, String> {
        val field = fieldArray.first { it.fieldName == key }
        val value = ele.get(key) as String
        return Pair(field, value)
    }

    private fun getElementKeys(element: JSONObject): Pair<JSONObject, MutableSet<String>> {
        val keys = element.keySet()
        return Pair(element, keys)
    }

    private fun getErrorMessages(
        isLengthValid: Boolean,
        index: Int,
        field: ConfigurationTemplate
    ) {
        if (!isLengthValid) {
            when(mapOfTypeErrors[field.fieldName]){ null -> mapOfTypeErrors[field.fieldName] = mutableListOf() }
            mapOfTypeErrors[field.fieldName]?.add((index + 2).toString())
        }
    }

    private fun createDateTimeMap(field: ConfigurationTemplate): Map<String, String?> {
        return mapOf(
            "DateTime" to field.datetime,
            "Date" to field.date,
            "Time" to field.time,
        )
    }
}
