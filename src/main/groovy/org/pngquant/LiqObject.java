package org.pngquant;

import java.util.StringTokenizer;

abstract class LiqObject {
    static {
        //libimagequant.jnilib or libimagequant.so must be in java.library.path
        printPath();
        NativeLibsLoaderUtil.addLibsToJavaLibraryPath("/Users/paul.sprotte/Projekte/Android-Quant/lib/src/main/jniLibs");
        printPath();
        System.loadLibrary("imagequant");
    }

    private static void printPath() {
        String property = System.getProperty("java.library.path");
        StringTokenizer parser = new StringTokenizer(property, ";");
        while (parser.hasMoreTokens()) {
            System.err.println(parser.nextToken());
        }
    }

    long handle;

    /**
     * Free memory used by the library. The object must not be used after this call.
     */
    abstract public void close();

    protected void finalize() throws Throwable {
        close();
    }
}
