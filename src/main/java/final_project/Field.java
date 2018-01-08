package final_project;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.net.URL;
import java.util.*;
import java.util.concurrent.*;

import com.sun.org.apache.bcel.internal.generic.SIPUSH;
import final_project.Formation;
import java.io.*;
import java.lang.*;

public class Field extends JPanel {
    private static int height, h;
    private static int width, w;
    private static int count = 1;
    private static int movecount = 0;
    private  Player[][] field;
    private Vector<Vector<Player>> players;
    private boolean start;
    private boolean is_end;
    private boolean is_record;
    private java.io.File recordfile;
    public java.io.File readfile;
    public java.io.PrintWriter output;
    public Scanner input;
    private final int OFFSET = 80;

    private Field myfield = this;
    private Image succeed_image;


    public Field(int width, int height) throws IOException{
        this.width = width * 80;
        this.height = height * 80;
        w = width;
        h = height;
        field = new Player[w][h];
        players = new Vector<>();
        start = true;

        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                field[i][j] = null;

        is_end = false;
        is_record = false;

        recordfile = null;
        readfile = null;
        output = null;
        input = null;

        addKeyListener(new TAdapter());
        setFocusable(true);
//        initWorld();

    }

    synchronized void insert_formation(Formation formation) {
        Vector<Player> player_list = formation.getPlayers();
        players.add(player_list);
        for(Player p: player_list) {
            int x = p.getLocation().getX();
            int y = p.getLocation().getY();
            field[x][y] = p;
        }
    }

    synchronized void outln() {
        if(!is_record) {
            output.println();
            output.flush();
        }
    }

    synchronized public int moveable(int x, int y, Player player) {
        int cx = player.getLocation().getX();
        int cy = player.getLocation().getY();
//        if(field[cx][cy] != player)
//        {
//            System.out.print("loc error!");
//            System.out.print(x + " " + y + " " + cx + " " + cy + " ");
//            System.out.println(field[cx][cy].getLocation().toString());
////            System.exit(-1);
//        }


//        System.out.println(player.getLocation().toString());
        repaint();

        handle_end();

        if(!is_record) {
            output.print(player.getLocation().toString());
        }

        if(y >= h || x >= w || x < 0 || y < 0) {
            int d = 0;
            if(!is_record) {
                d = Math.abs(new java.util.Random().nextInt() % 8);
                output.print(" " + d);
            }
            else {
                d= input.nextInt();
            }
            switch (d) {
                case 0: player.getCreature().direction = Creature.Direction.NW; break;
                case 1: player.getCreature().direction = Creature.Direction.NE; break;
                case 2: player.getCreature().direction = Creature.Direction.SW; break;
                case 3: player.getCreature().direction = Creature.Direction.SE; break;
                case 4: player.getCreature().direction = Creature.Direction.UP; break;
                case 5: player.getCreature().direction = Creature.Direction.LEFT; break;
                case 6: player.getCreature().direction = Creature.Direction.DOWN; break;
                case 7: player.getCreature().direction = Creature.Direction.RIGHT; break;
            }

//            if(x + y == h -1)
//                player.getCreature().direction = Creature.Direction.NW;
//            if(player.getCreature().stand == Creature.Stand.GOOD)
//                player.getCreature().direction = Creature.Direction.NW;
//            else
//                player.getCreature().direction = Creature.Direction.NE;
            outln();
            return -1;
        }
//        else if(x >= w) {
//            if(player.getCreature().stand == Creature.Stand.GOOD)
//                player.getCreature().direction = Creature.Direction.SW;
//            else
//                player.getCreature().direction = Creature.Direction.NW;
//            outln();
//            return -1;
//        }
//        else if(x < 0) {
//            if(player.getCreature().stand == Creature.Stand.GOOD)
//                player.getCreature().direction = Creature.Direction.NE;
//            else
//                player.getCreature().direction = Creature.Direction.SE;
//            outln();
//            return -1;
//        }
//        else if(y < 0) {
//            if(x + y == h -1)
//                player.getCreature().direction = Creature.Direction.SE;
//            if(player.getCreature().stand == Creature.Stand.GOOD)
//                player.getCreature().direction = Creature.Direction.SE;
//            else
//                player.getCreature().direction = Creature.Direction.SW;
//            outln();
//            return -1;
//        }



        if(field[x][y] == null) {
            outln();
            field[x][y] = player;
            player.set_location(x, y);
            field[cx][cy] = null;
            return 1;
        }
        else if(field[x][y].getCreature().stand == player.getCreature().stand) {
            int d = 0;
            if(!is_record) {
                d = Math.abs(new java.util.Random().nextInt() % 8);
                output.print(" " + d);
            }
            else {
                d= input.nextInt();
            }
            switch (d) {
                case 0: player.getCreature().direction = Creature.Direction.NW; break;
                case 1: player.getCreature().direction = Creature.Direction.NE; break;
                case 2: player.getCreature().direction = Creature.Direction.SW; break;
                case 3: player.getCreature().direction = Creature.Direction.SE; break;
                case 4: player.getCreature().direction = Creature.Direction.UP; break;
                case 5: player.getCreature().direction = Creature.Direction.LEFT; break;
                case 6: player.getCreature().direction = Creature.Direction.DOWN; break;
                case 7: player.getCreature().direction = Creature.Direction.RIGHT; break;
            }
            outln();
            return -1;
        }
        else {
            boolean res;
            if(is_record) {
                int r = input.nextInt();
                res = (r == 1);
            }
            else {
                res = this.fight(player, field[x][y]);
                if(res)
                    output.println(" "+1);
                else
                    output.println(" "+2);
            }
            if(res) {
                dead(field[x][y]);
//                field[x][y] = player;
//                player.set_location(x, y);
//                field[cx][cy] = null;
                field[x][y] = null;
                return -1;
            }
            else {
                dead(player);
                dead(field[cx][cy]);
                field[cx][cy] = null;
                return 0;
            }
        }
    };

