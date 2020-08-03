package vison1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.Random;

/**
 * =================TankWar 游戏画面绘制==============
 *
 * @author Steven
 * @create 2020-06-03 7:47
 */
public class TankWarGame extends Frame {
    public static final int GAME_WIDTH = 800, GAME_HEIGHT = 600, TANK_WIDTH = 75, TANK_HEIGHT = 75;      //  代码重构：将以后可能改动的量定为常量，常量名一般大写
    static final int FRAME_WIDTH = GAME_WIDTH - 70, FRAME_HEIGHT = GAME_HEIGHT - 70;            //   定义游戏内界，限制坦克的运动
    public int score = 0;
    private TankWarGame twg = this;
    //    private Tank player=new Tank(50,50);
    static ArrayList<Tank> tanks = new ArrayList<Tank>();
    static ArrayList<Bullet> bullets = new ArrayList<>();
    Tank myTank = new Tank(50, 50, true);
    Image offScreen = null;
    Image desk = new ImageIcon(ClassLoader.getSystemResource("image/caoppin.png")).getImage();

    public void launchFrame() {
        this.setBackground(Color.white);
        this.setBounds(300, 150, GAME_WIDTH, GAME_HEIGHT);
        this.setTitle("TANK WAR");
        this.setLayout(null);
        this.setResizable(false);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {          // 匿名类，创建窗口关闭的监听器
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
        this.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                myTank.keyDirection(e);
            }

            @Override
            public void keyReleased(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_SPACE) {
                    bullets.add(myTank.fire());
                }
            }
        });
        new Thread(new PaintThread()).start();
    }

    @Override
    public void update(Graphics g) {             //  运用 "双缓冲" 解决闪烁问题
        if (offScreen == null) {
            offScreen = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScenn = offScreen.getGraphics();     //  缓冲画笔
        paint(gOffScenn);
        g.drawImage(offScreen, 0, 0, null);
    }

    @Override
    public void paint(Graphics g) {             //  绘制图像
        Color begin = g.getColor();
        g.drawImage(desk, 0, 0, GAME_WIDTH, GAME_HEIGHT, this);
        myTank.draw(g);
        for (int i = 0; i < tanks.size(); i++) {       //  绘制坦克容器中的坦克
            Tank temp = (Tank) tanks.get(i);
            if (temp.isLive()) {
                temp.draw(g);
            } else {
                tanks.remove(i);
                int x = temp.getX();           // 用于处理爆炸事件
                int y = temp.getY();

                Tank enmy = new Tank(new Random().nextInt(TankWarGame.FRAME_WIDTH - 70), new Random().nextInt(TankWarGame.FRAME_HEIGHT - 70), false);
                addTanks(enmy);
                this.score++;
            }
        }
        for (int i1 = 0; i1 < bullets.size(); i1++) {       //  绘制子弹容器中的子弹
            Bullet temp = (Bullet) bullets.get(i1);
            if (temp.isaLive()) {
                temp.draw(g);
            } else {
                bullets.remove(temp);           // 当子弹命中坦克后设置 isaLive 为false 并将对象从容器中剔除
            }
        }
        Font font = new Font("等线", Font.BOLD, 17);
        g.setFont(font);
        g.setColor(Color.RED);
        g.setColor(Color.white);
        g.drawString("子弹数：" + bullets.size(), 10, 50);
        g.drawString(" 分  数：" + this.score, 10, 70);
//        ========  添加游戏打印信息==========（待添加）
        g.setColor(begin);
    }

    private class PaintThread implements Runnable {  // 使用多线程不断对图像进行绘制
        @Override                               //  内部类
        public void run() {
            while (true) {
                try {
                    repaint();
                    Thread.sleep(30);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }
    }


    public void addTanks(Tank temp) {
        tanks.add(temp);
    }
}






