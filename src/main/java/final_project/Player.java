package final_project;

import java.awt.*;
import java.net.URL;
import java.util.concurrent.*;
import final_project.Field.*;

import javax.swing.*;

public class Player implements Runnable {
    private Location location;
    private Location printlocation;
    private Creature creature;
    private Field myfield;

    private final int OFFSET = 80;
    private final int BX = 0, BY = 0;

    public int getX() {
        return location.getX();
    }

    public int getY() {
        return location.getY();
    }

    Player(Field field) {
        creature = new Creature();
        location = new Location();
        printlocation = new Location();
        myfield = field;
    }

    Player(Creature cre) {
        creature = cre;
        location = new Location();
    }

    void set_location(Location loc) {
        location = loc;
    }

    public Creature getCreature() {
        return creature;
    }

    void set_location(int x, int y) {
        location.setX(x);
        location.setY(y);
        printlocation.setX(BX + x * OFFSET);
        printlocation.setY(BY + y * OFFSET);
    }

    public Location getLocation() {
        return location;
    }

    public Location getPrintlocation() {
        return printlocation;
    }

    public void dead() {
        this.creature.setImage(this.creature.getDeadimage());
        this.creature.setIs_alive(false);

        // change image;
//        try {
//            wait();
//        }catch (Exception ex) {
//            ex.printStackTrace();
//        }
    }

    void move() {
        int x = this.location.getX(), y = this.location.getY();
        switch(this.creature.direction) {
            case UP: y--; break;
            case DOWN: y++; break;
            case LEFT: x--; break;
            case RIGHT: x++; break;
            case NE: x++; y--; break;
            case NW: x--; y--; break;
            case SE: x++; y++; break;
            case SW: x--; y++; break;
        }
        int flag = myfield.moveable(x, y, this);
    }

    public void run() {
        try {
            while(this.creature.is_alive && !myfield.isIs_end()) {
                move();
                myfield.repaint();
//                Thread.yield();
                TimeUnit.MILLISECONDS.sleep(150);
            }
        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    void set_creature(Creature cre) {
        creature = cre;
    }

    void print() {
        creature.display();
    }

    int get_Priority() {
        return Thread.currentThread().getPriority();
    }
    void set__Priority(int pri) {
        Thread.currentThread().setPriority(pri);
    }

}
