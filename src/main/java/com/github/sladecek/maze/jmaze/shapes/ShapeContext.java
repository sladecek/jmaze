package com.github.sladecek.maze.jmaze.shapes;
//REV1
/*
 * Contextual information for a collection of shapes.
 */
public class ShapeContext {

    /**
     * Creates a new Shape context.
     *
     * @param isPolarCoordinates the coordinates are polar
     * @param pictureHeight      picture height in pixels (2D) or rooms (3D)
     * @param pictureWidth       picture width in pixels (2D) or rooms (3D)
     */
    public ShapeContext(boolean isPolarCoordinates, int pictureHeight, int pictureWidth, int margin) {


        super();
        this.isPolarCoordinates = isPolarCoordinates;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;
        this.margin = margin;
    }

    public int getMargin() {
        return margin;
    }

    public boolean isPolarCoordinates() {
        return isPolarCoordinates;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public int getPictureWidth() {
        return pictureWidth;
    }

    private final boolean isPolarCoordinates;
    private final int pictureHeight;
    private final int pictureWidth;
    private final int margin;
}
