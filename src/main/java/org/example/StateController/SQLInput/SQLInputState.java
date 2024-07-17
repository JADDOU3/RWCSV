package org.example.StateController.SQLInput;

import org.example.ReadWrite.CSVFileRW;
import org.example.ReadWrite.SQLRead;
import org.example.StateController.*;

import java.util.Scanner;

public class SQLInputState implements State {
    private final Context context;
    public SQLInputState(Context context){
        this.context = context;
    }

    @Override
    public void handleInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("SQL command :");
        String input = in.nextLine();
        if (input.equals(SpecialInput.BACK.getSympol())){
            context.setCurrentState(new ChooseInputFormatState(context));
        }
        else if(input.equals(SpecialInput.EXIT.getSympol())){
            context.setCurrentState(new ExitState(context));
        }
        else {
            if(!input.matches("select\\s+([*\\w+]+\\s+)+[\\w\\\\:]+.csv\\s+where\\s+([\\w\\d>*=<\\()+]+\\s+)+orderby\\s+\\w+")){
                System.out.println("invalid Query please enter again");
                context.handleInput();
            }
            else {
                SQLRead read = new SQLRead(input);
                CSVFileRW.readFile();
                read.getColumn();
                String[] temp = read.getRules();
                CSVFileRW.excuteRule(temp);
                context.setCurrentState(new SQLOutputState(context));
            }
        }
        context.handleInput();
    }
}
