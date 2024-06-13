import javax.swing.*;
import java.util.concurrent.BlockingQueue;

public class MoveExecutor implements Runnable{

    private BlockingQueue<MoveExecutable> myQueue;
    private Character me;
    private Character enemy;

    private JTextArea txtLog;

    public MoveExecutor(BlockingQueue<MoveExecutable> myQueue, Character me, Character enemy, JTextArea txtLog) {
        this.myQueue = myQueue;
        this.me = me;
        this.enemy = enemy;
        this.txtLog = txtLog;
    }

    @Override
    public void run() {
        while (!Battle.BATTLE_FINISHED){
            try {
                MoveExecutable move = myQueue.take();
                move.execute(me, enemy);
                txtLog.append(me.getName() + " used " + move.getMoveName() + "!\n");
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
