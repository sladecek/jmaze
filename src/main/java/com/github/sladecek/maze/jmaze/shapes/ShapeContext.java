package com.github.sladecek.maze.jmaze.shapes;

public class ShapeContext {
    
    public ShapeContext(boolean isPolarCoordinates, int pictureHeight,
            int pictureWidth, int resolution, int markOffsetXPercent,
            int markOffsetYPercent) {
        super();
        this.isPolarCoordinates = isPolarCoordinates;
        this.pictureHeight = pictureHeight;
        this.pictureWidth = pictureWidth;
        this.resolution = resolution;
        this.markOffsetXPercent = markOffsetXPercent;
        this.markOffsetYPercent = markOffsetYPercent;
    }
    private boolean isPolarCoordinates;
    private int pictureHeight;
    private int pictureWidth;
    private int resolution;
    private int markOffsetXPercent;
    private int markOffsetYPercent;
    
    public boolean isPolarCoordinates() {
        return isPolarCoordinates;
    }
    public int getPictureHeight() {
        return pictureHeight;
    }
    public int getPictureWidth() {
        return pictureWidth;
    }
    public int getResolution() {
        return resolution;
    }
    public int getMarkOffsetXPercent() {
        return markOffsetXPercent;
    }
    public int getMarkOffsetYPercent() {
        return markOffsetYPercent;
    }
}
