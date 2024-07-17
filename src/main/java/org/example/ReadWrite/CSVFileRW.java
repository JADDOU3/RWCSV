package org.example.ReadWrite;

import org.example.Cells.*;
import org.example.Cells.Row;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

public class CSVFileRW {
    private static String filePath;
    private static int columnIndex,whereToPrint,sortingOrder;
    private static FileReader file;
    private static List<Row> list = new ArrayList<>();
    private static String sortingBy;



    public static void setFilePath(String filePath) {
        CSVFileRW.filePath = filePath;
    }

    public static void setColumnIndex(int columnIndex) {
        CSVFileRW.columnIndex = columnIndex;
    }

    public static void setWhereToPrint(int whereToPrint){
        CSVFileRW.whereToPrint = whereToPrint;
    }

    public static void setSortingOrder(int sortingOrder){
        CSVFileRW.sortingOrder = sortingOrder;
    }

    public static int getRowSize(){
        return list.get(0).cellList.size();
    }


    private static boolean isValidBigDecimal(String a){
        try {
            new BigDecimal(a);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }


    public static void readFile(){
        try {
            file = new FileReader(filePath);
            BufferedReader reader = new BufferedReader(file);
            String line;
            while ((line = reader.readLine()) != null) {
                String[] splits = line.split(",(?!.*[\"])");
                Row row = new Row();
                for (String val : splits) {
                    Cell cell;
                    if(val.matches("\\d{1,2}/\\d{1,2}/\\d{4}")) {
                        cell = new DateCell(val);
                    }
                    else if(val.matches("\\d{2}:\\d{2}:\\d{2}")){
                        cell = new TimeCell(val);
                    }
                    else if(isValidBigDecimal(val)){
                        cell = new BigDecimalCell(val);
                    }
                    else{
                        cell = new StringCell(val);
                    }
                    row.cellList.add(cell);
                       }
                list.add(row);
            }
            reader.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
    }

    public static void sort() {
        if (sortingOrder == 1)
            sortAscending();
        else
            sortDescending();
    }

    public static void sortAscending(){
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (list.get(j).cellList.get(columnIndex).compare(list.get(j + 1).cellList.get(columnIndex)) > 0) {
                    Row temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public static void sortDescending(){
        for (int i = 1; i < list.size() - 1; i++) {
            for (int j = 1; j < list.size() - i; j++) {
                if (list.get(j).cellList.get(columnIndex).compare(list.get(j + 1).cellList.get(columnIndex)) < 0) {
                    Row temp = list.get(j);
                    list.set(j, list.get(j + 1));
                    list.set(j + 1, temp);
                }
            }
        }
    }

    public static void sqlSort(){
        columnIndex = getColumnIndexByName(sortingBy);
        sortAscending();
    }

    private static String type = "string";

    public static void excuteRule(String[] arr){
        List<Row> filteredList = new ArrayList<>();
        filteredList.add(list.get(0));

        for(Row r : list) {
            Stack<String> stack = new Stack<>();
            for (String s : arr) {
                if (!Operations.containOperation(s)) {
                    if (isColumn(s)) {
                        type = r.cellList.get(getColumnIndexByName(s)).getType();
                        stack.add(String.valueOf(r.cellList.get(getColumnIndexByName(s)).getValue()));
                    } else{
                        stack.add(s);
                    }
                }
                else{
                    String right = stack.pop();
                    String left = stack.pop();
                    boolean result = excuteRuleHelper(left, right, s);
                    stack.add(String.valueOf(result));
                }
            }
            if(stack.size() == 1 && Boolean.parseBoolean(String.valueOf(stack.pop())))
                filteredList.add(r);
        }
        list = filteredList;
        sqlSort();
    }

    public static  boolean  excuteRuleHelper(String left , String right , String operation){
        switch (operation){
            case ">" : return greaterOperation(left , right);

            case ">=": return greaterEqualOperation(left, right);

            case "<": return lessOperation(left, right);

            case "<=": return lessEqualOperation(left, right);

            case "=": return left.equals(right);
            case "*":   return ( Boolean.parseBoolean(left) && Boolean.parseBoolean(right));
            case "+": return ( Boolean.parseBoolean(left) || Boolean.parseBoolean(right));
            default:
                throw new IllegalArgumentException("Invalid operation: " + operation);
        }
    }

    public static boolean greaterOperation(String left , String right){
            switch (type){
                case "date" :
                  return Date.valueOf(left).compareTo(Date.valueOf(right)) > 0;
                case "time" :
                    return Time.valueOf(left).compareTo(Time.valueOf(right)) > 0;
                case "bigdecimal" :
                    return  new BigDecimal(left).compareTo(new BigDecimal(right)) > 0;
                default:
                    return left.compareTo(right) > 0;
            }
    }

    public static boolean greaterEqualOperation(String left , String right){
        switch (type){
            case "date" :
                return Date.valueOf(left).compareTo(Date.valueOf(right)) >= 0;
            case "time" :
                return Time.valueOf(left).compareTo(Time.valueOf(right)) >= 0;
            case "bigdecimal" :
               return  new BigDecimal(left).compareTo(new BigDecimal(right)) >= 0;
            default:
                return left.compareTo(right) >= 0;
        }
    }

    public static boolean lessOperation(String left , String right){
        switch (type){
            case "date" :
                return Date.valueOf(left).compareTo(Date.valueOf(right)) < 0;
            case "time" :
                return Time.valueOf(left).compareTo(Time.valueOf(right)) < 0;
            case "bigdecimal" :
                return  new BigDecimal(left).compareTo(new BigDecimal(right)) < 0;
            default:
                return left.compareTo(right) < 0;
        }
    }

    public static boolean lessEqualOperation(String left , String right){
        switch (type){
            case "date" :
                return Date.valueOf(left).compareTo(Date.valueOf(right)) <= 0;
            case "time" :
                return Time.valueOf(left).compareTo(Time.valueOf(right)) <= 0;
            case "bigdecimal" :
                return  new BigDecimal(left).compareTo(new BigDecimal(right)) <= 0;
            default:
                return left.compareTo(right) <= 0;
        }
    }


    public static boolean isColumn(String s){
        if (list.isEmpty() || list.get(0).cellList.isEmpty()) {
            return false;
        }
        for (int i = 0; i < list.get(0).cellList.size(); i++) {
            if (list.get(0).cellList.get(i).getValue().equals(s)) {
                return true;
            }
        }
        return false;
    }


    private static int getColumnIndexByName(String columnName) {
        for (int i = 0; i < list.get(0).cellList.size(); i++) {
            if (columnName.equals(list.get(0).cellList.get(i).getValue())) {
                return i;
            }
        }
       throw new IllegalArgumentException("Column name not found: " + columnName);
    }

    public static void cPrint (){
        for(Row row: list){
            System.out.print("{ ");
            for(Cell cell : row.cellList){
                System.out.print("[ " + cell.getValue() + " ]");
            }
            System.out.println(" }");
        }
        System.out.print("Sorted by : " + list.get(0).cellList.get(columnIndex).getValue());
    }

    public static void fPrint(){
        System.out.println("Enter the Filepath :");
        String path = new Scanner(System.in).nextLine();
        try {
            FileWriter file = new FileWriter(path);
            BufferedWriter buffer = new BufferedWriter(file);
            for (Row row : list) {
                for (Cell cell : row.cellList) {
                    buffer.write(cell.getValue() + ",");
                }
                buffer.newLine();
            }
            buffer.write("Sorted by : " + list.get(0).cellList.get(columnIndex).getValue());
            buffer.close();
            System.out.println("File Saved Successfully");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void wrtieFile(){
        if(whereToPrint == 1)
            fPrint();
        else
            cPrint();
    }

    public static void getHeader(){
            System.out.print("{ ");
            for(int i = 0 ; i < list.get(0).cellList.size();i++){
                System.out.print("[ "+list.get(0).cellList.get(i).getValue()+" ] ");
            }
            System.out.println(" }");
    }

    public static void compareHeader(List<String> s){
        if(list.isEmpty())
            return;

        if(s.get(0).equals("*") || s.isEmpty()){
            return;
        }
        else {
            List<Integer> indexToRemove = new ArrayList<>();
            for (int i = 0; i < list.get(0).cellList.size(); i++) {
                boolean match = false;
                for (String temp : s) {
                    if (list.get(0).cellList.get(i).getValue().equals(temp)) {
                        match = true;
                        break;
                    }
                }
                if(!match){
                  indexToRemove.add(i);
                }
            }
            for(int i = indexToRemove.size() - 1 ; i >= 0 ; i--){
                int m = indexToRemove.get(i);
                for (Row row : list) {
                    if (row.cellList.size() > m) {
                        row.cellList.remove(m);
                    }
                }
            }
        }

    }

    public static void setSortingBy(String s) {
        sortingBy = s;
    }




}

