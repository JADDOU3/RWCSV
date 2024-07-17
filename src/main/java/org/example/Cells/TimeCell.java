package org.example.Cells;

import java.sql.Time;

public class TimeCell implements Cell<TimeCell, Time>{
    private Time time;

    public TimeCell(String a){
        Time.valueOf(a);
    }

    public Time getValue(){
        return time;
    }


    @Override
    public String getType() {
        return "time";
    }

    @Override
    public int compare(TimeCell cell1) {
        int comparesion = time.compareTo(cell1.getValue());
        if(comparesion > 0){
            return 1;
        }
        else if(comparesion < 0){
            return -1;
        }
        return 0;
    }

}
