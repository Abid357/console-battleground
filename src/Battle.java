import javax.swing.*;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.TimeUnit;

public class Battle implements Subject, Runnable {

    public static boolean BATTLE_FINISHED = false;
    public static int BATTLE_DURATION = 40;

    private Character player1;
    private Character player2;
    private BlockingQueue<MoveExecutable> moveQueueP1;
    private BlockingQueue<MoveExecutable> moveQueueP2;

    private Thread timerThread;
    private Thread queueThread1;
    private Thread queueThread2;
    private List<Observer> observerList;

    private JTextArea txtLog;
    private JLabel lblClock;
    private JScrollBar scrollBar;

    public Battle(Character player1, Character player2, JLabel lblClock, JTextArea txtLog, JScrollBar scrollBar) {
        this.player1 = player1;
        this.player2 = player2;
        this.txtLog = txtLog;
        this.scrollBar = scrollBar;
        this.lblClock = lblClock;
        moveQueueP1 = new LinkedBlockingDeque<>();
        moveQueueP2 = new LinkedBlockingDeque<>();
        observerList = new ArrayList<>();

        registerObserver(player1);
        registerObserver(player2);

        timerThread = new Thread(this);
        queueThread1 = new Thread(new MoveExecutor(moveQueueP1, player1, player2, txtLog));
        queueThread2 = new Thread(new MoveExecutor(moveQueueP2, player2, player1, txtLog));
    }

    public void start() {
        timerThread.start();
        player1.startFight(moveQueueP1);
        player2.startFight(moveQueueP2);
        queueThread1.start();
        queueThread2.start();
    }

    public void announceWinner() {
        txtLog.append("GAME OVER\n");
        if (player1.getHitpoints() == player2.getHitpoints()) {
            txtLog.append("Draw!\n");
        } else if (player1.getHitpoints() > player2.getHitpoints()) {
            txtLog.append("Player 1 (" + player1.getName() + ") wins!\n");
        } else {
            txtLog.append("Player 2 (" + player2.getName() + ") wins!\n");
        }
    }

    // Timer thread for battle
    @Override
    public void run() {
        int tick = 0;
        while (tick != BATTLE_DURATION && player1.getHitpoints() != 0 && player2.getHitpoints() != 0) {
            try {
                TimeUnit.SECONDS.sleep(1);
                tick++;
                notifyObservers(tick);
                SwingUtilities.invokeLater(() -> {
                    scrollBar.setValue(scrollBar.getMaximum());
                });
                lblClock.setText(new DecimalFormat("#00").format(BATTLE_DURATION - tick));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        BATTLE_FINISHED = true;

        removeObserver(player1);
        removeObserver(player2);

        announceWinner();
        player1.resetHitpoints();
        player2.resetHitpoints();

        SwingUtilities.invokeLater(() -> {
            scrollBar.setValue(scrollBar.getMaximum());
        });

        Gameplay.toggle();
    }

    @Override
    public void registerObserver(Observer observer) {
        observerList.add(observer);
    }

    @Override
    public void removeObserver(Observer observer) {
        observerList.remove(observer);
    }

    @Override
    public void notifyObservers(int tick) {
        observerList.forEach(observer -> observer.update(tick));
    }
}
