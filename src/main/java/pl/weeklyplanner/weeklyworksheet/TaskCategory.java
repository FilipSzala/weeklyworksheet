package pl.weeklyplanner.weeklyworksheet;

public enum TaskCategory {

    NORMAL(1)
    ,IMPORTANT(2),
    THEMOSTIMPORTANT(3);

    private int value;

    private TaskCategory(int value){

        this.value = value;
    }

    public int getValue(){
        return value;
    }
}
