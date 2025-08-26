package com.moduleconfig.generator.inteface

import java.io.Serializable

sealed class ConfigValue(open val _type: String) : Serializable {
    companion object {
        const val LIST_TYPE_PATTERN = "List<([a-zA-Z][a-zA-Z0-9_]*(?:\\.[a-zA-Z][a-zA-Z0-9_]*)*)>"
        const val LIST_VALUE_PATTERN = "^\\s*(\"(?:[^\"\\\\]|\\\\.)*\"|[^\\s,]+)\\s*(?:,\\s*(\"(?:[^\"\\\\]|\\\\.)*\"|[^\\s,]+)\\s*)*$"
        const val MAP_TYPE_PATTERN = "Map<([a-zA-Z][a-zA-Z0-9_]*(?:\\.[a-zA-Z][a-zA-Z0-9_]*)*),\\s*([a-zA-Z][a-zA-Z0-9_]*(?:\\.[a-zA-Z][a-zA-Z0-9_]*)*)>"
        const val MAP_VALUE_PATTERN = "^\\s*(?:\"[^\"]*\"|[^,]+?)\\s+to\\s+(?:\"[^\"]*\"|[^,]+?)\\s*(?:,\\s*(?:\"[^\"]*\"|[^,]+?)\\s+to\\s+(?:\"[^\"]*\"|[^,]+?)\\s*)*$"
    }

    data class StringValue(val _value: String) : ConfigValue("String")
    data class IntValue(val _value: Int) : ConfigValue("int")
    data class LongValue(val _value: Long) : ConfigValue("long")
    data class FloatValue(val _value: Float) : ConfigValue("float")
    data class DoubleValue(val _value: Double) : ConfigValue("double")
    data class BooleanValue(val _value: Boolean) : ConfigValue("boolean")
    data class ObjectValue(override val _type: String, val _value: String) : ConfigValue(_type)

    /**
     * 支持的 _value 格式 "$var1, $var2, $var3, ..." 以 , 隔开的值
     * @since 9
     */
    data class ListValue(override val _type: String, val _value: String) : ConfigValue(_type) {
        companion object {
            fun typeCheck(type: String) : Boolean {
                val regex = Regex(LIST_TYPE_PATTERN)
                return regex.matches(type)
            }

            fun valueCheck(value: String) : Boolean {
                val regex = Regex(LIST_VALUE_PATTERN)
                return regex.matches(value)
            }

            fun extractElementType(type: String): String? {
                val regex = Regex(LIST_TYPE_PATTERN)
                val matchResult = regex.find(type.trim())
                return matchResult?.groupValues?.get(1)
            }
        }
    }

    /**
     * 支持的 _value 格式 "$key1 to $value1, $key2 to $value2, $key3 to $value3, ..." 以 , 隔开的 * to * 值
     * @since 9
     */
    data class MapValue(override val _type: String, val _value: String) : ConfigValue(_type) {
        companion object {
            fun typeCheck(type: String) : Boolean {
                val regex = Regex(MAP_TYPE_PATTERN)
                return regex.matches(type)
            }

            fun valueCheck(value: String) : Boolean {
                val regex = Regex(MAP_VALUE_PATTERN)
                return regex.matches(value)
            }

            fun extractElementType(type: String): Pair<String, String>? {
                val regex = Regex(MAP_TYPE_PATTERN)
                val matchResult = regex.find(type.trim())
                return if (matchResult != null && matchResult.groupValues.size >= 3) {
                    val keyType = matchResult.groupValues[1]
                    val valueType = matchResult.groupValues[2]
                    Pair(keyType, valueType)
                } else null
            }
        }
    }

    fun getValue(): String {
        return when (this) {
            is StringValue -> _value
            is IntValue -> _value.toString()
            is LongValue -> "${_value}L"
            is FloatValue -> "${_value}f"
            is DoubleValue -> _value.toString()
            is BooleanValue -> _value.toString().lowercase()
            is ObjectValue -> _value
            is ListValue -> {
                require(ListValue.valueCheck(_value)) {
                    "列表格式不符合, 要求 v1 , v2, v3 ... , 发现 $_value"
                }
                "List.of($_value)"
            }
            is MapValue -> {
                require(MapValue.valueCheck(_value)) {
                    "键值对格式不符合, 要求 key1 to value1, key2 to value2, key3 to value3, ... , 发现 $_value"
                }
                "Map.ofEntries(${
                    _value.split(",").joinToString(", ") {
                        val kV = it.trim().split(" to ")
                        val k = kV[0].trim()
                        val v = kV[1].trim()
                        "Map.entry($k, $v)"
                    }
                });"
            }
        }
    }

    fun getType(): String {
        return when (this) {
            is ListValue -> {
                require(ListValue.typeCheck(_type)) {
                    "列表类型不符合, 要求 List</** className **/> , 发现 $_type"
                }
                _type
            }
            is MapValue -> {
                require(MapValue.typeCheck(_type)) {
                    "键值对类型不符合, 要求 Map</** keyClassName **/, /** valueClassName **/> , 发现 $_type"
                }
                _type
            }
            else -> _type
        }
    }
}