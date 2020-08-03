package vison1;

import java.util.Random;

/**
 * @author Steven
 * ===========坦克大战主函数==============
 * @create 2020-06-03 9:05
 * 2020.06.05   融合tank类中draw（）和eDraw ，添加boolean类型属性值区别敌我坦克
 */
public class Gaming {
    public static void main(String[] args) {
        TankWarGame twg = new TankWarGame();
        twg.launchFrame();
        addTK(3, twg);
    }

    public static void addTK(int count, TankWarGame tankWarGame) {
        Random random = new Random();
        while (tankWarGame.tanks.size() < count) {
            Tank enemy = new Tank(random.nextInt(TankWarGame.GAME_WIDTH - 70), random.nextInt(TankWarGame.GAME_HEIGHT - 70), false);
            tankWarGame.addTanks(enemy);
        }
    }
}
