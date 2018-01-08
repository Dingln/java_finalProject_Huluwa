package final_project;

import java.util.*;

public class Move_record implements Runnable{
    Field myfield;

    Move_record(Field field) throws Exception{
        myfield = field;

        myfield.input = new Scanner(myfield.readfile);
    }

    public void run() {
        myfield.move_record();
    }
}
