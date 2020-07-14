/**
 *
 * add more features:
 *
 * increasing/decreasing evolution speed (use buttons or JSlider)
 *
 * color of cells (use JColorChooser or JComboBox)
 *
 * setting size of the new field (use JTextField or JDialog)
 *
 * save/load (use JFileChooser)
 *
 * and so on.
 */
package life;

import java.util.Random;
import java.util.Scanner;

public class Main {
    static int size;

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        size = scanner.nextInt();

        GameOfLife gameOfLife = new GameOfLife();

        GameOfLifeCore game = new GameOfLifeCore(size, gameOfLife);
        gameOfLife.coreGame = game;
        gameOfLife.size = size;
        game.go();

    }
}

class GameOfLifeCore {
    int generation;
    int alive;
    int size;
    GameOfLife gameOfLife;
    char[][] universe = new char[size][size];
    static boolean isRunning = true;
    GenerationAlgorithm algorithm = new GenerationAlgorithm();

    public GameOfLifeCore(int size, GameOfLife gameOfLife) {
        this.size = size;
        this.generation = 1;
        this.gameOfLife = gameOfLife;
    }

    public void go() {

        gameOfLife.setGenerationLabel(generation);
        universe = populateUniverse(size);
        gameOfLife.updateField(gameOfLife.setField(universe));
        //while (true) {
            cyclicGenerationMechanism();
        //}
    }

    public void cyclicGenerationMechanism() {
        while (isRunning) {

            try {
                Thread.sleep(500);
                System.out.print("\033[H\033[2J");
                System.out.flush();
                System.out.println();
            } catch (InterruptedException e) {
            }
            System.out.println("Generation #" + generation);
            gameOfLife.setGenerationLabel(generation);
            alive = calculateNumberOfAliveCells(universe);
            System.out.println("Alive: " + alive);
            gameOfLife.setAliveLabel(alive);
            displayUniverse(universe);

            universe = algorithm.createNextGeneration(universe);
            gameOfLife.updateField(gameOfLife.setField(universe));
            generation++;
        }
    }


    public char[][] populateUniverse(int size) {
        Random random = new Random();
        char[][] universe = new char[size][size];


        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (random.nextBoolean()) {
                    universe[i][j] = 'O';
                } else {
                    universe[i][j] = ' ';
                }
            }
        }
        return universe;
    }

    public void displayUniverse(char[][] universe) {
        for (char[] chars : universe) {
            for (int j = 0; j < universe.length; j++) {
                System.out.print(chars[j]);
            }
            System.out.println();
        }
    }

    public int calculateNumberOfAliveCells(char[][] universe) {
        int cnt = 0;

        for (int i = 0; i < universe.length; i++) {
            for (int j = 0; j < universe.length; j++) {
                if (universe[i][j] == 'O') {
                    cnt++;
                }
            }
        }
        return cnt;
    }
}


class GenerationAlgorithm {

