package final_project;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class File_Chooser implements ActionListener {

    Field myfield;

    File_Chooser(Field field) {
        myfield = field;
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        JFileChooser jfc=new JFileChooser();
        jfc.setFileSelectionMode(JFileChooser.FILES_ONLY );
        jfc.setCurrentDirectory(new File("./"));
        jfc.showDialog(new JLabel(), "选择");
        myfield.readfile = jfc.getSelectedFile();
        System.out.println("open" + jfc.getSelectedFile().getName());

        myfield.initWorld();
        myfield.setStart(false);
        myfield.setIs_record(true);
        ExecutorService exec = Executors.newCachedThreadPool();
        try {
            exec.execute(new Move_record(myfield));
        }catch (Exception ex) {
            ex.printStackTrace();
        }
        finally {
            exec.shutdown();
        }


    }
}
