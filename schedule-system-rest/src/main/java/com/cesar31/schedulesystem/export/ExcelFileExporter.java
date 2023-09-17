package com.cesar31.schedulesystem.export;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * Clase encargada de exportar un conjunto de datos a un OutputStream con formato Excel.
 * <br>
 * Ejemplo de uso:
 * <pre>{@code
 *     import static com.nabenik.excelfileexporter.ExcelFileExporter.aExcelFileExporter;
 *
 *     ...
 *
 *     final var fileData = new LinkedHashMap<DataColumn, List<?>>();
 *     final var filters = new LinkedHashMap<String, String>();
 *
 *     final var export = aExcelFileExporter(fileData)
 *                                .withTitle("Titulo de reporte")  // Opcional
 *                                .withFilters(filters)            // Opcional
 *                                .export();
 *
 *     try (var file = new FileOutputStream("file.xlsx")) {
 *         file.write(export.toByteArray());
 *     }
 * }</pre>
 */
public class ExcelFileExporter {
    private String title;
    private LinkedHashMap<String, String> filters;
    private LinkedHashMap<DataColumn, List<?>> fileData;

    private ExcelFileExporter(final LinkedHashMap<DataColumn, List<?>> fileData) {
        this.fileData = fileData;
    }

    /**
     * Método estático para crear una nueva instancia de {@code ExcelFileExporter}.
     * @param fileData {@code LinkedHashMap<DataColumn, List<?>>} que contiene como llave un {@link DataColumn}
     *        y como valor el listado de datos de la columna correspondiente. El motivo de utilizar un {@link LinkedHashMap}
     *        es mantener el orden de inserción dentro del mismo.
     * @return {@link ExcelFileExporter}
     */
    public static ExcelFileExporter aExcelFileExporter(final LinkedHashMap<DataColumn, List<?>> fileData) {
        return new ExcelFileExporter(fileData);
    }

    /**
     * Método opcional para definir el título del reporte.
     * @param title Título opcional del reporte
     * @return {@link ExcelFileExporter}
     */
    public ExcelFileExporter withTitle(final String title) {
        this.title = title;
        return this;
    }

    /**
     * Método opcional para definir los filtros del reporte.
     * @param filters Filtros del reporte en formato llave valor dentro de un {@link LinkedHashMap} para asegurar orden.
     * @return {@link ExcelFileExporter}
     */
    public ExcelFileExporter withFilters(final LinkedHashMap<String, String> filters) {
        this.filters = filters;
        return this;
    }

    /**
     * Método para generar un {@code ByteArrayOutputStream} en formato Excel.
     * @return {@link ByteArrayOutputStream}
     * @throws IOException
     */
    public ByteArrayOutputStream export() throws IOException {
        return ExcelExporter.export(title, filters, fileData);
    }
}
