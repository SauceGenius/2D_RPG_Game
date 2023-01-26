package display;

import core.Log;
import login.AccountController;
import ui.UIController;
import gameobject.Player;
import game.state.State;
import input.Input;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

public class MainFrame extends JFrame {

    private AccountController accountController;
    private Log log;
    private Canvas canvas;
    private Renderer renderer;

    private int x = 20;
    private int y = 20;

    public MainFrame(int width, int height, AccountController accountController, Input input, Player player, Log log, UIController uiController) {
        this.log = log;
        ImageIcon icon = new ImageIcon("resources/official_wow_icon.png");
        setIconImage(icon.getImage());
        setTitle("WoW 2D");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setResizable(false);

        this.renderer = new Renderer(accountController, player, log, uiController);

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

    public void changeToLogInDisplay(){

    }

    public void changeToGameDisplay(){

    }
}
