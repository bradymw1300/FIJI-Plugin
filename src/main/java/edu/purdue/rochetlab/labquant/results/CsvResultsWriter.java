package edu.purdue.rochetlab.labquant.results;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.util.List;

public class CsvResultsWriter {
    public void write(List<MeasurementRecord> records, File target) throws IOException {
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(new FileOutputStream(target), "UTF-8"));
        try {
            writeRow(writer, ResultsSchema.DUMMY_COLUMNS);
            for (MeasurementRecord record : records) {
                for (int i = 0; i < ResultsSchema.DUMMY_COLUMNS.size(); i++) {
                    if (i > 0) {
                        writer.print(",");
                    }
                    writer.print(escape(record.values().get(ResultsSchema.DUMMY_COLUMNS.get(i))));
                }
                writer.println();
            }
        } finally {
            writer.close();
        }
    }

    private static void writeRow(PrintWriter writer, List<String> values) {
        for (int i = 0; i < values.size(); i++) {
            if (i > 0) {
                writer.print(",");
            }
            writer.print(escape(values.get(i)));
        }
        writer.println();
    }

    private static String escape(String value) {
        String safe = value == null ? "" : value;
        if (safe.contains(",") || safe.contains("\"") || safe.contains("\n") || safe.contains("\r")) {
            return "\"" + safe.replace("\"", "\"\"") + "\"";
        }
        return safe;
    }
}
