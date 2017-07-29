// TODO odstranit nepouzite vlastnosti po zmene 3d mapperu

package com.github.sladecek.maze.jmaze.print3d;

public final class Maze3DSizes {

    public double getCellSizeInmm() {
        return cellSizeInmm;
    }

    public void setCellSizeInmm(double cellSizeInmm) {
        this.cellSizeInmm = cellSizeInmm;
    }

    public double getBaseThicknessInmm() {
        return baseThicknessInmm;
    }

    public void setBaseThicknessInmm(double baseThicknessInmm) {
        this.baseThicknessInmm = baseThicknessInmm;
    }

    public double getInnerWallToPixelRatio() {
        return innerWallToPixelRatio;
    }

    public void setInnerWallToPixelRatio(double innerWallToPixelRatio) {
        this.innerWallToPixelRatio = innerWallToPixelRatio;
    }

    public double getOuterWallToPixelRatio() {
        return outerWallToPixelRatio;
    }

    public void setOuterWallToPixelRatio(double outerWallToPixelRatio) {
        this.outerWallToPixelRatio = outerWallToPixelRatio;
    }

    public double getWallHeightInmm() {
        return wallHeightInmm;
    }

    public void setWallHeightInmm(double wallHeightInmm) {
        this.wallHeightInmm = wallHeightInmm;
    }


    private double cellSizeInmm = 4;
    private double baseThicknessInmm = 1;
    private double innerWallToPixelRatio = 0.2;
    private double outerWallToPixelRatio = 0.4;
    private double wallHeightInmm = 10;

}
