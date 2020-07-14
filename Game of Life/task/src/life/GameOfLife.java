package life;

import javax.swing.*;
import java.awt.*;

public class GameOfLife extends JFrame {
    int size;
    String generation = "Generation #";
    String alive = "Alive: ";
    JPanel leftPanel = new JPanel();
    JLabel generationLabel = new JLabel(generation);
    JLabel aliveLabel = new JLabel(alive);
    JPanel mainField;
    JToggleButton playToggleButton = new JToggleButton();
    JButton resetButton = new JButton();
    int cnt = 0;
    GameOfLifeCore coreGame;

    public GameOfLife() {
        super("Game of Life");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLayout(new BorderLayout());

        //left panel
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setSize(200, 500);
        //

        playToggleButton.setName("PlayToggleButton");
        playToggleButton.setText("Pause");
        playToggleButton.setBounds(100, 100, 100, 100);
        playToggleButton.addActionListener(e -> {
            if (playToggleButton.isSelected()) {
                playToggleButton.setText("Play");
                coreGame.isRunning = false;
            } else {
                playToggleButton.setText("Pause");
                coreGame.isRunning = true;
                GameThread runGameThread = new GameThread();
                runGameThread.start();
            }
                });
        leftPanel.add(playToggleButton);

        resetButton.setName("ResetButton");
        resetButton.setText("Reset");
        resetButton.setBounds(100, 100, 100, 80);
        resetButton.addActionListener(e -> {
            generationLabel.setText(generation + 1);
            coreGame.universe = coreGame.populateUniverse(size);
            coreGame.generation = 1;
            if (leftPanel != null) {
                remove(leftPanel);
            }
            add(leftPanel, BorderLayout.WEST);
            leftPanel.revalidate();
            leftPanel.repaint();
        });

        leftPanel.add(resetButton);

        generationLabel.setName("GenerationLabel");
        generationLabel.setText(generation + cnt);
        generationLabel.setBounds(40, 20, 100, 30);
        leftPanel.add(generationLabel);

        aliveLabel.setName("AliveLabel");
        aliveLabel.setText(alive + cnt);
        aliveLabel.setBounds(40, 50, 100, 30);
        leftPanel.add(aliveLabel);

        add(leftPanel, BorderLayout.WEST);
        leftPanel.setVisible(true);

        setVisible(true);
    }

    public JPanel setField(char[][] matrix) {
        int length = matrix.length;
        JPanel board = new JPanel();
        JPanel squares[][] = new JPanel[length][length];

        board.setLayout(new GridLayout(length, length));

        for (int i = 0; i < length; i++) {
            for (int j = 0; j < length; j++) {
                squares[i][j] = new JPanel();
                if (matrix[i][j] == 'O') {
                    squares[i][j].setBackground(Color.black);
                }
                squares[i][j].setBorder(BorderFactory.createLineBorder(Color.black, 1));
                board.add(squares[i][j]);
            }
        }
        return board;
    }

    public void updateField(JPanel jPanel) {
        if (mainField != null) {
            remove(mainField);
        }
        mainField = jPanel;
        add(mainField, BorderLayout.CENTER);
        mainField.revalidate();
        mainField.repaint();
    }

    public void setGenerationLabel(int generation) {
        generationLabel.setText(this.generation + generation);
        repaint();
    }

    public void setAliveLabel(int alive) {
        aliveLabel.setText(this.alive + alive);
        repaint();
    }

    class GameThread extends Thread {
        @Override
        public void run() {
            coreGame.cyclicGenerationMechanism();
        }
    }
}