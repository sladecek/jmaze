package com.github.sladecek.maze.jmaze.maze;

import com.github.sladecek.maze.jmaze.circular.CircularMazeDescription;
import com.github.sladecek.maze.jmaze.hexagonal.HexagonalMazeDescription;
import com.github.sladecek.maze.jmaze.moebius.MoebiusMazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeDescription;
import com.github.sladecek.maze.jmaze.rectangular.RectangularMazeDescription;
import com.github.sladecek.maze.jmaze.spheric.EggMazeDescription;
import com.github.sladecek.maze.jmaze.spheric.EllipsoidMazeDescription;
import com.github.sladecek.maze.jmaze.spheric.SphericMazeDescription;
import com.github.sladecek.maze.jmaze.triangular.TriangularMazeDescription;
import com.github.sladecek.maze.jmaze.voronoi.VoronoiMazeDescription;

import java.util.ArrayList;
import java.util.stream.Collectors;

/**
 * Static list of all available maze types in their logical order.
 */
public class AllMazeTypes {
    public static ArrayList<MazeDescription> getAllMazes() {
        return allMazes;
    }

    public static MazeDescription findByName(String name) {
        for (MazeDescription d: allMazes) {
            if (name.equals(d.getName())) {
                return d;
            }
        }
        return null;
    }

    public static String listOfNamesAsString() {
        return allMazes
                .stream()
                .map(MazeDescription::getName)
                .collect(Collectors.joining(", "));
    }

    private static final ArrayList<MazeDescription> allMazes;

    static {
        allMazes = new ArrayList<>();

        allMazes.add(new RectangularMazeDescription());
        allMazes.add(new TriangularMazeDescription());
        allMazes.add(new CircularMazeDescription());
        allMazes.add(new HexagonalMazeDescription());
        allMazes.add(new SphericMazeDescription());
        allMazes.add(new EllipsoidMazeDescription());
        allMazes.add(new EggMazeDescription());
        allMazes.add(new VoronoiMazeDescription());
        allMazes.add(new MoebiusMazeDescription());
    }
}
