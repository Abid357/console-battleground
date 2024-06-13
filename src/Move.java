import java.util.LinkedList;
import java.util.Queue;

public abstract class Move implements MoveExecutable {
    private String name;
    private Queue<String> sequence;
    private Queue<String> validation;
    private boolean moveExecuted;
    private int damage;

    public Move(String name, Queue<String> sequence, int damage) {
        this.name = name;
        this.sequence = sequence;
        if (sequence.isEmpty())
            sequence.add(name);
        this.moveExecuted = false;
        this.damage = damage;
    }

    public Move(String name, Queue<String> sequence) {
        this(name, sequence, 0);
    }

    public Move(String name, String oneWordSequence, int damage){
        this(oneWordSequence, new LinkedList<>(), damage);
        this.name = name;
    }

    public Move(String name, String oneWordSequence){
        this(oneWordSequence, new LinkedList<>(), 0);
        this.name = name;
    }

    public boolean validate(String string) {
        if (validation == null) {
            validation = new LinkedList<>();
            validation.addAll(sequence);
        }
        if (validation.peek().equalsIgnoreCase(string)) {
            validation.poll();
            if (validation.isEmpty()) {
                moveExecuted = true;
                validation = null;
            }
            return true;
        } else
            return false;
    }

    public boolean moveExecuted() {
        if (moveExecuted) {
            moveExecuted = false;
            return true;
        } else
            return false;
    }

    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Queue<String> getSequence() {
        return sequence;
    }

    public void setSequence(Queue<String> sequence) {
        this.sequence = sequence;
    }
}
