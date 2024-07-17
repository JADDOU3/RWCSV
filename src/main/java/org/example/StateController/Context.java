package org.example.StateController;

import java.util.Scanner;

public class Context {
    private Scanner scanner = new Scanner(System.in);
    private State currentState;

    public Scanner getScanner(){
        return scanner;
    }

    public void setCurrentState(State currentState){
        this.currentState = currentState;
    }

    public void handleInput(){
        currentState.handleInput();
    }


}
