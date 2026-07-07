package edu.purdue.rochetlab.labquant.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Set;

public class RecursiveImageFinder {
    public List<Path> find(Path inputFolder, List<String> extensions, boolean recursive) throws IOException {
        final List<Path> matches = new ArrayList<Path>();
        final Set<String> normalizedExtensions = normalizeExtensions(extensions);
        if (recursive) {
            Files.walkFileTree(inputFolder, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    if (matchesExtension(file, normalizedExtensions)) {
                        matches.add(file);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } else {
            Files.walkFileTree(inputFolder, Collections.<java.nio.file.FileVisitOption>emptySet(), 1,
                    new SimpleFileVisitor<Path>() {
                        @Override
                        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                            if (matchesExtension(file, normalizedExtensions)) {
                                matches.add(file);
                            }
                            return FileVisitResult.CONTINUE;
                        }
                    });
        }
        Collections.sort(matches);
        return matches;
    }

    private static Set<String> normalizeExtensions(List<String> extensions) {
        Set<String> normalized = new HashSet<String>();
        for (String extension : extensions) {
            String value = extension == null ? "" : extension.trim().toLowerCase(Locale.US);
            if (value.startsWith(".")) {
                value = value.substring(1);
            }
            if (!value.isEmpty()) {
                normalized.add(value);
            }
        }
        return normalized;
    }

    private static boolean matchesExtension(Path path, Set<String> extensions) {
        String fileName = path.getFileName() == null ? "" : path.getFileName().toString();
        int dot = fileName.lastIndexOf('.');
        if (dot < 0 || dot == fileName.length() - 1) {
            return false;
        }
        return extensions.contains(fileName.substring(dot + 1).toLowerCase(Locale.US));
    }
}
