package com.github.sladecek.maze.jmaze.geometry;

/**
 * Left/Right dichotomy.
 */
public enum LeftRight {
    left, right;

    public LeftRight mirror() {
        return this == left ? right : left;
    }
}
