package com.github.sladecek.maze.jmaze.print;

public final class WinterColors implements MazeColors {

private final Color baseColor = new Color("777777");
	private final Color outerWallColor = new Color("666666");
	private final Color innerWallColor = new Color("777777");
	private final Color cornerColor = new Color("666666");
	private final Color holeColor = new Color("777777");
	private final Color debugWallColor = new Color("ff0000");
	private final Color debugFloorColor = new Color("00ff00");
	
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
	public Color getHoleColor() {
		return holeColor;
	}
	public Color getDebugWallColor() {
		return debugWallColor;
	}
	public Color getDebugFloorColor() {
		return debugFloorColor;
	}
	
	}