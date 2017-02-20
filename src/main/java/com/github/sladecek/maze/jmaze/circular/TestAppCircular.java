package com.github.sladecek.maze.jmaze.circular;

import com.github.sladecek.maze.jmaze.print2d.TestApp2DBase;

public class TestAppCircular extends TestApp2DBase {

    public static void main(String[] args) {
        new TestAppCircular().printTestMaze("maze-circular", () -> {
            final int defaultSize = 4;
            return new Circular2DMaze(defaultSize);
        });
    }


}
