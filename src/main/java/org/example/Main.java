package org.example;
import org.example.StateController.*;
import org.example.StateController.Context;

public class Main {
    public static void main(String[] args) {
        Context context = new Context();
        new ChooseInputFormatState(context).handleInput();
    }
}