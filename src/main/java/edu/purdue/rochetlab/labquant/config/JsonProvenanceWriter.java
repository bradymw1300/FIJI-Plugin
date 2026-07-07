package edu.purdue.rochetlab.labquant.config;

import edu.purdue.rochetlab.labquant.core.WorkflowConfig;
import edu.purdue.rochetlab.labquant.core.WorkflowSummary;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class JsonProvenanceWriter {
    public void writeWorkflowConfig(WorkflowConfig config, File target) throws IOException {
        PrintWriter writer = writer(target);
        try {
            writer.println("{");
            writer.println("  \"inputFolder\": \"" + json(config.getInputFolder().getAbsolutePath()) + "\",");
            writer.println("  \"outputFolder\": \"" + json(config.getOutputFolder().getAbsolutePath()) + "\",");
            writer.println("  \"recursive\": " + config.isRecursive() + ",");
            writer.println("  \"fileExtensions\": \"" + json(config.getFileExtensions().toString()) + "\",");
            writer.println("  \"exportQc\": " + config.isExportQc() + ",");
            writer.println("  \"assayId\": \"" + json(config.getAssayId()) + "\"");
            writer.println("}");
        } finally {
            writer.close();
        }
    }

    public void writeRunManifest(WorkflowSummary summary, File target) throws IOException {
        PrintWriter writer = writer(target);
        try {
            writer.println("{");
            writer.println("  \"createdAtUtc\": \"" + timestampUtc() + "\",");
            writer.println("  \"discoveredImages\": " + summary.getDiscoveredImages() + ",");
            writer.println("  \"completedImages\": " + summary.getCompletedImages() + ",");
            writer.println("  \"failedImages\": " + summary.getFailedImages() + ",");
            writer.println("  \"cancelled\": " + summary.isCancelled());
            writer.println("}");
        } finally {
            writer.close();
        }
    }

    private static PrintWriter writer(File target) throws IOException {
        return new PrintWriter(new OutputStreamWriter(new FileOutputStream(target), "UTF-8"));
    }

    private static String timestampUtc() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        format.setTimeZone(TimeZone.getTimeZone("UTC"));
        return format.format(new Date());
    }

    private static String json(String value) {
        return value == null ? "" : value.replace("\\", "\\\\").replace("\"", "\\\"");
    }
}
