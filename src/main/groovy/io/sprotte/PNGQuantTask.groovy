package io.sprotte

import groovy.io.FileType
import org.gradle.api.DefaultTask
import org.gradle.api.tasks.Input
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.TaskAction
import org.gradle.api.tasks.incremental.IncrementalTaskInputs
import org.pngquant.Image
import org.pngquant.PngQuant
import java.awt.image.BufferedImage

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
            System.out.println("Hello World!");

            PngQuant attr = new PngQuant();
            attr.setQuality(60,80);
            attr.setSpeed(1);

            BufferedImage img = null;
            img = ImageIO.read(new File("/Users/paul.sprotte/Desktop/PNGQuant-JNITEST/src/io/sprotte/lion_PNG3805.png"));

            Image image = new Image(attr, img);

            attr.getRemapped(image);


            File outputfile = new File("saved.png");
            try {
                ImageIO.write(attr.getRemapped(image), "png", outputfile);
                System.out.println("done");
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }




        def optimizer = new PNGQuantOptimizer()


        inputs.outOfDate {
            def changedFile = it.file
            if (changedFile.isDirectory()) {
                optimizeDirectory(changedFile)
            }else {
                def filePath = changedFile.absolutePath

                if(filePath =~ ~/.*\.png/ && !filePath.contains(".9.png")) {
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