package org.example.StateController.NormalInput;
import org.example.ReadWrite.CSVFileRW;
import org.example.StateController.ChooseInputFormatState;
import org.example.StateController.Context;
import org.example.StateController.ExitState;
import org.example.StateController.State;

import java.util.Scanner;

public class FilePathState implements State {
private Context context;

public FilePathState(Context context){
    this.context = context;
}

    @Override
    public void handleInput() {
    Scanner scanner = new Scanner(System.in);
    System.out.println("Enter The File Path :");
    String filePath = scanner.nextLine();
    if(filePath.toLowerCase().trim().equals("exit")) {
        context.setCurrentState(new ExitState(context));
    }
    else if (filePath.toLowerCase().trim().equals("back")) {
        context.setCurrentState(new ChooseInputFormatState(context));
    } else{
    CSVFileRW.setFilePath(filePath);
    CSVFileRW.readFile();
    context.setCurrentState(new ChooseColumnState(context));
    }
        context.handleInput();

    }
}
