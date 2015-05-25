package com.github.sladecek.maze.jmaze;

import com.github.sladecek.maze.jmaze.shapes.HoleShape;
import com.github.sladecek.maze.jmaze.shapes.LineShape;

public interface I3DShapeConsumer {
	void consumeWall(LineShape ls);
	void consumeHole(HoleShape hs);

}
