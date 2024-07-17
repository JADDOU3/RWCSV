package org.example.Cells;

import java.sql.Time;

public class StringCell implements Cell<StringCell,String>{
    private String value;

    public StringCell(String a){
        value = a;
    }

    public String getValue(){
        return value;
    }



    @Override
    public String getType() {
        return "string";
    }


    @Override
    public int compare(StringCell cell1) {
        int comparesion = value.compareTo(cell1.getValue());
        if(comparesion > 0){
            return 1;
        }
        else if(comparesion < 0){
            return -1;
        }
        return 0;

    }
}
