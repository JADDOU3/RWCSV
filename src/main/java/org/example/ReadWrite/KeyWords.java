package org.example.ReadWrite;

public enum KeyWords {
    SELECT("select"),
    FROM("from"),
    WHERE("where"),
    ORDERBY("orderby");

    private String sympol;

    KeyWords(String sympol){
        this.sympol = sympol;
    }

    public String getSympol() {
        return sympol;
    }
}