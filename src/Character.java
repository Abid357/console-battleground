import javax.swing.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Queue;
import java.util.concurrent.BlockingQueue;

public abstract class Character implements Runnable, Observer{
    private String name;
    private int hitpoints;
    private int attack;
    private int armor;
    private List<Move> moveList;
    protected BlockingQueue<MoveExecutable> moveQueue;
    protected int tick;

    private JLabel lblAttack;
    private JLabel lblArmor;
    private JLabel lblHp;

    public Character(String name, int hitpoints, int attack, int armor) {
        this.name = name;
        this.hitpoints = hitpoints;
        this.attack = attack;
        this.armor = armor;
        moveList = new ArrayList<>();
        moveQueue = null;
        tick = 0;
    }

    public void setAttackLabel(JLabel lblAttack) {
        this.lblAttack = lblAttack;
    }

    public void setArmorLabel(JLabel lblArmor) {
        this.lblArmor = lblArmor;
    }

    public void setHitpointsLabel(JLabel lblHp) {
        this.lblHp = lblHp;
    }

    @Override
    public void update(int tick){
        this.tick = tick;
        SwingUtilities.invokeLater(()->{
            lblAttack.setText("Attack: " + attack);
            lblArmor.setText("Armor: " + armor);
            lblHp.setText("HP: " + hitpoints);
        });
    }

    public void startFight(BlockingQueue<MoveExecutable> moveQueue) {
        this.moveQueue = moveQueue;
        Thread thread = new Thread(this);
        thread.start();
    }

    public Character(String name) {
        this(name, 100, 5, 10);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getHitpoints() {
        return hitpoints;
    }

    public void setHitpoints(int hitpoints) {
        this.hitpoints = hitpoints;
    }

    public int getAttack() {
        return attack;
    }

    public void setAttack(int attack) {
        this.attack = attack;
    }

    public int getArmor() {
        return armor;
    }

    public void setArmor(int armor) {
        this.armor = armor;
    }

    public void addMove(Move move){
        boolean found = false;
        for (Move m : moveList){
            if (m.getName().equals(move.getName())){
                found = true;
                break;
            }
        }
        if (!found)
            moveList.add(move);
    }

    public List<Move> getMoveList() {
        return moveList;
    }

    public void damagePlayer(int damage){
        if (damage <= 0)
            damage = 1;
        hitpoints -= damage;
        if (hitpoints < 0)
            hitpoints = 0;
    }

    public abstract void resetHitpoints();

    @Override
    public String toString() {
        return "Character{" +
                "name='" + name + '\'' +
                ", hitpoints=" + hitpoints +
                ", attack=" + attack +
                ", armor=" + armor +
                '}';
    }
}