    synchronized public boolean fight(Player p1, Player p2) {
        int add = p1.getCreature().strength - p2.getCreature().strength;
        int PR = Math.abs(new java.util.Random().nextInt() % 10);
        PR += add;
        if(PR > 5)
            return true;
        else
            return false;
    }

    synchronized void dead(Player p) {
        p.getCreature().setImage(p.getCreature().getDeadimage());
        p.getCreature().setIs_alive(false);
    }

    synchronized boolean ends() {
        boolean goods = true, evils = true;
        for(Vector<Player> units: players)
            for(Player item: units) {
                if(item.getCreature().isIs_alive()) {
                    if (item.getCreature().stand == Creature.Stand.GOOD)
                        evils = false;
                    else
                        goods = false;
                }
            }
        if(goods) {
            URL url = this.getClass().getClassLoader().getResource("hsl.png");
            ImageIcon imi = new ImageIcon(url);
            succeed_image = imi.getImage();
        }
        if(evils) {
            URL url = this.getClass().getClassLoader().getResource("esl.png");
            ImageIcon imi = new ImageIcon(url);
            succeed_image = imi.getImage();
        }
        return (goods ^ evils);
    }

    synchronized void handle_end() {
        if(ends()) {
            repaint();
            is_end = true;
            if(!is_record)
                output.close();
            else
                input.close();

//            try {
//                TimeUnit.MILLISECONDS.sleep(500);
//            }catch (Exception ex) {
//                ex.printStackTrace();
//            }
//            finally {
////                players.clear();
//            }

            setStart(true);
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            }catch (Exception ex) {
                ex.printStackTrace();
            }


        }
    }

    void move_record() {
        if(ends())
            return;
        try {
            int m, n;
            while(input.hasNext()){
                m = input.nextInt();
                n = input.nextInt();
                System.out.println("read "+ m + " " + n);
                field[m][n].move();
                input.nextLine();
                TimeUnit.MILLISECONDS.sleep(20);
                repaint();
            }
            handle_end();

        }catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    synchronized public final void initWorld() {

        players.clear();
        players = new Vector<>();
        field = new Player[w][h];
        for(int i = 0; i < w; i++)
            for(int j = 0; j < h; j++)
                field[i][j] = null;

        start = true;
        is_end = false;


        Snake_formation huluwas = new Snake_formation(8, 0, new Huluwa().huluwas, this);
        Goose_formation evils = new Goose_formation(1, 0, new Scorpion_Entity(), this);
//        Single_formation grandpa = new Single_formation(9, 5, new Grandpa_Entity(), this);
//        Single_formation scorpion = new Single_formation(2, 4, new LS_Entity(), this);
//        Single_formation snake = new Single_formation(4, 2, new Snake_Entity(), this);

        insert_formation(huluwas);
        insert_formation(evils);
//        insert_formation(grandpa);
//        insert_formation(scorpion);
//        insert_formation(snake);
    }

    synchronized public void buildWorld(Graphics g) {
//        g.setColor(new Color(39, 238, 250));
//        g.fillRect(0, 0, this.getW() * 80, this.getH() * 80);

        URL url = this.getClass().getClassLoader().getResource("bgl.png");
        ImageIcon igi = new ImageIcon(url);
        Image image = igi.getImage();
        g.drawImage(image, 0, 0, this);

        for(Vector<Player> units: players)
            for(Player item: units) {
//                if(!item.getCreature().isIs_alive())
//                    continue;
                int x = item.getPrintlocation().getX();
                int y = item.getPrintlocation().getY();
                g.drawImage(item.getCreature().getImage(), x, y, this);
            }
        if(ends()) {
            g.drawImage(succeed_image, height / 4, w / 4, this);
        }
        if(isStart()) {
            URL url2 = this.getClass().getClassLoader().getResource("start.png");
            ImageIcon imi = new ImageIcon(url2);
            Image startimage = imi.getImage();
            g.drawImage(startimage, height / 3, width / 3, this);
        }

    }


    @Override
    synchronized public void paint(Graphics g) {
        super.paint(g);
        buildWorld(g);
//        System.out.println("paint");
    }


    class TAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            int key = e.getKeyCode();
            if(key == KeyEvent.VK_SPACE) {

                initWorld();
                setStart(false);
                setIs_record(false);
                try {
                    if (!is_record) {
                        recordfile = new java.io.File("record" + count + ".txt");
                        output = new java.io.PrintWriter(recordfile);
                        count++;
                    }
                }catch (Exception ex) {
                    ex.printStackTrace();
                }

                ExecutorService exec = Executors.newCachedThreadPool();
                for(Vector<Player> units: players)
                    for(Player unit: units)
                        exec.execute(unit);
                exec.shutdown();
            }
//            else if(key == KeyEvent.VK_R) {
//                is_record = true;
//                ExecutorService exec = Executors.newCachedThreadPool();
//                try {
//                    exec.execute(new Move_record(myfield));
//                }catch (Exception ex) {
//                    ex.printStackTrace();
//                }
//                finally {
//                    exec.shutdown();
//                }
//            }

            repaint();
        }

    }

    synchronized public void setIs_record(boolean is_record) {
        this.is_record = is_record;
    }

    public boolean isStart() {
        return start;
    }

    synchronized public void setStart(boolean start) {
        this.start = start;
    }

    synchronized public boolean isIs_record() {
        return is_record;
    }

    synchronized public boolean isIs_end() {
        return is_end;
    }

    synchronized public  Player[][] getField() {
        return field;
    }

    synchronized public Vector<Vector<Player>> getPlayers() {
        return players;
    }

    synchronized public int getH() {
        return h;
    }

    synchronized public void setHeight(int height) {
        this.h = height;
    }

    synchronized public int getW() {
        return w;
    }

    synchronized public void setWidth(int width) {
        this.w = width;
    }
}
