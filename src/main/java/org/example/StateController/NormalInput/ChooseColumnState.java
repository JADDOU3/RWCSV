package org.example.StateController.NormalInput;

import org.example.ReadWrite.CSVFileRW;
import org.example.StateController.Context;
import org.example.StateController.ExitState;
import org.example.StateController.State;

import java.util.Scanner;

public class ChooseColumnState implements State {
    private Context context;
    public ChooseColumnState(Context context){
        this.context = context;
    }

    @Override
    public void handleInput() {
    Scanner scanner = context.getScanner();
    System.out.println("Enter the Index of the column starting from 0");
    CSVFileRW.getHeader();
    String index = scanner.next();
    if(index.toLowerCase().trim().equals("exit"))
        context.setCurrentState(new ExitState(context));
    else if(index.toLowerCase().trim().equals("back")){
        context.setCurrentState(new FilePathState(context));
    }
    else if(Integer.parseInt(index) < 0 || Integer.parseInt(index) > CSVFileRW.getRowSize()){
        System.err.println("invalid input");
    }
    else {
        CSVFileRW.setColumnIndex(Integer.parseInt(index));
        context.setCurrentState(new ChooseSortingOrderState(context));
    }
        context.handleInput();
    }
}
