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

// TODO duplicita, neda se to tahat z descriptions?
public class MazeFactory {
    public Maze newMaze(String name) {
        switch (name) {
            case "rectangular":
                return new RectangularMaze();
            case "circular":
                return new CircularMaze();
            case "hexagonal":
                return new HexagonalMaze();
            case "moebius":
                return new MoebiusMaze();
            case "egg":
            case "ellipsoid":
            case "spheric":
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
