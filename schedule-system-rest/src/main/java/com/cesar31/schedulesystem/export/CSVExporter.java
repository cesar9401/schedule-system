package com.cesar31.schedulesystem.export;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

import static com.cesar31.schedulesystem.export.FileExporterUtils.getFileDataRowCount;
import static com.cesar31.schedulesystem.export.FileExporterUtils.verifyFileData;
import static java.lang.String.join;
import static java.util.stream.IntStream.range;

final class CSVExporter {

    private CSVExporter() {
        //no-op
    }

    static ByteArrayOutputStream export(final String delimiter, final LinkedHashMap<String, List<?>> fileData) {
        verifyFileData(fileData);

        final var result = new StringBuilder();
        result.append(join(delimiter, fileData.keySet())).append("\n");

        range(0, getFileDataRowCount(fileData)).forEach(rowIndex -> {
            fileData.keySet()
                    .forEach(columnHeader -> result.append(fileData.get(columnHeader).get(rowIndex))
                            .append(delimiter));
            result.deleteCharAt(result.length() - 1);
            result.append("\n");
        });

        final var outputStream = new ByteArrayOutputStream();
        outputStream.writeBytes(result.toString().getBytes());

        return outputStream;
    }

}
