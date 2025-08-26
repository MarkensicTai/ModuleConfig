package com.moduleconfig.generator.extension

import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.Project

open class ModuleConfigGeneratorExtension(project: Project) {
    
    var packageName: String = ""
    var className: String = "GeneratedConfig"
    
    // 字段容器
    val fields: NamedDomainObjectContainer<ModuleConfigField> = project.container(ModuleConfigField::class.java) { name ->
        ModuleConfigField(name)
    }

    /**
     * DSL 方法：添加字段
     * @param name 字段名
     * @param value 字段值
     */
    fun field(name: String, type: String, value: Any?) {
        val field = ModuleConfigField(name)
        field.type(type)
        field.value(value)
        fields.add(field)
    }

    /**
     * DSL 方法：添加字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun field(name: String, type: String, value: Any?, comment: String) {
        val field = ModuleConfigField(name)
        field.type(type)
        field.value(value)
        field.comment = comment
        fields.add(field)
    }

    /**
     * DSL 方法：字符串字段
     * @param name 字段名
     * @param value 字段值
     */
    fun stringField(name: String, value: String) {
        field(name, "String", "\"$value\"")
    }

    /**
     * DSL 方法：字符串字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun stringField(name: String, value: String, comment: String) {
        field(name, "String", "\"$value\"", comment)
    }

    /**
     * DSL 方法：整数字段
     * @param name 字段名
     * @param value 字段值
     */
    fun intField(name: String, value: Int) {
        field(name, "int", value)
    }

    /**
     * DSL 方法：整数字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun intField(name: String, value: Int, comment: String) {
        field(name, "int", value, comment)
    }

    /**
     * DSL 方法：布尔字段
     * @param name 字段名
     * @param value 字段值
     */
    fun booleanField(name: String, value: Boolean) {
        field(name, "boolean", value)
    }

    /**
     * DSL 方法：布尔字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun booleanField(name: String, value: Boolean, comment: String) {
        field(name, "boolean", value, comment)
    }

    /**
     * DSL 方法：长整数字段
     * @param name 字段名
     * @param value 字段值
     */
    fun longField(name: String, value: Long) {
        field(name, "long", "${value}L")
    }

    /**
     * DSL 方法：长整数字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun longField(name: String, value: Long, comment: String) {
        field(name, "long", "${value}L", comment)
    }

    /**
     * DSL 方法：浮点数字段
     * @param name 字段名
     * @param value 字段值
     */
    fun floatField(name: String, value: Float) {
        field(name, "float", "${value}f")
    }

    /**
     * DSL 方法：浮点数字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun floatField(name: String, value: Float, comment: String) {
        field(name, "float", "${value}f", comment)
    }

    /**
     * DSL 方法：双精度浮点数字段
     * @param name 字段名
     * @param value 字段值
     */
    fun doubleField(name: String, value: Double) {
        field(name, "double", value)
    }

    /**
     * DSL 方法：双精度浮点数字段
     * @param name 字段名
     * @param value 字段值
     * @param comment 字段注解
     */
    fun doubleField(name: String, value: Double, comment: String) {
        field(name, "double", value, comment)
    }

    /**
     * DSL 方法：列表字段
     * @param name 字段名
     * @param type 元素类型
     * @param value 格式 "$var1, $var2, $var3, ..." 以 , 隔开的值
     * @since java9
     */
    fun listField(name: String, type: String, value: String) {
        field(name, "List<$type>", value)
    }

    /**
     * DSL 方法：列表字段
     * @param name 字段名
     * @param type 元素类型
     * @param value 格式 "$var1, $var2, $var3, ..." 以 , 隔开的值
     * @param comment 字段注解
     * @since java9
     */
    fun listField(name: String, type: String, value: String, comment: String) {
        field(name, "List<$type>", value, comment)
    }

    /**
     * DSL 方法：键值对字段
     * @param name 字段名
     * @param keyType key 类型
     * @param valueType value 类型
     * @param value 格式 "$key1 to $value1, $key2 to $value2, $key3 to $value3, ..." 以 , 隔开的 * to * 值
     * @since java9
     */
    fun mapField(name: String, keyType: String, valueType: String, value: String) {
        field(name, "Map<$keyType, $valueType>", value)
    }

    /**
     * DSL 方法：键值对字段
     * @param name 字段名
     * @param keyType key 类型
     * @param valueType value 类型
     * @param value 格式 "$key1 to $value1, $key2 to $value2, $key3 to $value3, ..." 以 , 隔开的 * to * 值
     * @param comment 字段注释
     * @since java9
     */
    fun mapField(name: String, keyType: String, valueType: String, value: String, comment: String) {
        field(name, "Map<$keyType, $valueType>", value, comment)
    }
    
    // DSL 方法：配置字段容器
    fun fields(closure: org.gradle.api.Action<NamedDomainObjectContainer<ModuleConfigField>>) {
        closure.execute(fields)
    }
}