package final_project;

import sun.java2d.CRenderer;

import javax.swing.*;
import java.awt.*;
import java.lang.*;
import java.net.URL;

class Creature implements Cloneable{
    protected String name;
    protected Image image;
    protected Image deadimage;
    public enum Direction {UP, DOWN, LEFT, RIGHT, NE, NW, SE, SW};
    public enum Stand {GOOD, EVIL};
    Direction direction;
    Stand stand;
    protected boolean is_alive;
    public int strength;

    Creature() {
        image = null;
        is_alive = true;
    }

    public void setDeadimage(Image deadimage) {
        this.deadimage = deadimage;
    }

    public Image getDeadimage() {
        return deadimage;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public boolean isIs_alive() {
        return is_alive;
    }

    public void setIs_alive(boolean is_alive) {
        this.is_alive = is_alive;
    }

    void display() {

        System.out.print(name);
    }

    @Override
    public Creature clone() {
        Creature cre = null;
        try{
            cre = (Creature) super.clone();
        }catch (CloneNotSupportedException ex) {
            ex.printStackTrace();
        }
        return cre;
    }
}

class Huluwa_Entity extends Creature {
    static int count = 1;
    int rank;

    Huluwa_Entity() {
        rank = count++;
        name = "\uD83D\uDC76";
        direction = Direction.LEFT;
        stand = Stand.GOOD;
        strength = 5;
    }
}

class Scorpion_Entity extends Creature {
    Scorpion_Entity() {
        name = "\uD83E\uDD82";
        direction = Direction.RIGHT;
        stand = Stand.EVIL;
        strength = 3;

        URL url = this.getClass().getClassLoader().getResource("l.png");
        ImageIcon igi = new ImageIcon(url);
        setImage(igi.getImage());
        url = this.getClass().getClassLoader().getResource("ld.png");
        igi = new ImageIcon(url);
        deadimage = igi.getImage();
    }
}

class Grandpa_Entity extends Creature {
    Grandpa_Entity() {
        name = "\uD83C\uDF85";
        direction = Direction.LEFT;
        stand = Stand.GOOD;
        strength = 8;

        URL url = this.getClass().getClassLoader().getResource("grandpa.png");
        ImageIcon igi = new ImageIcon(url);
        setImage(igi.getImage());
        url = this.getClass().getClassLoader().getResource("grandpad.png");
        igi = new ImageIcon(url);
        deadimage = igi.getImage();
    }
}

class Snake_Entity extends Creature {
    Snake_Entity() {
        name = "\uD83D\uDC0D";
        direction = Direction.RIGHT;
        stand = Stand.EVIL;
        strength = 7;

        URL url = this.getClass().getClassLoader().getResource("snake.png");
        ImageIcon igi = new ImageIcon(url);
        setImage(igi.getImage());
        url = this.getClass().getClassLoader().getResource("snaked.png");
        igi = new ImageIcon(url);
        deadimage = igi.getImage();

    }
}

class LS_Entity extends Creature {
    LS_Entity() {
        name = "\uD83D\uDC0D";
        direction = Direction.RIGHT;
        stand = Stand.EVIL;
        strength = 8;

        URL url = this.getClass().getClassLoader().getResource("scorpion.png");
        ImageIcon igi = new ImageIcon(url);
        setImage(igi.getImage());
        url = this.getClass().getClassLoader().getResource("scorpiond.png");
        igi = new ImageIcon(url);
        deadimage = igi.getImage();

    }
}

