package org.example.StateController.NormalInput;

import org.example.ReadWrite.CSVFileRW;
import org.example.StateController.Context;
import org.example.StateController.ExitState;
import org.example.StateController.State;

import java.util.Scanner;

public class WhereToPrintState implements State {
    private Context context;

    WhereToPrintState(Context context) {
        this.context = context;
    }


    @Override
    public void handleInput() {
        Scanner scanner = context.getScanner();
        System.out.println("The File Is Sorted Successfully");
        System.out.println("Where Do You Want To Print It: \n 1.Print Into A New File        2.Print In Console");
        String whereToPrint = scanner.next();
        if(whereToPrint.toLowerCase().trim().equals("exit"))
            context.setCurrentState(new ExitState(context));
        else if(whereToPrint.toLowerCase().trim().equals("back")){
            context.setCurrentState(new ChooseSortingOrderState(context));
        }
        else if(!whereToPrint.equals("1") && !whereToPrint.equals("2")){
            System.err.println("invalid input");
        }
        else {
            CSVFileRW.setWhereToPrint(Integer.parseInt(whereToPrint));
            context.setCurrentState(new ExitState(context));
            CSVFileRW.wrtieFile();
        }
        context.handleInput();
    }
}
