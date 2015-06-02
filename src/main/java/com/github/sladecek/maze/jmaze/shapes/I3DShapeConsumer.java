package com.github.sladecek.maze.jmaze.shapes;


public interface I3DShapeConsumer {
	void consumeWall(LineShape ls);
	void consumeHole(HoleShape hs);

}
