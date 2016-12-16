package io.sprotte

import com.android.build.gradle.api.BaseVariant
import org.gradle.api.DomainObjectCollection
import org.gradle.api.Plugin
import org.gradle.api.Project

class AndroidQuantPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {
        if (project.plugins.hasPlugin('com.android.application')) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.applicationVariants);
        } else if (project.plugins.hasPlugin('com.android.library')) {
            applyAndroid(project, (DomainObjectCollection<BaseVariant>) project.android.libraryVariants);
        } else {
            throw new IllegalArgumentException('PNGQuant Optimizer plugin requires the Android plugin to be configured');
        }
    }

    private static void applyAndroid(Project project, DomainObjectCollection<BaseVariant> variants) {
        project.extensions.create('pngquantOptimizer', PNGQuantOptimizerExtension)

        def ext = project.extensions['pngquantOptimizer'] as PNGQuantOptimizerExtension

        variants.all { variant ->
            def variantName = variant.name.capitalize()

            if(ext.onlyOnRelease && !'release'.equalsIgnoreCase(variant.buildType.name) ){
                return
            }

            def task = project.tasks.create("optimize${variantName}Drawable", PNGQuantTask) {

                it.description = "PNG Quant optimization"
                it.module = project.name
                it.optimizerType    = ext.optimizer
                it.compressionLevel = ext.compressionLevel
                it.iterations   = ext.iterations
                it.logLevel     = ext.logLevel
                it.drawableDirs = variant.mergeResources.outputDir

            }

            variant.mergeResources.doLast { task.execute() }
        }
    }
}