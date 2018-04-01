package com.github.sladecek.maze.jmaze.maze;
//REV1
import com.github.sladecek.maze.jmaze.makers.circular.CircularMazeDescription;
import com.github.sladecek.maze.jmaze.makers.hexagonal.HexagonalMazeDescription;
import com.github.sladecek.maze.jmaze.makers.moebius.MoebiusMazeDescription;
import com.github.sladecek.maze.jmaze.makers.rectangular.RectangularMazeDescription;
import com.github.sladecek.maze.jmaze.makers.spheric.EggMazeDescription;
import com.github.sladecek.maze.jmaze.makers.spheric.EllipsoidMazeDescription;
import com.github.sladecek.maze.jmaze.makers.spheric.SphericMazeDescription;
import com.github.sladecek.maze.jmaze.makers.triangular.TriangularMazeDescription;
import com.github.sladecek.maze.jmaze.makers.voronoi.VoronoiMazeDescription;
import com.github.sladecek.maze.jmaze.properties.MazeDescription;

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
        for (MazeDescription d : allMazes) {
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
