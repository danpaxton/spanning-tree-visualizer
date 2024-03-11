package GUI;

import structures.Edge;
import structures.GraphMST;
import structures.IndexedObject;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.LinkedList;

// Line record used for animating lines.
final record Line(int x1, int y1, int x2, int y2, Color color){}
// Graphics component. Visualizes Kruskal's Algorithm.
public class KruskalGraphics extends JPanel implements ActionListener {
    // Undirected graph with vertices 'V'.
    private GraphMST<Point> nodes;
    // List of all edges 'E' considered by Kruskal's algorithm.
    private ArrayList<Edge<Point>> mst;
    // List of lines to be drawn.
    private final LinkedList<Line> lines;
    // Determines max or min spanning tree.
    private boolean rev;
    // Number of vertices in the graph.
    private int size;
    private int newSize;
    // Delay between actions performed.
    private int delay;
    // Index in the mst list.
    private int index;
    // X-coordinate and direction value for load animation.
    private int loady;
    private int loadv;
    // Boolean value for if load symbol should appear.
    private boolean loading;
    // Boolean value for anti-aliasing option.
    private boolean smoothGraphics;
    // Custom colors.
    private final Color redOrange = new Color(255, 69, 0, 255);
    private final Color clearGrey = new Color(169, 169, 169, 64);
    private final Color darkRed = new Color(30, 0, 0);
    // Animation timer.
    private final Timer timer = new Timer(delay, this);
    // Graphics object that supports drawing.
    private Graphics2D graph2D;
    // Initializes all necessary variables. O(V^2).
    public KruskalGraphics() {
        rev = true;
        lines = new LinkedList<>();
        size = 100;
        newSize = size;
        delay = 125;
        loady = 475;
        loading = false;
        buttons();
        reset();
    }
    // Displays graphics until window is closed.
    public void paintComponent(Graphics graph) {
        super.paintComponent(graph);
        this.setBackground(Color.BLACK);
        // Initialize 2D graphics component.
        graph2D = (Graphics2D) graph;
        // Anti-Aliasing option.
        if (smoothGraphics) {
            graph2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        } else {
            graph2D.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
        }
        // Draw visual components.
        drawLines();
        drawVertices();
        if (loading) {
            drawLoading();
        }
        // Change background color after algorithm finishes.
        if (index == mst.size()) {
            this.setBackground(darkRed);
        }
    }
    // Add all buttons to window layout, O(1).
    public void buttons() {
        // Set layout and initialize panels.
        this.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel();
        JPanel bottomPanel = new JPanel();
        // Generate Button.
        JButton generateB = new JButton("Generate");
        generateB.setBackground(Color.ORANGE);
        generateB.addActionListener(e -> {
            // Regenerate all vertices.
            stop();
            reset();
        });
        // Run Kruskal's algorithm button.
        JButton kruskalB = new JButton("Run Kruskal's Algorithm");
        kruskalB.setBackground(Color.orange);
        kruskalB.addActionListener(e -> {
            // Start run of the algorithm.
            stop();
            start();
        });
        // Minimum or Maximum Spanning tree button option.
        JLabel treeType = new JLabel("      Tree type:");
        JRadioButton minB = new JRadioButton("Minimum Spanning");
        minB.setBorderPainted(true);
        minB.setActionCommand("Minimum Spanning");
        JRadioButton maxB = new JRadioButton(("Maximum Spanning"));
        maxB.setBorderPainted(true);
        maxB.setActionCommand("Maximum Spanning");
        // Apply button to set tree type.
        JButton apply = new JButton("Apply");
        ButtonGroup group = new ButtonGroup();
        group.add(minB);
        group.add(maxB);
        apply.addActionListener(e -> {
            // Remake priority queue and mst with comparator reversed.
            stop();
            String command = group.getSelection().getActionCommand(); // Get button selection.
            rev = command.equals("Minimum Spanning");
            nodes.treeOrientation(rev);
            makeEdges();
            mst = nodes.KruskalMST();
        });
        // Add buttons to top panel.
        topPanel.add(generateB);
        topPanel.add(kruskalB);
        topPanel.add(treeType);
        topPanel.add(minB);
        topPanel.add(maxB);
        topPanel.add(apply);
        // Number of vertices slider.
        JLabel sLabel = new JLabel("Number of Vertices:");
        JSlider sizeS = new JSlider(10, 500, 100);
        Hashtable<Integer, JLabel> labelTable = new Hashtable<>();
        labelTable.put(10, new JLabel("10"));
        labelTable.put(100, new JLabel("100"));
        labelTable.put(200, new JLabel("200"));
        labelTable.put(300, new JLabel("300"));
        labelTable.put(400, new JLabel("400"));
        labelTable.put(500, new JLabel("500"));
        sizeS.setLabelTable(labelTable);
        sizeS.setPaintLabels(true);
        // Get new size for graph.
        sizeS.addChangeListener(l -> newSize = sizeS.getValue());
        // Delay slider.
        JLabel dLabel = new JLabel("        Delay:");
        JSlider speedS = new JSlider(0, 250);
        speedS.addChangeListener(l -> delay = speedS.getValue());
        // Anti-Aliasing option button.
        JRadioButton aa = new JRadioButton("Anti-Aliasing (Slower Runtime)");
        aa.addActionListener(e -> smoothGraphics = !smoothGraphics);
        // Add buttons to bottom panel.
        bottomPanel.add(sLabel);
        bottomPanel.add(sizeS);
        bottomPanel.add(dLabel);
        bottomPanel.add(speedS);
        bottomPanel.add(aa);
        // Add panels to window.
        this.add(topPanel, BorderLayout.PAGE_START);
        this.add(bottomPanel, BorderLayout.PAGE_END);
        // Preselect minB button option.
        minB.setSelected(true);
    }
    // Start animation timer with current delay. O(1).
    public void start() {
        loading = true;
        index = 0;
        timer.setDelay(delay);
        timer.start();
    }
    // Stop animation timer. O(1).
    public void stop() {
        resetLoading();
        timer.stop();
        clearLines();
    }
    // Draw circle from center coordinates. O(1).
    public void drawCenteredCircle(int x, int y, int r) {
        x = x - (r / 2);
        y = y - (r / 2);
        graph2D.fillOval(x, y, r, r);
    }
    // Draw vertices with width dependent on number of vertices. O(V).
    public void drawVertices() {
        // Set circle width.
        int r;
        if (size <= 50)
            r = 12;
        else if (size <= 100)
            r = 10;
        else if (size <= 200)
            r = 8;
        else if (size <= 300)
            r = 6;
        else if (size <= 400)
            r = 4;
        else
            r = 3;
        graph2D.setColor(Color.orange);
        for (IndexedObject<Point> n : nodes.getVertices()) {
            // Draw circle.
            drawCenteredCircle((int) n.obj().getX(), (int) n.obj().getY(), r);
        }
    }
    // Make nodes at random for the graph. O(V).
    public void makeVertices() {
        for (int i = 0; i < size; i++) {
            // True graphics area is bounded to avoid clipping panels.
            int x = (int) (Math.random() * 950) + 25;
            int y = (int) (Math.random() * 425) + 75;
            // Add point.
            Point p = new Point(x, y);
            nodes.addVertice(p);
        }
    }
    // Draw spanning tree lines with stroke dependent on number of vertices. O(E).
    public void drawLines() {
        int w;
        if (size <= 100)
            w = 3;
        else if (size <= 300)
            w = 2;
        else
            w = 1;
        // Set stroke width.
        BasicStroke s = new BasicStroke(w);
        for (Line l : lines) {
            // Non-cycle edge.
            if (l.color().equals(redOrange)) {
                graph2D.setStroke(s);
            }
            // Cycle-edge.
            else {
                graph2D.setStroke(new BasicStroke(1));
            }
            // Draw line.
            graph2D.setColor(l.color());
            graph2D.drawLine(l.x1(), l.y1(), l.x2(), l.y2());
        }
    }
    // Make all (V * (V - 1)) / 2) possible edges. O(V^2).
    public void makeEdges() {
        for (int i = 0; i < size - 1; i++) {
            for (int j = i + 1; j < size; j++) {
                // Make source and destination vertices.
                IndexedObject<Point> src = nodes.getVertices()[i];
                IndexedObject<Point> dest = nodes.getVertices()[j];
                // Calculate distance.
                double distance = Math.sqrt(Math.pow((dest.obj().getX() - src.obj().getX()), 2)
                        + Math.pow(dest.obj().getY() - src.obj().getY(), 2));
                // Add edge to graph with distance as weight.
                nodes.addEdge(src, dest, distance);
            }
        }
    }
    // Draw line given edge and color. O(1).
    public void drawEdge(Edge<Point> e, Color color) {
        int x1 = (int) e.getSrc().obj().getX();
        int y1 = (int) e.getSrc().obj().getY();
        int x2 = (int) e.getDest().obj().getX();
        int y2 = (int) e.getDest().obj().getY();
        addLine(x1, y1, x2, y2, color);
    }
    // Add line to list of all lines. O(1).
    public void addLine(int x1, int y1, int x2, int y2, Color color) {
        lines.add(new Line(x1, y1, x2, y2, color));
        repaint();
    }
    // Clear line list and wipe all painted lines. O(1).
    public void clearLines() {
        lines.clear();
        repaint();
    }
    // Draw loading animation. O(1).
    public void drawLoading() {
        graph2D.setColor(Color.white);
        // Draw circle.
        // x,y coordinates and direction value v used for load animation.
        int loadx = 990;
        drawCenteredCircle(loadx, loady, 6);
    }
    // Determines the direction for load animation. O(1).
    public void loadAnim() {
        if (loady == 510)
            loadv = -1;
        else if (loady == 475)
            loadv = 1;
        loady += loadv;
    }
    // Draws load symbol and current edge in mst list, stops animation otherwise. Executes once for each timer tick determined by delay. O(1), O(E) total.
    @Override
    public void actionPerformed(ActionEvent e) {
        if (index < mst.size()) {
            // Next edge in the mst.
            Edge<Point> currEdge = mst.get(index++);
            if (currEdge.isCycle()) {
                // Cycle edge.
                drawEdge(currEdge, clearGrey);
            } else {
                // Non-Cycle edge.
                drawEdge(currEdge, redOrange);
            }
            loadAnim();
        } else {
            // Stop load animation and reset timer.
            resetLoading();
            timer.stop();
        }

    }
    // Resets loading variables. O(1).
    public void resetLoading() {
        loading = false;
        loadv = 0;
        loady = 475;
    }
    // Resets variables for a new graph. O(V^2), makeEdges() dominates makeVertices() and KruskalMST().
    public void reset() {
        size = newSize;
        nodes = new GraphMST<>(size, rev);
        makeVertices();
        makeEdges();
        mst = nodes.KruskalMST();
    }
}


