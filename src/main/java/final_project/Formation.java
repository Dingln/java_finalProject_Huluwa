package final_project;

import java.util.*;
import java.lang.*;

abstract class Formation {
    Location border;

    Vector<Player> players;
    protected Field myfield;

    Formation(int x, int y, Field field) {
        this.border = new Location(x, y);
        myfield = field;
    }

    public Location getBorder() {
        return border;
    }

    public Vector<Player> getPlayers() {
        return players;
    }
}

class Snake_formation extends Formation {

    Snake_formation(int border_x, int border_y, Vector<? extends Creature> entities, Field field) {
        super(border_x, border_y, field);

        int size = entities.size();

        players = new Vector<>(size);

        int x = border_x;
        int y = border_y;
        for(int i = 0; i < size; i++) {
            players.add(new Player(myfield));  // TO opt
            players.get(i).set_location(x, y++);
            players.get(i).set_creature(entities.get(i));
        }
    }

    public void set_creature(Vector<? extends Creature> entities) {

        for(int i = 0; i < players.size(); i++) {
            players.get(i).set_creature(entities.get(i));
        }
    }
}

class Goose_formation extends Formation {

    Goose_formation(int border_x, int border_y, Creature entity, Field field) {
        super(border_x, border_y, field);

        int size = 6;
        int x = border_x, y = border_y;
        players = new Vector<>(size);

        try {
            for(int i = 0; i < size; i++) {
                players.add(new Player(myfield));
                players.get(i).set_location(x++, y++);
                players.get(i).set_creature(entity.clone());
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}

class Crane_formation extends Formation {
    Crane_formation(int border_x, int border_y, Creature entity, Field field) {
        super(border_x, border_y, field);

        int size = 9;
        int x = border_x, y = border_y;
        players = new Vector<>(size);

        try {
            int i = 0;
            for(; i <= size / 2; i++) {
                players.add(new Player(myfield));
                players.get(i).set_location(x--, y--);
                players.get(i).set_creature(entity.clone());
            }
            x = border_x - 1;
            y = border_y + 1;
            for(; i < size; i++) {
                players.add(new Player(myfield));
                players.get(i).set_location(x--, y++);
                players.get(i).set_creature(entity.clone());
            }

        }catch (Exception ex) {
            ex.printStackTrace();
        }

    }
}

class Single_formation extends Formation {
    Single_formation(int border_x, int border_y, Creature entity, Field field) {
        super(border_x, border_y, field);
        players = new Vector<>(1);
        players.add(new Player(myfield));
        players.get(0).set_location(border_x, border_y);
        players.get(0).set_creature(entity);
    }
}
