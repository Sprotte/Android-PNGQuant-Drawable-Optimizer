import org.gradle.api.Project
import org.pngquant.Image
import org.pngquant.PngQuant

import javax.imageio.ImageIO
import java.awt.image.BufferedImage
import java.text.DecimalFormat

class PNGQuantOptimizer {

    public static String floatForm (double d)
    {
        return new DecimalFormat("#.##").format(d);
    }

    public static String bytesToHuman (long size)
    {
        long Kb = 1  * 1024;
        long Mb = Kb * 1024;
        long Gb = Mb * 1024;
        long Tb = Gb * 1024;
        long Pb = Tb * 1024;
        long Eb = Pb * 1024;

        if (size <  Kb)                 return floatForm(        size     ) + " byte";
        if (size >= Kb && size < Mb)    return floatForm((double)size / Kb) + " Kb";
        if (size >= Mb && size < Gb)    return floatForm((double)size / Mb) + " Mb";
        if (size >= Gb && size < Tb)    return floatForm((double)size / Gb) + " Gb";
        if (size >= Tb && size < Pb)    return floatForm((double)size / Tb) + " Tb";
        if (size >= Pb && size < Eb)    return floatForm((double)size / Pb) + " Pb";
        if (size >= Eb)                 return floatForm((double)size / Eb) + " Eb";

        return "???";
    }

    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {

        files.each {

            def originalFileSize = it.length()

            try {

                PngQuant attr = new PngQuant();
                attr.setQuality(60,80);
                attr.setSpeed(1);

                BufferedImage img = null;
                img = ImageIO.read(it);

                Image image = new Image(attr, img);

                attr.getRemapped(image);

                try {
                    img = attr.getRemapped(image);
                    ImageIO.write(attr.getRemapped(image), "png", it);

                    def newFileSize = it.length()

                    println "Done " + it.name + " Orignal Size: " + bytesToHuman(originalFileSize) + " New Size: " + bytesToHuman(newFileSize)

                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}