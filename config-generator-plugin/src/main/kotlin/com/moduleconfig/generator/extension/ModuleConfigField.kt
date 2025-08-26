package com.moduleconfig.generator.extension

import com.moduleconfig.generator.inteface.ConfigValue
import java.io.Serializable

class ModuleConfigField(val name: String) : Serializable {
    companion object {
        private val nullValue = ConfigValue.ObjectValue("Object", "null")
    }

    var configValue: ConfigValue = nullValue
    var comment: String = ""
    var isStatic: Boolean = true
    var isFinal: Boolean = true
    var visibility: String = "public"

    var type: String
        get() = configValue.getType()
        set(_type) {
            configValue = when (_type.lowercase()) {
                "string" -> ConfigValue.StringValue("")
                "int", "integer" -> ConfigValue.IntValue(0)
                "long" -> ConfigValue.LongValue(0L)
                "float" -> ConfigValue.FloatValue(0.0f)
                "double" -> ConfigValue.DoubleValue(0.0)
                "boolean" -> ConfigValue.BooleanValue(false)
                else -> {
                    if (ConfigValue.ListValue.typeCheck(_type)) {
                        ConfigValue.ListValue(_type, "null")
                    } else if (ConfigValue.MapValue.typeCheck(_type)) {
                        ConfigValue.MapValue(_type, "null")
                    } else {
                        ConfigValue.ObjectValue(_type, "null")
                    }
                }
            }
        }
    
    var value: String
        get() = configValue.getValue()
        set(_value) {
            // 根据当前类型设置相应的 ConfigValue
            val currentType = configValue.getType().lowercase()
            configValue = when (currentType) {
                "string" -> ConfigValue.StringValue(_value.removeSurrounding("\""))
                "int" -> ConfigValue.IntValue(_value.toIntOrNull() ?: 0)
                "long" -> ConfigValue.LongValue(_value.toLongOrNull() ?: 0L)
                "float" -> ConfigValue.FloatValue(_value.toFloatOrNull() ?: 0.0f)
                "double" -> ConfigValue.DoubleValue(_value.toDoubleOrNull() ?: 0.0)
                "boolean" -> ConfigValue.BooleanValue(_value.toBooleanStrictOrNull() ?: false)
                else -> {
                    if (ConfigValue.ListValue.typeCheck(configValue.getType())) {
                        ConfigValue.ListValue(configValue.getType(), _value)
                    } else if (ConfigValue.MapValue.typeCheck(configValue.getType())) {
                        ConfigValue.MapValue(configValue.getType(), _value)
                    } else {
                        ConfigValue.ObjectValue(configValue.getType(), _value)
                    }
                }
            }
        }

    fun type(type: String): ModuleConfigField {
        this.type = type
        return this
    }

    fun value(value: Any?): ModuleConfigField {
        this.configValue = when (value) {
            is Int -> {
                require("int" == configValue.getType()) { "类型设置不匹配, 要求 type is ${configValue.getType()} , 发现 value is int" }
                ConfigValue.IntValue(value)
            }
            is Long -> {
                require("long" == configValue.getType()) { "类型设置不匹配, 要求 type is ${configValue.getType()} , 发现 value is long" }
                ConfigValue.LongValue(value)
            }
            is Float -> {
                require("float" == configValue.getType()) { "类型设置不匹配, 要求 type is ${configValue.getType()} , 发现 value is float" }
                ConfigValue.FloatValue(value)
            }
            is Double -> {
                require("double" == configValue.getType()) { "类型设置不匹配, 要求 type is ${configValue.getType()} , 发现 value is double" }
                ConfigValue.DoubleValue(value)
            }
            is Boolean -> {
                require("boolean" == configValue.getType()) { "类型设置不匹配, 要求 type is ${configValue.getType()} , 发现 value is boolean" }
                ConfigValue.BooleanValue(value)
            }
            else -> {
                if ("String" == configValue.getType()) {
                    ConfigValue.StringValue(value.toString())
                } else if (value is String && ConfigValue.ListValue.valueCheck(value)) {
                    if (ConfigValue.ListValue.typeCheck(configValue.getType())) {
                        ConfigValue.ListValue(type, value)
                    } else {
                        ConfigValue.ObjectValue(type, value.toString())
                    }
                } else if (value is String && ConfigValue.MapValue.valueCheck(value)) {
                    if (ConfigValue.MapValue.typeCheck(configValue.getType())) {
                        ConfigValue.MapValue(type, value)
                    } else {
                        ConfigValue.ObjectValue(type, value.toString())
                    }
                } else {
                    ConfigValue.ObjectValue(type, value.toString())
                }
            }
        }
        return this
    }
    
    // 设置注释
    fun comment(comment: String): ModuleConfigField {
        this.comment = comment
        return this
    }
    
    // 设置是否静态
    fun isStatic(isStatic: Boolean): ModuleConfigField {
        this.isStatic = isStatic
        return this
    }
    
    // 设置是否 final
    fun isFinal(isFinal: Boolean): ModuleConfigField {
        this.isFinal = isFinal
        return this
    }
    
    // 设置可见性
    fun visibility(visibility: String): ModuleConfigField {
        this.visibility = visibility
        return this
    }
    
    // 获取修饰符字符串
    fun getModifiers(): String {
        val modifiers = mutableListOf<String>()
        
        if (visibility.isNotEmpty() && visibility != "package") {
            modifiers.add(visibility)
        }
        
        if (isStatic) {
            modifiers.add("static")
        }
        
        if (isFinal) {
            modifiers.add("final")
        }
        
        return modifiers.joinToString(" ")
    }
    
    override fun toString(): String {
        return "ModuleConfigField{name='$name', type='$type', value=$value}"
    }
}