package org.example;


 class  Cells  {
    private String value = null;
    private boolean isDate = false;

     public Cells(){
     }
    public Cells(String value){
        this.value = value;
    }

    public void setValue(String value){
        this.value = value;

    }

    public void checkIfDate(){
         String dateFormat = "\\d{1,2}/\\d{1,2}/\\d{4}";
         if(value.matches(dateFormat))
             isDate = true;
    }

    public boolean getIsDate(){
         return isDate;
    }

    public String getValue(){
        return this.value;
    }
     @Override
     public String toString(){
         return   value ;
     }
}
