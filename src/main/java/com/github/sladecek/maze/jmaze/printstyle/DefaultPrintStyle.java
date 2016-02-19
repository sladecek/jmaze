package com.github.sladecek.maze.jmaze.printstyle;

public final class DefaultPrintStyle implements IPrintStyle {

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
		return 15;
	}

	@Override
	public int getStartTargetMarkWidth() {
		return 25;
	}

	private final Color baseColor = new Color("777777");
	private final Color outerWallColor = new Color("000000");
	private final Color innerWallColor = new Color("000000");
	private final Color solutionWallColor = new Color("ff0000");
	private final Color solutionMarkColor = new Color("777777");
	private final Color startMarkColor = new Color("ff0000");
	private final Color targetMarkColor = new Color("00ff00");
	private final Color cornerColor = new Color("666666");
	private final Color holeColor = new Color("777777");
	private final Color debugWallColor = new Color("ff0000");
	private final Color debugFloorColor = new Color("00ff00");


}