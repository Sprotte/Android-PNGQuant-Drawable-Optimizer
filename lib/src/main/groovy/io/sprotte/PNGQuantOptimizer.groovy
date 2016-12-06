import org.gradle.api.Project

class PNGQuantOptimizer {

    void optimize(Project project, int compressionLevel, int iterations, String logLevel, File[] files) {

        files.each {
            def originalFileSize = it.length()

            println originalFileSize

            println it.name

        }
    }
}