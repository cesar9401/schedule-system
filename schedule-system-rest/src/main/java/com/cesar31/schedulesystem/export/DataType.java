package com.cesar31.schedulesystem.export;

/**
 * Define el tipo de dato de una columna de un archivo de Excel
 */
public enum DataType {
    /**
     * DataType para booleanos. Para representar un valor <code>true</code> se debe de enviar el valor String <code>"true"</code>.
     * La conversión al tipo booleano ignora el uso de mayúsculas y minúsculas.
     */
    BOOLEAN,

    /**
     * DataType para fecha en formato <code>yyyy-MM-dd</code>. Ejemplo <code>2023-01-31</code>.
     */
    LOCAL_DATE,

    /**
     * DataType para fechas en formato <code>yyyy-MM-ddTHH:mm:ss</code>. Ejemplo <code>2023-01-31T16:32:35</code>.
     */
    LOCAL_DATE_TIME,

    /**
     * DataType para números enteros y decimales. Ejemplo <code>22.50</code>.
     */
    NUMBER,

    /**
     * DataType para texto incluyento caracteres especiales.
     */
    STRING
}
