package com.example.app.data;

/**
 * Created by John on 2/28/14.
 */
public class DataModel {
    private int rowCount;
    private int colCount;
    private boolean[][] nodes;

    public DataModel(){

    }

    public void setRowCount(int rowCount) {
        this.rowCount = rowCount;
    }

    public void setColumnCount(int colCount) {
        this.colCount = colCount;
    }

    public void setNodes(boolean[][] nodes) {
        this.nodes = nodes;
    }

    public int getRowCount() {
        return rowCount;
    }

    public int getColCount() {
        return colCount;
    }

    public boolean[][] getNodes() {
        return nodes;
    }
}
