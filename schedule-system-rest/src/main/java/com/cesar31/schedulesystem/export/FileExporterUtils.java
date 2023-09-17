package com.cesar31.schedulesystem.export;

import java.util.List;
import java.util.Map;

final class FileExporterUtils {

    private FileExporterUtils() {
        // no-op
    }

    static void verifyFileData(final Map<?, List<?>> fileData) {
        if (fileData.isEmpty()) {
            throw new IllegalStateException("La información para generar el archivo esta vacia");
        }

        final var rowSize = fileData.values().stream()
                                    .map(List::size)
                                    .findFirst()
                                    .orElse(0);

        final var allSizeMatches = fileData.values()
                                           .stream()
                                           .map(List::size)
                                           .allMatch(size -> size.equals(rowSize));

        if (!allSizeMatches) {
            throw new IllegalStateException("Todas las columnas deben tener el mismo tamaño de filas");
        }
    }

    static int getFileDataRowCount(final Map<?, List<?>> fileData) {
        return fileData.values().stream()
                       .findAny()
                       .map(List::size)
                       .orElse(0);
    }
}
