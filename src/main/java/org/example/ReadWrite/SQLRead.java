package org.example.ReadWrite;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SQLRead {
    private Stack<String> operation = new Stack<>();
    private  List<String> output = new ArrayList<>();
    private List<String> column = new ArrayList<>();

    public SQLRead(String s){
        String[] temp = s.split("\\s+");
        boolean x = false;
        for (int i = 0 ; i < temp.length ; i++) {
            if(temp[i].toLowerCase().equals(KeyWords.FROM.getSympol())){
                CSVFileRW.setFilePath(temp[i + 1]);
                if(!temp[i - 1].toLowerCase().equals(KeyWords.SELECT.getSympol()))
                    setColumn(Arrays.copyOfRange(temp, 0 , i));
            }

            if(temp[i].toLowerCase().equals(KeyWords.WHERE.getSympol())){
                i++;
                x = true;
            }
            if(temp[i].toLowerCase().equals(KeyWords.ORDERBY.getSympol())) {
                CSVFileRW.setSortingBy(temp[i + 1]);
                break;
            }
            else if(x){
                if (Operations.containOperation(temp[i])) {
                    checkPriority(temp[i]);
                    operation.add(temp[i]);
                    if(operation.peek().equals(Operations.PARENTHESESEND.getSymbol()))
                        operation.pop();
                } else {
                    output.add(temp[i]);
                }
            }

        }

    }

    public void checkPriority(String s){
        if(operation.isEmpty())
            return;
        if(s.equals(Operations.PARENTHESESSTART.getSymbol()))
            return;
        else if(s.equals(Operations.PARENTHESESEND.getSymbol()) && operation.contains(Operations.PARENTHESESSTART.getSymbol())){
            while(!operation.peek().equals(Operations.PARENTHESESSTART.getSymbol()))
                output.add(operation.pop());
            operation.pop();
        }
        while(!operation.isEmpty() && Operations.getPrecedence(operation.peek()) >= Operations.getPrecedence(s)){
            output.add(operation.pop());
        }
    }

    public void setColumn(String[] s){
        for(int i = 1 ; i < s.length ; i++)
            column.add(s[i]);
    }

   public void getColumn(){
        CSVFileRW.compareHeader(column);
    }

    public String[] getRules() {
        String[] s = new String[output.size()];
        for(int i = 0 ; i < output.size() ; i++){
            s[i] = output.get(i);
        }
        return s;
    }





}