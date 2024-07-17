package org.example.StateController;

import org.example.ReadWrite.CSVFileRW;
import org.example.StateController.NormalInput.FilePathState;
import org.example.StateController.SQLInput.SQLInputState;

import java.util.Scanner;

public class ChooseInputFormatState implements State{
    private final Context context;
    public ChooseInputFormatState(Context context){
        this.context = context;
    }

    @Override
    public void handleInput() {
        Scanner scanner = context.getScanner();
        System.out.println("Choose an input format :\n1.SQL input   2.normal input");
        String inputFormat = scanner.next();
        if(inputFormat.toLowerCase().trim().equals("exit"))
            context.setCurrentState(new ExitState(context));
        else if(inputFormat.equals("1")){
            context.setCurrentState(new SQLInputState(context));
        }
        else if (inputFormat.equals("2")) {
            context.setCurrentState(new FilePathState(context));
        }
        else {
            System.out.println("invalid input");
        }
        context.handleInput();
    }
}
