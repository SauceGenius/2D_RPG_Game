package display;

import core.Log;
import display.ui.UI;
import entity.Player;
import game.state.State;
import input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;
import java.util.ArrayList;

public class Display extends JFrame {

    private Log log;
    private Canvas canvas;
    private Renderer renderer;

    private int x = 20;
    private int y = 20;

    public Display(int width, int height, Input input, Player player, Log log, ArrayList<UI> uis) {
        this.log = log;
        ImageIcon icon = new ImageIcon("resources/official_wow_icon.png");
        setIconImage(icon.getImage());
        setTitle("WoW 2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.renderer = new Renderer(player, log, uis);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);

        // Default Cursor
        Toolkit toolkit = Toolkit.getDefaultToolkit();
        Image image = toolkit.getImage("resources/sprites/ui/MouseCursor.png");
        Cursor cursor = toolkit.createCustomCursor(image,new Point(0,0),"Game Cursor");
        setCursor(cursor);

        add(canvas);
        addKeyListener(input);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void render(State state){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        /////////////////////////////////////////////////////

        // Background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        // Map, GameObjects, NamePlates, UI
        renderer.render(state, graphics);

        ////////////////////////////////////////////////////
        graphics.dispose();
        bufferStrategy.show();
    }
}
