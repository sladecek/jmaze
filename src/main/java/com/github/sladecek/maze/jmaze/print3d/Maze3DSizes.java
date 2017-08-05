// TODO odstranit nepouzite vlastnosti po zmene 3d mapperu

package com.github.sladecek.maze.jmaze.print3d;

public final class Maze3DSizes {

    public double getCellSizeInmm() {
        return cellSizeInmm;
    }

    public void setCellSizeInmm(double cellSizeInmm) {
        this.cellSizeInmm = cellSizeInmm;
    }


    public double getInnerWallToPixelRatio() {
        return innerWallToPixelRatio;
    }

    public void setInnerWallToPixelRatio(double innerWallToPixelRatio) {
        this.innerWallToPixelRatio = innerWallToPixelRatio;
    }


    public double getWallHeightInmm() {
        return wallHeightInmm;
    }

    public void setWallHeightInmm(double wallHeightInmm) {
        this.wallHeightInmm = wallHeightInmm;
    }


    private double cellSizeInmm = 2;
    private double innerWallToPixelRatio = 1;

    private double wallHeightInmm = 10;

}
