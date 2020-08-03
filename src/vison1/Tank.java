package vison1;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.util.Random;

/**
 * ==========实现坦克类=========
 *
 * @author Steven
 * @create 2020-06-03 23:20
 */
public class Tank {
    private int x, y, boold = 100;
    private boolean group;

    Bullet a;

    enum Direction {U, D, L, R, STOP}

    ;
    Direction dir = Direction.U;
    Direction fireDir = Direction.U;
    private final int TANK_WIDTH = 75, TANK_HEIGHT = 75, SPEED = 10;
    Rectangle tBound;
    Image desk = new ImageIcon(ClassLoader.getSystemResource("image/caoppin.png")).getImage();
    Image tank = new ImageIcon(ClassLoader.getSystemResource("image/TankUp.png")).getImage();
    Image eTank = new ImageIcon(ClassLoader.getSystemResource("image/eTankUp.png")).getImage();
    Image tankRight = new ImageIcon(ClassLoader.getSystemResource("image/TankRight.png")).getImage();
    Image tankDown = new ImageIcon(ClassLoader.getSystemResource("image/TankDown.png")).getImage();
    Image tankLeft = new ImageIcon(ClassLoader.getSystemResource("image/TankLeft.png")).getImage();
    Image tankUP = new ImageIcon(ClassLoader.getSystemResource("image/TankUp.png")).getImage();
    Image eTankUP = new ImageIcon(ClassLoader.getSystemResource("image/eTankUp.png")).getImage();
    Image eTankDown = new ImageIcon(ClassLoader.getSystemResource("image/eTankDown.png")).getImage();
    Image eTankLeft = new ImageIcon(ClassLoader.getSystemResource("image/eTankLeft.png")).getImage();
    Image eTankRight = new ImageIcon(ClassLoader.getSystemResource("image/eTankRight.png")).getImage();

    public Tank(int x, int y, boolean group) {
        this.x = x;
        this.y = y;
        tBound=new Rectangle(x,y,TANK_WIDTH,TANK_HEIGHT);
        this.group = group;
    }

    public int getX() {
        return x;
    }

    public boolean isGroup() {
        return group;
    }

    public int getBoold() {
        return boold;
    }

    public void setBoold(int boold) {
        this.boold = boold;
    }

    public int getY() {
        return y;
    }

    public void draw(Graphics g) {
        if (this.group) {
            g.drawImage(tank, x, y, TANK_WIDTH, TANK_HEIGHT, null);
            this.move();
            dir = Direction.STOP;
        } else {
            g.drawImage(eTank, x, y, TankWarGame.TANK_WIDTH, TankWarGame.TANK_HEIGHT, null);
            if(new Random().nextInt(48)>38) {
                Random r = new Random();
                switch (r.nextInt() % 5) {
                    case 1: {
                        this.dir = Direction.D;
                        break;
                    }
                    case 2: {
                        this.dir = Direction.L;
                        break;
                    }
                    case 3: {
                        this.dir = Direction.R;
                        break;
                    }
                    case 4: {
                        this.dir = Direction.U;
                        break;
                    }
                    case 5: {
                        this.dir = Direction.STOP;
                        break;
                    }
                }
            }
            if(new Random().nextInt(10)>7)
                TankWarGame.bullets.add(this.fire());
            if(new Random().nextInt(10)>7)
               this.eMove();

        }

    }

//    public void eDraw(Graphics g){                    //===========2020.06.05 移除，添加boolean类型属性进行区别
//        g.drawImage(eTank,x,y,TankWarGame.TANK_WIDTH,TankWarGame.TANK_HEIGHT,null);
//    }

    public void move() {
        switch (dir) {
            case D: {
                if (y < TankWarGame.FRAME_HEIGHT) {
                    tank = tankDown;
                    y += SPEED;
                } else {
                    y = TankWarGame.FRAME_HEIGHT + 5;
                }
                break;
            }
            case L: {
                if (x >= 5) {
                    tank = tankLeft;
                    x -= SPEED;
                } else {
                    x = 0;
                }
                break;
            }
            case R: {
                if (x < TankWarGame.FRAME_WIDTH) {
                    tank = tankRight;
                    x += SPEED;
                } else {
                    x = TankWarGame.FRAME_WIDTH + 5;
                }
                break;
            }
            case U: {
                if (y >= 5) {
                    tank = tankUP;
                    y -= SPEED;
                } else {
                    y = 0;
                }
                break;
            }
            case STOP: {
                break;
            }
        }
        tBound.setBounds(x,y,TANK_WIDTH,TANK_HEIGHT);
    }

    public void eMove() {
        switch (dir) {
            case D: {
                if (y < TankWarGame.FRAME_HEIGHT) {
                    eTank = eTankDown;
                    y += SPEED;
                } else {
                    y = TankWarGame.FRAME_HEIGHT + 5;
                }
                break;
            }
            case L: {
                if (x >= 5) {
                    eTank = eTankLeft;
                    x -= SPEED;
                } else {
                    x = 0;
                }
                break;
            }
            case R: {
                if (x < TankWarGame.FRAME_WIDTH) {
                    eTank = eTankRight;
                    x += SPEED;
                } else {
                    x = TankWarGame.FRAME_WIDTH + 5;
                }
                break;
            }
            case U: {
                if (y >= 5) {
                    eTank = eTankUP;
                    y -= SPEED;
                } else {
                    y = 0;
                }
                break;
            }
            case STOP: {
                break;
            }
        }
        tBound.setBounds(x,y,TANK_WIDTH,TANK_HEIGHT);
    }

    void keyDirection(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_DOWN: {
                this.dir = Direction.D;
                fireDir = Direction.D;
                break;
            }
            case KeyEvent.VK_LEFT: {
                this.dir = Direction.L;
                fireDir = Direction.L;
                break;
            }
            case KeyEvent.VK_RIGHT: {
                dir = Direction.R;
                fireDir = Direction.R;
                break;
            }
            case KeyEvent.VK_UP: {
                this.dir = Direction.U;
                fireDir = Direction.U;
                break;
            }
            default: {
                this.dir = Direction.STOP;
                break;
            }
        }
    }

    public Bullet fire() {
        Bullet m = new Bullet(this);
        return m;
    }

    public boolean isLive() {
        if (this.getBoold() <= 0)
            return false;
        return true;
    }
}
