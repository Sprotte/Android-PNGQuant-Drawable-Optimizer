package io.sprotte

import org.gradle.api.Project
import org.pngquant.Image
import org.pngquant.PngQuant

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.awt.image.DataBufferByte
import java.text.DecimalFormat

class PNGQuantOptimizer {

    public long total

    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {

        files.each {

            def originalFileSize = it.length()

            System.out.print('Optimized: ' + it.name)

            try {

                PngQuant attr = new PngQuant()
                attr.setQuality(60, 80)
                attr.setSpeed(1)

                BufferedImage imgIn = null
                imgIn = ImageIO.read(it)

                Image image = new Image(attr, imgIn)
                BufferedImage imgOut = attr.getRemapped(image)

                if (imgOut != null) {
                    try {
                        byte[] imageInBytes = ((DataBufferByte) imgIn.getData().getDataBuffer()).getData();
                        byte[] imageOutBytes = ((DataBufferByte) imgOut.getData().getDataBuffer()).getData();
                        //Debug Stuff to find out if the new version ist bigger than the old but seems to be not working
                        //System.out.print('Lenght IN: ' + imageInBytes.length+"!!!!!!")
                        //System.out.print('Lenght OUT: ' + imageOutBytes.length+"!!!!!!")
                        //System.out.print('BOOL : ' +  (imageInBytes.length > imageOutBytes.length))

                        ImageIO.write(imgOut, "png", it)
                        def newFileSize = it.length()
                        total = total + (originalFileSize - newFileSize)
                        def percent =  (originalFileSize - newFileSize) / (originalFileSize / 100)
                        System.out.print("  ${Helper.bytesToHuman(originalFileSize)} / ${Helper.bytesToHuman(newFileSize)} -> ${Helper.bytesToHuman(originalFileSize - newFileSize)} ~ ${new DecimalFormat("#.00").format(percent)}% \n\r")

                    } catch (IOException e) {
                        e.printStackTrace()
                    }
                }
            } catch (Exception e) {
                e.printStackTrace()
            }
        }
    }
}