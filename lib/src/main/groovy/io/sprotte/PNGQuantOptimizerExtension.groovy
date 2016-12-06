import java.util.zip.Deflater

/**
 *  Created by fabiomsr on 12/3/16.
 */
class PNGQuantOptimizerExtension {

    /**
     * The optimizer
     */
    def optimizer = 'zopfli'

    /**
     * The compression level; 0-9 allowed. Default is to try them all by brute force (useful for pngtastic compressor)
     */
    def compressionLevel = Deflater.BEST_COMPRESSION

    /**
     *  Number of compression iterations (useful for zopfli)
     */
    def iterations = 15

    /**
     * The level of logging output (none, debug, info, or error)
     */
    def logLevel = 'info'

    /**
     * Execute only on release build
     */
    def onlyOnRelease = false
}