import java.util.Random;
import java.util.concurrent.TimeUnit;

public class Vegeta extends Character {

    private static Vegeta instance;
    private String[] moveStrings = {"jab", "cut", "sweep"};

    private Vegeta() {
        super("Vegeta", 100, 5, 40);
        addMove(MovesDatabase.QUICK_JAB);
        addMove(MovesDatabase.UPPER_CUT);
        addMove(MovesDatabase.LEG_SWEEP);
    }

    public static Vegeta getInstance() {
        if (instance == null) {
            instance = new Vegeta();
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
                    synchronized (moveQueue) {
                        moveQueue.put(selectedMove);
                    }

                TimeUnit.SECONDS.sleep(3);
            } catch (Exception e) {

            }
        }
    }

    @Override
    public void resetHitpoints() {
        setHitpoints(100);
    }
}
