package final_project;

import java.util.Vector;
import javax.swing.*;
import java.awt.*;
import java.net.URL;

public class Huluwa {
    Vector<Huluwa_Entity> huluwas;

    Huluwa() {
        huluwas = new Vector<>(7);
        for(int i = 1; i <= 7; i++){
            Huluwa_Entity hlw = new Huluwa_Entity();
            URL url = this.getClass().getClassLoader().getResource("h"+i+".png");
            ImageIcon igi = new ImageIcon(url);
            hlw.setImage(igi.getImage());
            url = this.getClass().getClassLoader().getResource("h"+i+"d.png");
            igi = new ImageIcon(url);
            hlw.setDeadimage(igi.getImage());
            huluwas.add(hlw);
        }
    }
}
