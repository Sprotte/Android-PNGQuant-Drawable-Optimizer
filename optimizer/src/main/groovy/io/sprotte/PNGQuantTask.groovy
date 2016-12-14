package io.sprotte

import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs
import org.pngquant.NativeLibsLoaderUtil

class PNGQuantTask extends DefaultTask {
    String greeting = 'hello from Sprotte'

    @Input
    def module

    @Input
    def optimizerType

    @InputDirectory
    def drawableDirs

    @Input
    int compressionLevel

    @Input
    int iterations

    String logLevel

    @TaskAction
    void optimize(IncrementalTaskInputs inputs) {
        println ":$module:$name"

        try {
            new File("build/paul/").mkdirs()
            if (!new File("build/paul/libimagequant.jniLib").exists())
                new File("build/paul/", "libimagequant.jniLib") << new URL("https://github.com/Sprotte/Android-PNGQuant-Drawable-Optimizer/raw/master/optimizer/src/main/jniLibs/libimagequant.jnilib").getText()
        } catch (Exception e1) {
            e1.printStackTrace()
        }
        try {
            NativeLibsLoaderUtil.addLibsToJavaLibraryPath("build/paul");
            System.loadLibrary("imagequant");
        } catch (Exception e) {
            e.printStackTrace()
        }
        
        def optimizer = new PNGQuantOptimizer()

        inputs.outOfDate {
            def changedFile = it.file
            if (changedFile.isDirectory()) {
                optimizeDirectory(changedFile)
            } else {
                def filePath = changedFile.absolutePath

                if (filePath =~ ~/.*\.png/ && !filePath.contains(".9.png")) {
                    optimizer.optimize(project, compressionLevel, iterations, logLevel, changedFile)
                }
            }
        }
    }

    def optimizeDirectory(File directory) {

        println directory.absolutePath

        directory.eachFileMatch(~/drawable(.)*/) {
            def imageFiles = []
            it.eachFileMatch(FileType.FILES, ~/.*\.png/) { drawable ->
                if (!drawable.name.contains(".9.png")) {
                    imageFiles << drawable
                }
            }

            if (imageFiles) {
                optimizer.optimize(project, compressionLevel, iterations, logLevel, imageFiles)
            }
        }
    }

}