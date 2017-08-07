package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.circular.CircularMaze;
import com.github.sladecek.maze.jmaze.hexagonal.HexagonalMaze;
import com.github.sladecek.maze.jmaze.moebius.MoebiusMaze;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMaze;
import com.github.sladecek.maze.jmaze.spheric.EggMaze;
import com.github.sladecek.maze.jmaze.triangular.TriangularMaze;
import com.github.sladecek.maze.jmaze.voronoi.VoronoiMaze;

/**
 * Creates a maze.
 */
public class MazeFactory {
    public BaseMaze newMaze(String name) {
        switch (name) {
            case "rect":
                return new RectangularMaze();
            case "circular":
                return new CircularMaze();
            case "hexagonal":
                return new HexagonalMaze();
            case "moebius":
                return new MoebiusMaze();
            case "egg":
                return new EggMaze();
            case "triangular":
                return new TriangularMaze();
            case "voronoi":
                return new VoronoiMaze();
            default:
                throw new IllegalArgumentException("Unknown maze type.");
        }
    }


}
