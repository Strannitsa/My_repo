package game_bam;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.IOException;

public class Game extends JFrame {

    private static Game game;
    private static long last_frame_time;
    private static Image backfon;
    private static Image try_again;
    private static Image ball;
    private static float ball_left = 100;
    private static float ball_top= -100;
    private static float ball_v = 100;
    private static int score;

    public static void main(String[] args) throws IOException{
        backfon = ImageIO.read(Game.class.getResourceAsStream("backfon.png"));
        try_again = ImageIO.read(Game.class.getResourceAsStream("try_again.png"));
        ball = ImageIO.read(Game.class.getResourceAsStream("ball.png"));
        game = new Game();
        game.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        game.setLocation(300, 100);
        game.setSize(800, 600);
        game.setResizable(false);
        last_frame_time = System.nanoTime();
        GameFild game_fild = new GameFild();
        game_fild.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int x = e.getX();
                int y = e.getY();
                float ball_right = ball_left + ball.getWidth(null);
                float ball_botton = ball_top + ball.getHeight(null);
                boolean is_ball = x >= ball_left && x <= ball_right && y >= ball_top && y <= ball_botton;
                if (is_ball);{
                    ball_top = -100;
                    ball_left =  (float) Math.random() * (game_fild.getWidth() - ball.getWidth(null));
                    ball_v = ball_v + 1;
                    score++;
                    game.setTitle("Score:" + score);
                }
            }
        });
        game.add(game_fild);
        game.setVisible(true);
    }

    private static void onRepaint(Graphics g) {
        long current_time = System.nanoTime();
        float delta_time = (current_time - last_frame_time) * 0.000000001f;
        last_frame_time = current_time;

        ball_top = ball_top + ball_v * delta_time;
        g.drawImage(backfon,0,0,null);
        g.drawImage(ball, (int) ball_left, (int) ball_top,null);
        if (ball_top > game.getHeight()) g.drawImage(try_again, 200,80,null);
    }

    private static class GameFild extends JPanel {

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            onRepaint(g);
            repaint();
        }

    }
}
