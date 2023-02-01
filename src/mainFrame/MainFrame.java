package mainFrame;

import core.Log;
import gameobject.Player;
import game.state.State;
import input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class MainFrame extends JFrame {

    private Log log;
    private Canvas canvas;
    private Renderer renderer;

    private int x = 20;
    private int y = 20;

    public MainFrame(int width, int height, Input input, Log log) {
        this.log = log;
        ImageIcon icon = new ImageIcon("resources/official_wow_icon.png");
        setIconImage(icon.getImage());
        setTitle("WoW 2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setUndecorated(true);
        setResizable(false);

        this.renderer = new Renderer(log);

        canvas = new Canvas();
        canvas.setPreferredSize(new Dimension(width, height));
        canvas.setFocusable(false);
        canvas.addMouseListener(input);
        canvas.addMouseMotionListener(input);

        add(canvas);
        addKeyListener(input);

        pack();
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        canvas.createBufferStrategy(3);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void update(State currentState){
        renderer.update(currentState);
    }

    public void render(State state){
        BufferStrategy bufferStrategy = canvas.getBufferStrategy();
        Graphics graphics = bufferStrategy.getDrawGraphics();
        /////////////////////////////////////////////////////

        // Background
        graphics.setColor(Color.BLACK);
        graphics.fillRect(0,0, canvas.getWidth(), canvas.getHeight());

        Graphics2D graphics2D = (Graphics2D)graphics;

        // Map, GameObjects, NamePlates, UI
        renderer.render(state, graphics);

        ////////////////////////////////////////////////////
        graphics.dispose();
        bufferStrategy.show();
    }

    public void changeToLogInDisplay(){

    }

    public void changeToGameDisplay(){

    }
}
