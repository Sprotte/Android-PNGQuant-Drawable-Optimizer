package org.pngquant;

import java.lang.reflect.Field;

public final class NativeLibsLoaderUtil {

    private static final String JAVA_LIBRARY_PATH = "java.library.path";
    private static final String SYS_PATHS = "sys_paths";

    public NativeLibsLoaderUtil() {
    }

    public static void addLibsToJavaLibraryPath(final String tmpDirName) {
        try {
            String currentPath = System.getProperty("java.library.path");
            System.setProperty(JAVA_LIBRARY_PATH, tmpDirName);
        /* Optionally add these two lines */
            System.setProperty("jna.library.path", tmpDirName);
            System.setProperty("jni.library.path", tmpDirName);
            final Field fieldSysPath = ClassLoader.class.getDeclaredField(SYS_PATHS);
            fieldSysPath.setAccessible(true);
            fieldSysPath.set(null, null);
        } catch (IllegalAccessException | NoSuchFieldException e) {
            e.printStackTrace();
        }
    }
}