package org.example.StateController;

public enum SpecialInput {
    EXIT("exit"),
    BACK("back");

    private String sympol;

    SpecialInput(String sympol){
        this.sympol = sympol;
    }

    public String getSympol() {
        return sympol;
    }

}
