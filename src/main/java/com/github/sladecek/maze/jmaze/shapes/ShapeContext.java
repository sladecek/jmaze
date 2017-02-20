package com.github.sladecek.maze.jmaze.shapes;

/*
 * Contextual information for a collection of shapes.
 */
public class ShapeContext {

    public ShapeContext(boolean isPolarCoordinates, int pictureHeight, int pictureWidth) {


        super();
        this.isPolarCoordinates = isPolarCoordinates;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;
  /*      this.resolutionX = resolutionX;
        this.resolutionY = resolutionY;*/

    }

    private boolean isPolarCoordinates;
    private int pictureHeight;
    private int pictureWidth;
  /*  private int resolutionX;
    private int resolutionY;
    private int markOffsetXPercent;
    private int markOffsetYPercent;
*/
    public boolean isPolarCoordinates() {
        return isPolarCoordinates;
    }

    public int getPictureHeight() {
        return pictureHeight;
    }

    public int getPictureWidth() {
        return pictureWidth;
    }
/*
    public int getResolutionX() {
        return resolutionX;
    }

    public int getResolutionY() {
        return resolutionY;
    }
*/

}
