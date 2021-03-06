package jlopez29.github.io.mazeswipe;

import android.util.Log;

import java.util.Arrays;
import java.util.Collections;

import static jlopez29.github.io.mazeswipe.GameScreen.mazeView;

/**
 * Created by JLOPEZ on 6/18/2018.
 */

public class MazeGenerator {
    private final int x;
    private final int y;
    private final int[][] maze;
    private String end = "";

    public MazeGenerator(int x, int y) {
        this.x = x;
        this.y = y;
        maze = new int[this.x][this.y];
        generateMaze(0, 0);
    }

    public int[][] getMaze()
    {
        return maze;
    }

    public String getEnd()
    {
        return end;
    }

    public void display() {
        String mazeBoard = "";
        for (int i = 0; i < y; i++) {
            // draw the north edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 1) == 0 ? "+---" : "+   ");
                mazeBoard += (maze[j][i] & 1) == 0 ? "+---" : "+     ";
            }
            System.out.println("+");
            mazeBoard += "+" + System.getProperty ("line.separator");

            // draw the west edge
            for (int j = 0; j < x; j++) {
                System.out.print((maze[j][i] & 8) == 0 ? "|   " : "    ");
                mazeBoard += (maze[j][i] & 8) == 0 ? " |    " : "        ";
            }
            System.out.println("|");
            mazeBoard += "|" + System.getProperty ("line.separator");
        }

        // draw the bottom line
        for (int j = 0; j < x; j++) {
            System.out.print("+---");
            mazeBoard += "+---";
        }
        System.out.println("+");
        mazeBoard += "+" + System.getProperty ("line.separator");

        mazeView.setText(mazeBoard);

        Log.e("End", "is " + end);

    }

    private void generateMaze(int cx, int cy) {
        DIR[] dirs = DIR.values();
        Collections.shuffle(Arrays.asList(dirs));
        for (DIR dir : dirs) {
            int nx = cx + dir.dx;
            int ny = cy + dir.dy;
            if (between(nx, x) && between(ny, y) && (maze[nx][ny] == 0)) {
                maze[cx][cy] |= dir.bit;
                maze[nx][ny] |= dir.opposite.bit;
                end = nx + "" + ny;
                generateMaze(nx, ny);
            }
        }
    }

    private static boolean between(int v, int upper) {
        return (v >= 0) && (v < upper);
    }

    private enum DIR {
        N(1, 0, -1), S(2, 0, 1), E(4, 1, 0), W(8, -1, 0);
        private final int bit;
        private final int dx;
        private final int dy;
        private DIR opposite;

        // use the static initializer to resolve forward references
        static {
            N.opposite = S;
            S.opposite = N;
            E.opposite = W;
            W.opposite = E;
        }

        private DIR(int bit, int dx, int dy) {
            this.bit = bit;
            this.dx = dx;
            this.dy = dy;
        }
    };

    public static void main(String[] args) {
        int x = args.length >= 1 ? (Integer.parseInt(args[0])) : 8;
        int y = args.length == 2 ? (Integer.parseInt(args[1])) : 8;
        MazeGenerator maze = new MazeGenerator(x, y);
        maze.display();
    }

}