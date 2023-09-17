package com.cesar31.schedulesystem.export;

import java.io.ByteArrayOutputStream;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Clase encargada de exportar un conjunto de datos a {@link ByteArrayOutputStream} con formato CSV.
 * <br>
 * Ejemplo de uso:
 * <pre>{@code
 *     import static com.nabenik.excelfileexporter.CSVFileExporter.aCSVFileExporter;
 *
 *     ...
 *
 *     final new LinkedHashMap<String, List<?>> fileData = getData(); // Obtener la información del reporte.
 *     final var export = aCSVFileExporter(fileData)
 *                             .export();
 *
 *     try (var file = new FileOutputStream("file.csv")) {
 *         file.write(export.toByteArray());
 *     }
 * }</pre>
 */
public class CSVFileExporter {
    private LinkedHashMap<String, List<?>> fileData;
    private String delimiter = ",";

    private CSVFileExporter(final LinkedHashMap<String, List<?>> fileData) {
        this.fileData = fileData;
    }

    /**
     * Método estático para crear una nueva instancia de {@code CSVFileExporter}.
     *
     * @param fileData {@code LinkedHashMap<String, List<?>>} que contiene como llave el nombre de la columna
     *                 y como valor el listado de datos de la columna correspondiente. El mótivo de utilizar
     *                 un {@link LinkedHashMap} es mantener el orden de inserción dentro del mismo.
     * @return {@link CSVFileExporter}
     */
    public static CSVFileExporter aCSVFileExporter(final LinkedHashMap<String, List<?>> fileData) {
        return new CSVFileExporter(fileData);
    }

    /**
     * Método para personalizar carácter delimitador de columnas
     * @param delimiter
     * @return
     */
    public CSVFileExporter withDelimiter(final char delimiter) {
        this.delimiter = String.valueOf(delimiter);
        return this;
    }

    /**
     * Método para generar un {@code ByteArrayOutputStream} en formato CSV.
     *
     * @return {@link ByteArrayOutputStream}
     */
    public ByteArrayOutputStream export() {
        return CSVExporter.export(delimiter, fileData);
    }
}
