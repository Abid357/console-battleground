import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class Gameplay {

    private static JLabel lblName, lblAvatarP1, lblAvatarP2, lblAttackP1, lblAttackP2, lblArmorP1, lblArmorP2,
            lblHpP1, lblHpP2, lblClock;
    private static JTextField txtName, txtAction;
    private static JButton btnName, btnMovelistP1, btnMovelistP2, btnBattle, btnAction;
    private static MainPlayer player;
    private static JComboBox<String> cbPlayer1, cbPlayer2;
    private static JTextArea txtLog;

    private static boolean isToggle = true;

    public static void toggle() {
        isToggle = !isToggle;
        txtName.setEnabled(isToggle);
        btnName.setEnabled(isToggle);
        cbPlayer1.setEnabled(isToggle);
        cbPlayer2.setEnabled(isToggle);
        btnBattle.setEnabled(isToggle);
    }

    public static void main(String[] args) {
        MovesDatabase.initialize();
        CharactersDatabase.initialize();

        // CHARACTERS
        player = new MainPlayer();

        JFrame frame = new JFrame();
        frame.setTitle("Text Battleground");
        Container contentPane = frame.getContentPane();
        contentPane.setLayout(new FlowLayout());
        frame.setSize(new Dimension(1000, 1000));
        frame.setLocationRelativeTo(null);

        txtLog = new JTextArea(10, 60);
        txtLog.setEditable(false);
        txtLog.setFont(new Font("Calibri", Font.PLAIN, 18));
        txtLog.setLineWrap(true);
        txtLog.setWrapStyleWord(true);
        JScrollPane scrollPane = new JScrollPane(txtLog);

        txtName = new JTextField();
        txtName.setPreferredSize(new Dimension(300, 30));
        txtName.setHorizontalAlignment(SwingConstants.CENTER);
        txtName.setFont(new Font("Calibri", Font.PLAIN, 25));
        txtName.setToolTipText("Enter your name");
        contentPane.add(txtName);

        lblClock = new JLabel(new DecimalFormat("#00").format(Battle.BATTLE_DURATION));
        lblClock.setPreferredSize(new Dimension(frame.getPreferredSize().width, 90));
        lblClock.setHorizontalAlignment(SwingConstants.CENTER);
        lblClock.setFont(new Font("Calibri", Font.BOLD, 60));

        Icon icon = new ImageIcon("update_icon.png");
        Image img = ((ImageIcon) icon).getImage();
        Image newimg = img.getScaledInstance(30, 30, java.awt.Image.SCALE_SMOOTH);
        icon = new ImageIcon(newimg);

        btnName = new JButton(icon);
        btnName.setPreferredSize(new Dimension(30, 30));
        btnName.setFont(new Font("Calibri", Font.PLAIN, 25));
        btnName.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (!txtName.getText().isEmpty()) {
                    lblName.setText("Hello " + txtName.getText() + "!");
                    player.getCharacter().setName(txtName.getText());
                }
            }
        });
        contentPane.add(btnName);

        JLabel lblEmpty1 = new JLabel();
        lblEmpty1.setPreferredSize(new Dimension(670, 0));
        contentPane.add(lblEmpty1);

        lblName = new JLabel("Hello ?");
        lblName.setPreferredSize(new Dimension(frame.getPreferredSize().width, 30));
        lblName.setHorizontalAlignment(SwingConstants.CENTER);
        lblName.setFont(new Font("Calibri", Font.PLAIN, 35));
        contentPane.add(lblName);

        cbPlayer1 = new JComboBox<>();
        cbPlayer1.setPreferredSize(new Dimension(300, 30));
        cbPlayer1.setToolTipText("Select Player 1");
        cbPlayer1.addItem("You");
        for (Character character : CharactersDatabase.getCharacterList())
            cbPlayer1.addItem(character.getName());
        cbPlayer1.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) cbPlayer1.getSelectedItem();

                Character character;
                if (selectedItem.equals("You"))
                    character = player.getCharacter();
                else
                    character = CharactersDatabase.getCharacter(selectedItem);

                character.setAttackLabel(lblAttackP1);
                character.setArmorLabel(lblArmorP1);
                character.setHitpointsLabel(lblHpP1);

                Icon avatar = new ImageIcon("avatars/" + selectedItem.toLowerCase() + ".png");
                Image img1 = ((ImageIcon) avatar).getImage();
                Image newimg1 = img1.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                avatar = new ImageIcon(newimg1);
                lblAvatarP1.setIcon(avatar);

                lblAttackP1.setText("Attack: " + character.getAttack());
                lblArmorP1.setText("Armor: " + character.getArmor());
                lblHpP1.setText("HP: " + character.getHitpoints());
            }
        });

        contentPane.add(cbPlayer1);

        cbPlayer2 = new JComboBox<>();
        cbPlayer2.setPreferredSize(new Dimension(300, 30));
        cbPlayer2.setToolTipText("Select Player 2");
        cbPlayer2.addItem("You");
        for (Character character : CharactersDatabase.getCharacterList())
            cbPlayer2.addItem(character.getName());
        cbPlayer2.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                String selectedItem = (String) cbPlayer2.getSelectedItem();

                Character character;
                if (selectedItem.equals("You"))
                    character = player.getCharacter();
                else
                    character = CharactersDatabase.getCharacter(selectedItem);

                character.setAttackLabel(lblAttackP2);
                character.setArmorLabel(lblArmorP2);
                character.setHitpointsLabel(lblHpP2);

                Icon avatar = new ImageIcon("avatars/" + selectedItem.toLowerCase() + ".png");
                Image img1 = ((ImageIcon) avatar).getImage();
                Image newimg1 = img1.getScaledInstance(250, 250, Image.SCALE_SMOOTH);
                avatar = new ImageIcon(newimg1);
                lblAvatarP2.setIcon(avatar);

                lblAttackP2.setText("Attack: " + character.getAttack());
                lblArmorP2.setText("Armor: " + character.getArmor());
                lblHpP2.setText("HP: " + character.getHitpoints());
            }
        });

        JLabel lblEmpty2 = new JLabel();
        lblEmpty2.setPreferredSize(new Dimension(200, 0));
        contentPane.add(lblEmpty2);

        contentPane.add(cbPlayer2);

        JLabel lblEmpty3 = new JLabel();
        lblEmpty3.setPreferredSize(new Dimension(200, 0));
        contentPane.add(lblEmpty3);

        JPanel btnPanel = new JPanel();
        btnPanel.setLayout(new FlowLayout());
        btnPanel.setPreferredSize(new Dimension(frame.getPreferredSize().width, 40));
        contentPane.add(btnPanel);

        btnMovelistP1 = new JButton("Movelist");
        btnMovelistP1.setPreferredSize(new Dimension(100, 30));
        btnMovelistP1.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnMovelistP1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbPlayer1.getSelectedItem();
                Character character;
                if (selectedItem.equals("You"))
                    character = player.getCharacter();
                else
                    character = CharactersDatabase.getCharacter(selectedItem);

                java.util.List<String> moveStrings = new ArrayList<>();
                moveStrings.add("MOVE NAME - [SEQUENCE]");
                for (Move move : character.getMoveList()) {
                    String sequence = "";
                    for (String string : move.getSequence())
                        sequence += string + ", ";
                    sequence = sequence.substring(0, sequence.length() - 2);
                    moveStrings.add(move.getName() + " - [" + sequence + "]");
                }

                JList<String> lstMoves = new JList<>(moveStrings.toArray(new String[0]));
                lstMoves.setPreferredSize(new Dimension(300, 200));
                Dialog dialog = new JDialog();
                dialog.setTitle(character.getName() + "'s Movelist");
                dialog.add(lstMoves);
                dialog.setLocationRelativeTo(null);
                dialog.setModal(true);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        btnPanel.add(btnMovelistP1);

        JLabel lblEmpty4 = new JLabel();
        lblEmpty4.setPreferredSize(new Dimension(400, 0));
        btnPanel.add(lblEmpty4);

        btnMovelistP2 = new JButton("Movelist");
        btnMovelistP2.setPreferredSize(new Dimension(100, 30));
        btnMovelistP2.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnMovelistP2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbPlayer2.getSelectedItem();
                Character character;
                if (selectedItem.equals("You"))
                    character = player.getCharacter();
                else
                    character = CharactersDatabase.getCharacter(selectedItem);

                java.util.List<String> moveStrings = new ArrayList<>();
                moveStrings.add("MOVE NAME - [SEQUENCE]");
                for (Move move : character.getMoveList()) {
                    String sequence = "";
                    for (String string : move.getSequence())
                        sequence += string + ", ";
                    sequence = sequence.substring(0, sequence.length() - 2);
                    moveStrings.add(move.getName() + " - [" + sequence + "]");
                }

                JList<String> lstMoves = new JList<>(moveStrings.toArray(new String[0]));
                lstMoves.setPreferredSize(new Dimension(300, 200));
                Dialog dialog = new JDialog();
                dialog.setTitle(character.getName() + "'s Movelist");
                dialog.add(lstMoves);
                dialog.setLocationRelativeTo(null);
                dialog.setModal(true);
                dialog.pack();
                dialog.setVisible(true);
            }
        });
        btnPanel.add(btnMovelistP2);

        btnBattle = new JButton("Battle!");
        btnBattle.setPreferredSize(new Dimension(100, 30));
        btnBattle.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnBattle.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedItem = (String) cbPlayer1.getSelectedItem();
                Character player1;
                Character player2;

                if (selectedItem.equals("You"))
                    player1 = player.getCharacter();
                else
                    player1 = CharactersDatabase.getCharacter(selectedItem);

                selectedItem = (String) cbPlayer2.getSelectedItem();

                if (selectedItem.equals("You"))
                    player2 = player.getCharacter();
                else
                    player2 = CharactersDatabase.getCharacter(selectedItem);

                txtLog.selectAll();
                txtLog.replaceSelection("");

                if (player1.equals(player.getCharacter()) || player2.equals(player.getCharacter())) {
                    if (player.getCharacter().getName().isEmpty()) {
                        txtLog.append("You must have a name for your character!\n");
                        return;
                    }
                    txtAction.setEnabled(true);
                    btnAction.setEnabled(true);
                } else {
                    txtAction.setEnabled(false);
                    txtAction.setText("");
                    btnAction.setEnabled(false);
                }

                if (!player1.equals(player2)) {
                    toggle();

                    Battle.BATTLE_FINISHED = false;
                    Battle battle = new Battle(player1, player2, lblClock, txtLog, scrollPane.getVerticalScrollBar());
                    battle.start();
                } else
                    txtLog.append("Player 1 and Player 2 cannot be the same characters!\n");
            }
        });
        contentPane.add(btnBattle);

        JPanel pnlAction = new JPanel();
        pnlAction.setLayout(new FlowLayout());
        pnlAction.setPreferredSize(new Dimension(frame.getPreferredSize().width, 60));
        contentPane.add(pnlAction);

        txtAction = new JTextField();
        txtAction.setEnabled(false);
        txtAction.setPreferredSize(new Dimension(300, 50));
        txtAction.setHorizontalAlignment(SwingConstants.CENTER);
        txtAction.setFont(new Font("Calibri", Font.PLAIN, 35));
        txtAction.setToolTipText("Enter any move");
        pnlAction.add(txtAction);

        player.setTextField(txtAction);
        player.setTextArea(txtLog);

        JPanel pnlAction2 = new JPanel();
        pnlAction2.setLayout(new FlowLayout());
        pnlAction2.setPreferredSize(new Dimension(frame.getPreferredSize().width, 60));
        contentPane.add(pnlAction2);

        btnAction = new JButton("Execute");
        btnAction.setEnabled(false);
        btnAction.setPreferredSize(new Dimension(100, 30));
        btnAction.setFont(new Font("Calibri", Font.PLAIN, 15));
        btnAction.addActionListener(player);
        pnlAction2.add(btnAction);
        frame.getRootPane().setDefaultButton(btnAction);

        JPanel pnlAvatarDetails1 = new JPanel();
        pnlAvatarDetails1.setLayout(new GridLayout(3, 1));
        pnlAvatarDetails1.setPreferredSize(new Dimension(200, 250));

        lblAvatarP1 = new JLabel();
        lblAvatarP1.setPreferredSize(new Dimension(250, 250));
        lblAvatarP1.setBorder(new LineBorder(Color.BLACK, 2));
        contentPane.add(lblAvatarP1);

        lblAttackP1 = new JLabel("Attack: ");
        lblAttackP1.setPreferredSize(new Dimension(100, 20));
        lblAttackP1.setFont(new Font("Calibri", Font.BOLD, 20));
        lblAttackP1.setForeground(Color.RED);
        pnlAvatarDetails1.add(lblAttackP1);
        lblArmorP1 = new JLabel("Armor: ");
        lblArmorP1.setPreferredSize(new Dimension(100, 20));
        lblArmorP1.setFont(new Font("Calibri", Font.BOLD, 20));
        lblArmorP1.setForeground(Color.BLUE);
        pnlAvatarDetails1.add(lblArmorP1);
        lblHpP1 = new JLabel("HP: ");
        lblHpP1.setPreferredSize(new Dimension(100, 20));
        lblHpP1.setFont(new Font("Calibri", Font.BOLD, 20));
        lblHpP1.setForeground(Color.DARK_GRAY);
        pnlAvatarDetails1.add(lblHpP1);

        contentPane.add(pnlAvatarDetails1);

        JPanel pnlAvatarDetails2 = new JPanel();
        pnlAvatarDetails2.setLayout(new GridLayout(3, 1));
        pnlAvatarDetails2.setPreferredSize(new Dimension(200, 250));

        lblAvatarP2 = new JLabel();
        lblAvatarP2.setPreferredSize(new Dimension(250, 250));
        lblAvatarP2.setBorder(new LineBorder(Color.BLACK, 2));
        contentPane.add(lblAvatarP2);

        lblAttackP2 = new JLabel("Attack: ");
        lblAttackP2.setPreferredSize(new Dimension(100, 20));
        lblAttackP2.setFont(new Font("Calibri", Font.BOLD, 20));
        lblAttackP2.setForeground(Color.RED);
        pnlAvatarDetails2.add(lblAttackP2);
        lblArmorP2 = new JLabel("Armor: ");
        lblArmorP2.setPreferredSize(new Dimension(100, 20));
        lblArmorP2.setFont(new Font("Calibri", Font.BOLD, 20));
        lblArmorP2.setForeground(Color.BLUE);
        pnlAvatarDetails2.add(lblArmorP2);
        lblHpP2 = new JLabel("HP: ");
        lblHpP2.setPreferredSize(new Dimension(100, 20));
        lblHpP2.setFont(new Font("Calibri", Font.BOLD, 20));
        lblHpP2.setForeground(Color.DARK_GRAY);
        pnlAvatarDetails2.add(lblHpP2);

        contentPane.add(pnlAvatarDetails2);

        contentPane.add(lblClock);

        contentPane.add(scrollPane);

        cbPlayer1.setSelectedIndex(1);
        cbPlayer2.setSelectedIndex(2);

        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
