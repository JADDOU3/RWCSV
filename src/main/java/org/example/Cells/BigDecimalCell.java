package org.example.Cells;
import java.math.BigDecimal;
import java.sql.Time;

public class BigDecimalCell implements Cell<BigDecimalCell,BigDecimal> {
    private BigDecimal value;

    @Override
    public int compare(BigDecimalCell cell1) {
        int comparesion = value.compareTo(cell1.getValue());
        if(comparesion > 0){
            return 1;
        }
        else if(comparesion < 0){
            return -1;
        }
        return 0;
    }


    public BigDecimal getValue(){
        return value;
    }



    @Override
    public String getType() {
        return "bigdecimal";
    }

    public BigDecimalCell(String a){
        value = new BigDecimal(a);
    }
}
