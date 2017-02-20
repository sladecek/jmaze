package com.github.sladecek.maze.jmaze.shapes;

/*
 * Contextual information for a collection of shapes.
 */
public class ShapeContext {

    /**
     * Creates a new Shape context.
     * @param isPolarCoordinates
     * @param pictureHeight  picture heigth in pixels (2D) or rooms (3D)
     * @param pictureWidth picture width in pixels (2D) or rooms (3D)
     */
    public ShapeContext(boolean isPolarCoordinates, int pictureHeight, int pictureWidth) {


        super();
        this.isPolarCoordinates = isPolarCoordinates;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;

    }

    private boolean isPolarCoordinates;
    private int pictureHeight;
    private int pictureWidth;

    public boolean isPolarCoordinates() {
        return isPolarCoordinates;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public int getPictureWidth() {
        return pictureWidth;
    }

}
