package final_project;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

public final class Main extends JFrame{

    private final int OFFSET = 80;

    Main() throws IOException{
        InitUI();
    }

    public void InitUI() throws IOException {

        Field field = new Field(12, 8);

        JMenu jm=new JMenu("Read Record") ;
        JMenuItem t1=new JMenuItem("open") ;
        t1.addActionListener(new File_Chooser(field));
        jm.add(t1) ;
        JMenuBar br=new JMenuBar();
        br.add(jm) ;
        this.setJMenuBar(br);


        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100 + OFFSET,
                1000 + 2 * OFFSET);
        setLocationRelativeTo(null);
        setTitle("Ground");


        add(field);


    }



    public static void main(String[] args) throws IOException{
        Main battle = new Main();
        battle.setVisible(true);
    }
}
