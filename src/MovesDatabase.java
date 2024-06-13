import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.TimeUnit;

public class MovesDatabase {
    public static Move KA_ME_HA_ME_HA;
    public static Move QUICK_JAB;
    public static Move UPPER_CUT;
    public static Move LEG_SWEEP;
    public static Move KAIO_KEN;

    private MovesDatabase() {
        Queue<String> sequence1 = new LinkedList<>();
        sequence1.add("ka");
        sequence1.add("me");
        sequence1.add("ha");
        sequence1.add("me");
        sequence1.add("ha");
        KA_ME_HA_ME_HA = new Move("Kamehameha!", sequence1, 200) {
            @Override
            public void execute(Character me, Character enemy) {
                int attack = (int) Math.floor(me.getAttack() * (getDamage() / 100.0));
                int block = (int) Math.ceil(attack * (enemy.getArmor() / 100.0));
                enemy.damagePlayer(attack - block);
            }

            @Override
            public String getMoveName() {
                return getName();
            }
        };

        QUICK_JAB = new Move("Quick Jab", "jab") {
            @Override
            public void execute(Character me, Character enemy) {
                int attack = me.getAttack();
                int block = (int) Math.ceil(attack * (enemy.getArmor() / 100.0));
                enemy.damagePlayer(attack - block);
            }

            @Override
            public String getMoveName() {
                return getName();
            }
        };

        UPPER_CUT = new Move("Upper Cut", "cut") {
            @Override
            public void execute(Character me, Character enemy) {
                int attack = me.getAttack();
                int block = (int) Math.ceil(attack * (enemy.getArmor() / 100.0));
                enemy.damagePlayer(attack - block);
            }

            @Override
            public String getMoveName() {
                return getName();
            }
        };

        LEG_SWEEP = new Move("Leg Sweep", "sweep") {
            @Override
            public void execute(Character me, Character enemy) {
                int attack = me.getAttack();
                int block = (int) Math.ceil(attack * (enemy.getArmor() / 100.0));
                enemy.damagePlayer(attack - block);
            }

            @Override
            public String getMoveName() {
                return getName();
            }
        };

        Queue<String> sequence2 = new LinkedList<>();
        sequence2.add("kaio");
        sequence2.add("ken");
        KAIO_KEN = new Move("Kaio-Ken", sequence2) {
            @Override
            public void execute(Character me, Character enemy) {
                Thread thread = new Thread(() -> {
                    try {
                        int oldValue = me.getAttack();
                        me.setAttack(oldValue * 2);
                        TimeUnit.SECONDS.sleep(8);
                        me.setAttack(oldValue);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                });
                thread.start();
            }

            @Override
            public String getMoveName() {
                return getName();
            }
        };
    }

    public static void initialize() {
        new MovesDatabase();
    }
}
