package org.example.Cells;

import java.sql.Time;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateCell implements Cell<DateCell , String>{
    private Date date;
    private SimpleDateFormat format;
    public DateCell(String a){
        format = new SimpleDateFormat("d/M/yyyy");
        try {
            date = format.parse(a);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public int compare(DateCell cell1) {
        try {
            int comparesion = date.compareTo(format.parse(cell1.getValue()));
            if(comparesion > 0){
                return 1;
            }
            else if(comparesion < 0){
                return -1;
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return 0;
    }

    public String getValue(){
        return format.format(date);
    }


    @Override
    public String getType() {
        return "date";
    }


}
