package edu.purdue.rochetlab.labquant.io;

import static org.junit.Assert.assertEquals;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;

public class RecursiveImageFinderTest {
    @Test
    public void findsImagesDeterministicallyByExtension() throws Exception {
        Path root = Files.createTempDirectory("labquant-finder-test");
        try {
            Files.createFile(root.resolve("b.tif"));
            Files.createFile(root.resolve("a.TIFF"));
            Files.createFile(root.resolve("notes.txt"));

            RecursiveImageFinder finder = new RecursiveImageFinder();
            List<Path> paths = finder.find(root, Arrays.asList("tif", ".tiff"), false);

            assertEquals(root.resolve("a.TIFF"), paths.get(0));
            assertEquals(root.resolve("b.tif"), paths.get(1));
            assertEquals(2, paths.size());
        } finally {
            deleteRecursively(root.toFile());
        }
    }

    private static void deleteRecursively(File file) {
        if (file.isDirectory()) {
            File[] children = file.listFiles();
            if (children != null) {
                for (File child : children) {
                    deleteRecursively(child);
                }
            }
        }
        file.delete();
    }
}
