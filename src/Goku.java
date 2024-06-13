import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Goku extends Character {

    private static Goku instance;
    private String[] moveStrings = {"jab", "cut", "sweep"};

    private Goku() {
        super("Goku", 100, 7, 25);
        addMove(MovesDatabase.KA_ME_HA_ME_HA);
        addMove(MovesDatabase.QUICK_JAB);
        addMove(MovesDatabase.UPPER_CUT);
        addMove(MovesDatabase.LEG_SWEEP);
    }

    public static Goku getInstance() {
        if (instance == null) {
            instance = new Goku();
        }
        return instance;
    }

    @Override
    public void run() {
        Random random = new Random();
        while (!Battle.BATTLE_FINISHED) {
            try {
                String moveString = moveStrings[random.nextInt(moveStrings.length)];
                Move selectedMove =
                        getMoveList().stream().filter(move -> move.validate(moveString) && move.moveExecuted()).findFirst().get();

                if (selectedMove != null)
                    synchronized (moveQueue){
                        moveQueue.put(selectedMove);
                    }

                TimeUnit.SECONDS.sleep(2);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void resetHitpoints() {
        setHitpoints(100);
    }
}
