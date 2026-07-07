package edu.purdue.rochetlab.labquant.results;

import edu.purdue.rochetlab.labquant.core.ImageFailure;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class FailedImageReportWriter {
    public void write(List<ImageFailure> failures, File target) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(target), "UTF-8"));
        try {
            writer.println("source_path,error_type,message,stacktrace_file");
            for (ImageFailure failure : failures) {
                writer.println(escape(failure.getSourcePath().toString()) + ","
                        + escape(failure.getErrorType()) + ","
                        + escape(failure.getMessage()) + ","
                        + escape(failure.getStacktraceFile()));
            }
        } finally {
            writer.close();
        }
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        if (safe.contains(",") || safe.contains("\"") || safe.contains("\n") || safe.contains("\r")) {
            return "\"" + safe.replace("\"", "\"\"") + "\"";
        }
        return safe;
    }
}
