package GUI;

import javax.swing.*;

public class GUIFrame extends JFrame {
    // Set up window.
    public GUIFrame() {
        KruskalGraphics graphics = new KruskalGraphics();
        this.setSize(1000, 625);
        this.setTitle("Spanning Tree Visualizer");
        this.setResizable(false);
        this.add(graphics);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }
}

