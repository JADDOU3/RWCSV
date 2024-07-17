package org.example.Cells;

import java.sql.Time;

public interface Cell<T , K > {
    int compare(T cell1);
    K getValue();
    String getType();
}
