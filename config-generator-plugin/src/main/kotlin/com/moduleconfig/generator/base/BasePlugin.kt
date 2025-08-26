package com.moduleconfig.generator.base

import com.android.build.api.instrumentation.InstrumentationScope
import com.android.build.api.variant.AndroidComponentsExtension
import com.android.build.api.variant.ApplicationAndroidComponentsExtension
import com.android.build.api.variant.DynamicFeatureAndroidComponentsExtension
import com.android.build.api.variant.LibraryAndroidComponentsExtension
import com.android.build.gradle.AppPlugin
import com.android.build.gradle.DynamicFeaturePlugin
import com.android.build.gradle.LibraryPlugin
import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project

abstract class BasePlugin : Plugin<Project> {

    enum class PluginType {
        NON, APP, LIBRARY, DYNAMIC_FEATURE
    }

    private fun Project.checkAndroidPlugin(): PluginType {
        return when {
            this.plugins.hasPlugin(AppPlugin::class.java) -> PluginType.APP
            this.plugins.hasPlugin(LibraryPlugin::class.java) -> PluginType.LIBRARY
            this.plugins.hasPlugin(DynamicFeaturePlugin::class.java) -> PluginType.DYNAMIC_FEATURE
            else -> PluginType.NON
        }
    }

    protected fun <T> Project.createExtension(name: String, clazz: Class<T>, vararg args: Any): T? {
        return this.extensions.create(name, clazz, *args)
    }

    protected fun <T> Project.findExtension(clazz: Class<T>): T? {
        return this.extensions.findByType(clazz)
    }

    protected fun Project.findExtension(name: String): Any? {
        return this.extensions.findByName(name)
    }

    protected fun Project.getAndroidComponentsExtension(): AndroidComponentsExtension<*, *, *> {
        val pluginType = checkAndroidPlugin()
        return when (pluginType) {
            PluginType.APP -> findExtension(ApplicationAndroidComponentsExtension::class.java)
                ?: throw GradleException("ApplicationAndroidComponentsExtension not found")
            PluginType.LIBRARY -> findExtension(LibraryAndroidComponentsExtension::class.java)
                ?: throw GradleException("LibraryAndroidComponentsExtension not found")
            PluginType.DYNAMIC_FEATURE -> findExtension(DynamicFeatureAndroidComponentsExtension::class.java)
                ?: throw GradleException("DynamicFeatureAndroidComponentsExtension not found")
            else -> throw GradleException("please apply 'com.android.library' or 'com.android.application' or 'com.android.dynamic-feature' plugin")
        }
    }

    protected fun Project.getInstrumentationScope(): InstrumentationScope {
        val pluginType = checkAndroidPlugin()
        return when (pluginType) {
            PluginType.APP -> InstrumentationScope.ALL
            PluginType.LIBRARY -> InstrumentationScope.PROJECT
            PluginType.DYNAMIC_FEATURE -> InstrumentationScope.ALL
            else -> throw GradleException("please apply 'com.android.library' or 'com.android.application' or 'com.android.dynamic-feature' plugin")
        }
    }
}