    public char[][] createNextGeneration(char[][] universe, int generation) {

        int origUniverseLength = universe.length;
        char[][] nextGeneration = new char[origUniverseLength][origUniverseLength];

        for (int i = 0; i < origUniverseLength; i++) {
            System.arraycopy(universe[i], 0, nextGeneration[i], 0, origUniverseLength);
        }

        if (generation == 0) {
            return nextGeneration;
        }


        char[][] temporaryUniverse = new char[origUniverseLength + 2][origUniverseLength + 2]; //extend original universe size by 2 to store edge values;

        for (int i = 0; i < origUniverseLength; i++) {
            System.arraycopy(universe[i], 0, temporaryUniverse[i + 1], 1, origUniverseLength);
        }

        for (int i = 0; i < origUniverseLength; i++) {
            temporaryUniverse[0][i + 1] = universe[origUniverseLength - 1][i]; //extend N side and populate with S values of original universe
            temporaryUniverse[origUniverseLength + 1][i + 1] = universe[0][i]; //extend S side and populate with N values of original universe
            temporaryUniverse[i + 1][0] = universe[i][origUniverseLength - 1]; //extend W side and populate with E values of original universe
            temporaryUniverse[i + 1][origUniverseLength + 1] = universe[i][0]; //extend E side and populate with W values of original universe
        }

        // set corner values with diagonally opposite values of original universe
        temporaryUniverse[0][0] = universe[origUniverseLength - 1][origUniverseLength - 1];
        temporaryUniverse[0][origUniverseLength + 1] = universe[origUniverseLength - 1][0];
        temporaryUniverse[origUniverseLength + 1][0] = universe[0][origUniverseLength - 1];
        temporaryUniverse[origUniverseLength + 1][origUniverseLength + 1] = universe[0][0];


        for (int i = 1; i < origUniverseLength + 1; i++) {
            for (int j = 1; j < origUniverseLength + 1; j++) {
                int countOfNeighbours = 0;
                if (temporaryUniverse[i][j - 1] == 'O') {
                    countOfNeighbours++;
                }
                if (temporaryUniverse[i][j + 1] == 'O') {
                    countOfNeighbours++;
                }
                for (int k = 0; k < 3; k++) {
                    if (temporaryUniverse[i - 1][j - 1 + k] == 'O') {
                        countOfNeighbours++;
                    }
                    if (temporaryUniverse[i + 1][j - 1 + k] == 'O') {
                        countOfNeighbours++;
                    }
                }

                if (temporaryUniverse[i][j] == 'O' && countOfNeighbours > 1 && countOfNeighbours < 4) {
                    nextGeneration[i - 1][j - 1] = 'O';
                } else if (temporaryUniverse[i][j] == 'O' && (countOfNeighbours < 2 || countOfNeighbours > 3)) {
                    nextGeneration[i - 1][j - 1] = ' ';
                } else if (temporaryUniverse[i][j] == ' ' && countOfNeighbours == 3) {
                    nextGeneration[i - 1][j - 1] = 'O';
                }
            }
        }

        return nextGeneration;//createNextGeneration(nextGeneration, generation - 1);
    }

    public char[][] createNextGeneration(char[][] universe) {

        int origUniverseLength = universe.length;
        char[][] nextGeneration = new char[origUniverseLength][origUniverseLength];
        char[][] temporaryUniverse = new char[origUniverseLength + 2][origUniverseLength + 2]; //extend original universe size by 2 to store edge values;

        for (int i = 0; i < origUniverseLength; i++) {
            System.arraycopy(universe[i], 0, temporaryUniverse[i + 1], 1, origUniverseLength);
        }

        for (int i = 0; i < origUniverseLength; i++) {
            temporaryUniverse[0][i + 1] = universe[origUniverseLength - 1][i]; //extend N side and populate with S values of original universe
            temporaryUniverse[origUniverseLength + 1][i + 1] = universe[0][i]; //extend S side and populate with N values of original universe
            temporaryUniverse[i + 1][0] = universe[i][origUniverseLength - 1]; //extend W side and populate with E values of original universe
            temporaryUniverse[i + 1][origUniverseLength + 1] = universe[i][0]; //extend E side and populate with W values of original universe
        }

        // set corner values with diagonally opposite values of original universe
        temporaryUniverse[0][0] = universe[origUniverseLength - 1][origUniverseLength - 1];
        temporaryUniverse[0][origUniverseLength + 1] = universe[origUniverseLength - 1][0];
        temporaryUniverse[origUniverseLength + 1][0] = universe[0][origUniverseLength - 1];
        temporaryUniverse[origUniverseLength + 1][origUniverseLength + 1] = universe[0][0];


        for (int i = 1; i < origUniverseLength + 1; i++) {
            for (int j = 1; j < origUniverseLength + 1; j++) {
                int countOfNeighbours = 0;
                if (temporaryUniverse[i][j - 1] == 'O') {
                    countOfNeighbours++;
                }
                if (temporaryUniverse[i][j + 1] == 'O') {
                    countOfNeighbours++;
                }
                for (int k = 0; k < 3; k++) {
                    if (temporaryUniverse[i - 1][j - 1 + k] == 'O') {
                        countOfNeighbours++;
                    }
                    if (temporaryUniverse[i + 1][j - 1 + k] == 'O') {
                        countOfNeighbours++;
                    }
                }

                if (temporaryUniverse[i][j] == 'O' && countOfNeighbours > 1 && countOfNeighbours < 4) {
                    nextGeneration[i - 1][j - 1] = 'O';
                } else if (temporaryUniverse[i][j] == 'O' && (countOfNeighbours < 2 || countOfNeighbours > 3)) {
                    nextGeneration[i - 1][j - 1] = ' ';
                } else if (temporaryUniverse[i][j] == ' ' && countOfNeighbours == 3) {
                    nextGeneration[i - 1][j - 1] = 'O';
                }
            }
        }

        return nextGeneration;
    }
}