package com.cesar31.schedulesystem.export;

public class DataColumn {

    private final String name;
    private final DataType dataType;

    public DataColumn(String name, DataType dataType) {
        this.name = name;
        this.dataType = dataType;
    }

    public String getName() {
        return name;
    }

    public DataType getDataType() {
        return dataType;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;

        DataColumn that = (DataColumn) object;

        if (!name.equals(that.name)) return false;
        return dataType == that.dataType;
    }

    @Override
    public int hashCode() {
        int result = name.hashCode();
        result = 31 * result + dataType.hashCode();
        return result;
    }
}
