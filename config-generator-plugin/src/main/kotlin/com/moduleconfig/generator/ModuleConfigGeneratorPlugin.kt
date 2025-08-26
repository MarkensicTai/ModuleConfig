package com.moduleconfig.generator

import com.moduleconfig.generator.base.BasePlugin
import com.moduleconfig.generator.extension.ModuleConfigGeneratorExtension
import com.moduleconfig.generator.task.GenerateModuleConfigTask
import org.gradle.api.Project
import java.io.File

class ModuleConfigGeneratorPlugin : BasePlugin() {
    
    override fun apply(project: Project) {
        val androidExtension = project.getAndroidComponentsExtension()
        project.createExtension("moduleConfigGenerator", ModuleConfigGeneratorExtension::class.java, project)
        androidExtension.onVariants { variant ->
            project.findExtension(ModuleConfigGeneratorExtension::class.java)?.let { config ->
                val taskName = "generate${variant.name.replaceFirstChar { it.uppercase() }}ModuleConfig"
                val generateTask = project.tasks.register(taskName, GenerateModuleConfigTask::class.java) { task ->
                    task.packageName = config.packageName
                    task.namespace = variant.namespace
                    task.className = config.className
                    task.fields = config.fields.toList()
                    task.outputDir.set(File(project.buildDir, "generated/source/moduleConfig/${variant.name}"))
                }

                // 使用新的 API 将生成的源码添加到编译路径
                variant.sources.java?.addGeneratedSourceDirectory(
                    generateTask,
                    GenerateModuleConfigTask::outputDir
                )
            }
        }
    }
}