import org.junit.Test;
import final_project.*;

import java.util.Vector;

import static org.junit.Assert.*;

public class FieldTest {

    @Test
    public void testinitWorld() throws Exception{
        Field testfield = new Field(12, 10);
        testfield.initWorld();

        int count1 = 0, count2 = 0;
        for(Vector<Player> units: testfield.getPlayers())
            for(Player item: units)
                count1++;
        for(int i = 0; i < testfield.getW(); i++)
            for(int j = 0; j < testfield.getH(); j++)
                if(testfield.getField()[i][j] == null)
                    count2++;
        assertEquals(120, count1 + count2);
    }
}
