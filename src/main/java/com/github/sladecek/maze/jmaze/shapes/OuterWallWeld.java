package com.github.sladecek.maze.jmaze.shapes;

/**
 * A Weld connects two outer walls together so that two layers of a maze can make a sandwich.
 */
public class OuterWallWeld {
    public OuterWallWeld(WallShape w1, WallShape w2, boolean swap) {
        this.w1 = w1;
        this.w2 = w2;
        this.swap = swap;
    }

    private WallShape w1;
    private WallShape w2;
    private boolean swap;
}
