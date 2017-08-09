package com.github.sladecek.maze.jmaze.printstyle;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;

public final class PrintStyle implements IPrintStyle {

    public void configureFromProperties(MazeProperties properties) {
        printAllWalls = properties.getBoolean("printAllWalls");
        printSolution = properties.getBoolean("printSolution");

        startMarkColor = properties.getColor("startMarkColor"); // new Color("ff0000");
        targetMarkColor = properties.getColor("tartgetMarkColor"); //new Color("00ff00");
        solutionMarkColor = properties.getColor("solutionMarkColor"); //new Color("777777");

        startMarkWidth = properties.getInt("startMarkWidth", 1, 1000);
        targetMarkWidth = properties.getInt("targetMarkWidth",1 ,1000);
        solutionMarkWidth = properties.getInt("solutionMarkWidth",1,1000);

      /*  private final boolean printSolution = true;
        private final Color baseColor = new Color("777777");
        private final Color outerWallColor = new Color("000000");
        private final Color innerWallColor = new Color("000000");
        private final Color solutionWallColor = new Color("ff0000");
        private final Color cornerColor = new Color("666666");
        private final Color holeColor = new Color("777777");
        private final Color debugWallColor = new Color("eeeeff");
        private final Color debugFloorColor = new Color("00ff00");
        private final Color threeJsClearColor = new Color("eeeeee");
        private final Color threeJsMeshColor = new Color("33ff33");
        private final Color threeJsAmbientLightColor = new Color("222222");
        private final Color threeJsPointLightColor = new Color("777777");
*/
    }

    @Override
    public boolean isPrintSolution() {
        return printSolution;
    }

    public boolean isPrintAllWalls() {

        return printAllWalls;
    }

    public Color getBaseColor() {
        return baseColor;
    }

    public Color getOuterWallColor() {
        return outerWallColor;
    }

    public Color getInnerWallColor() {
        return innerWallColor;
    }

    public Color getCornerColor() {
        return cornerColor;
    }

    public Color getSolutionWallColor() {
        return solutionWallColor;
    }

    public Color getSolutionMarkColor() {
        return solutionMarkColor;
    }

    @Override
    public Color getStartMarkColor() {
        return startMarkColor;
    }

    @Override
    public Color getTargetMarkColor() {
        return targetMarkColor;
    }

    public Color getHoleColor() {
        return holeColor;
    }

    public Color getDebugWallColor() {
        return debugWallColor;
    }

    public Color getDebugFloorColor() {
        return debugFloorColor;
    }

    public Color getThreeJsClearColor() {
        return threeJsClearColor;
    }

    public Color getThreeJsMeshColor() {
        return threeJsMeshColor;
    }

    public Color getThreeJsAmbientLightColor() {
        return threeJsAmbientLightColor;
    }

    public Color getThreeJsPointLightColor() {
        return threeJsPointLightColor;
    }

    @Override
    public int getInnerWallWidth() {
        return 1;
    }

    @Override
    public int getOuterWallWidth() {
        return 2;
    }

    @Override
    public int getSolutionWallWidth() {
        return 2;
    }


    @Override
    public int getSolutionMarkWidth() {
        return solutionMarkWidth;
    }

    @Override
    public int getStartMarkWidth() {
        return startMarkWidth;
    }

    @Override
    public int getTargetMarkWidth() {
        return targetMarkWidth;
    }

    private boolean printSolution = true;
    private boolean printAllWalls = false;

    private int startMarkWidth = 4;
    private int targetMarkWidth = 4;
    private int solutionMarkWidth = 2;

    private Color startMarkColor = new Color("ff0000");
    private Color targetMarkColor = new Color("00ff00");
    private Color solutionMarkColor = new Color("777777");


    private final Color baseColor = new Color("777777");
    private final Color outerWallColor = new Color("000000");
    private final Color innerWallColor = new Color("000000");
    private final Color solutionWallColor = new Color("ff0000");
    private final Color cornerColor = new Color("666666");
    private final Color holeColor = new Color("777777");
    private final Color debugWallColor = new Color("eeeeff");
    private final Color debugFloorColor = new Color("00ff00");
    private final Color threeJsClearColor = new Color("eeeeee");
    private final Color threeJsMeshColor = new Color("33ff33");
    private final Color threeJsAmbientLightColor = new Color("222222");
    private final Color threeJsPointLightColor = new Color("777777");

}