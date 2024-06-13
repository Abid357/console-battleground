import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainPlayer implements ActionListener {
    private Character character;
    private JTextField actionField;
    private JTextArea txtLog;

    public MainPlayer() {
        character = new Character("") {
            @Override
            public void resetHitpoints() {
                setHitpoints(100);
            }

            @Override
            public void run() {
            }
        };

        character.addMove(MovesDatabase.KA_ME_HA_ME_HA);
        character.addMove(MovesDatabase.UPPER_CUT);
        character.addMove(MovesDatabase.QUICK_JAB);
        character.addMove(MovesDatabase.LEG_SWEEP);
        character.addMove(MovesDatabase.KAIO_KEN);
    }

    public Character getCharacter() {
        return character;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (!Battle.BATTLE_FINISHED) {
            String moveString = actionField.getText();
            actionField.setText("");

            Move selectedMove =
                    character.getMoveList().stream().filter(move -> move.validate(moveString)).findFirst().orElse(null);

            if (selectedMove != null && selectedMove.moveExecuted())
                synchronized (character.moveQueue) {
                    try {
                        character.moveQueue.put(selectedMove);
                    } catch (InterruptedException interruptedException) {
                        interruptedException.printStackTrace();
                    }
                }
            else
                txtLog.append(character.getName() + " says " + moveString + "...\n");
        }
    }

    public void setTextArea(JTextArea textArea){
        txtLog = textArea;
    }

    public void setTextField(JTextField textField){
        actionField = textField;
    }
}
