package org.example.StateController.SQLInput;

import org.example.ReadWrite.CSVFileRW;
import org.example.StateController.*;

import java.util.Scanner;

public class SQLOutputState implements State {
    private final Context context;
    public SQLOutputState(Context context){
        this.context = context;
    }

    @Override
    public void handleInput() {
        Scanner in = new Scanner(System.in);
        System.out.println("The File Is Sorted Successfully");
        System.out.println("Where Do You Want To Print It: \n 1.Print Into A New File        2.Print In Console");
        String input = in.nextLine();
        if (input.equals(SpecialInput.BACK.getSympol())){
            context.setCurrentState(new ChooseInputFormatState(context));
        }
        else if(input.equals(SpecialInput.EXIT.getSympol())){
            context.setCurrentState(new ExitState(context));
        }
        else if(!input.equals("1") && !input.equals("2")){
            System.err.println("invalid input");
        }
        else {
            CSVFileRW.setWhereToPrint(Integer.parseInt(input));
            context.setCurrentState(new ExitState(context));
            CSVFileRW.wrtieFile();
        }
        context.handleInput();
    }
}
