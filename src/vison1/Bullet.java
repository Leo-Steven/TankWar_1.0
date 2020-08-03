package vison1;

import java.awt.*;

/**
 * @author Steven
 * @create 2020-06-03 22:53
 */
public class Bullet {
    private int x = 1;
    private int y = 100;
    private boolean aLive = true;
    private boolean group=true;
    Rectangle bBound;
    enum Direction {U, D, L, R};
    Direction dir = Direction.D;
    private static final int BULLET_WIDTH = 10, BULLET_HEIGHT = 10, SPEED = 20;

    public void setaLive(boolean aLive) {
        this.aLive = aLive;
    }

    public boolean isaLive() {
        return aLive;
    }

    Bullet(Tank tank) {
        switch (tank.fireDir) {
            case U: {
                dir = Direction.U;
                x = tank.getX() + 32;
                y = tank.getY() + 20;
                break;
            }
            case D: {
                dir = Direction.D;
                x = tank.getX() + 35;
                y = tank.getY() + 50;
                break;
            }
            case R: {
                dir = Direction.R;
                x = tank.getX() + 50;
                y = tank.getY() + 30;
                break;
            }
            case L: {
                dir = Direction.L;
                x = tank.getX() + 20;
                y = tank.getY() + 32;
                break;
            }
            case STOP: {
                break;
            }
        }
        bBound=new Rectangle(tank.getX(),tank.getY(),BULLET_WIDTH,BULLET_HEIGHT);
        this.group=tank.isGroup();
        new Thread(new BulletThread(this)).start();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public boolean isGroup() {
        return group;
    }

    void draw(Graphics g) {
        Color begin = g.getColor();
        g.setColor(Color.magenta);
        g.fillOval(x, y, BULLET_WIDTH, BULLET_HEIGHT);
        g.setColor(begin);
    }

    void shot() {
        switch (dir) {
            case U: {
                y -= SPEED;
                break;
            }
            case D: {
                y += SPEED;
                break;
            }
            case R: {
                x += SPEED;
                break;
            }
            case L: {
                x -= SPEED;
                break;
            }
        }
        bBound.setBounds(x,y,BULLET_WIDTH,BULLET_HEIGHT);
    }
}

class BulletThread implements Runnable {
    Bullet b;

    BulletThread(Bullet b) {
        this.b = b;
    }

    @Override
    public void run() {

        while (b.getX() < TankWarGame.GAME_WIDTH && b.getX() > 0 && b.getY() < TankWarGame.GAME_HEIGHT && b.getY() > 0) {
            b.shot();
            if (!b.isaLive()) {
                return;
            }
            if (isFired()) {
                b.setaLive(false);
            }
            try {
                Thread.sleep(200);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        b.setaLive(false);
    }

    public boolean isFired() {
        if(!b.isGroup()){
            return false;
        }
        Tank temp;
        for (int i = 0; i < TankWarGame.tanks.size(); i++) {
            temp = TankWarGame.tanks.get(i);
            if (b.bBound.intersects(temp.tBound)) {
                temp.setBoold(temp.getBoold() - 25);
                return true;
            }
        }
        return false;
    }
}
