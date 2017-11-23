package com.github.sladecek.maze.jmaze.makers.moebius;

import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import org.junit.Before;
import org.junit.Test;

import java.util.function.Predicate;

import static org.junit.Assert.*;


public class MoebiusMazeWallOpeningProbabilityTest {
    private MoebiusMaze maze;


    @Before
    public final void setUp() throws Exception {
        maze = new MoebiusMaze();

        MazeProperties p = new MoebiusMazeDescription().getDefaultProperties();
        p.put("sizeAlong", 40);
        p.put("sizeAcross", 20);
        maze.setProperties(p);

        maze.makeMazeAllSteps(false);
    }


    @Test
    public final void testAlongOpenings() {
        testOneCount((i)->maze.isAlongWall(i), 800, 389);
    }
    @Test
    public final void testAcrossOpenings() {
        testOneCount((i)->maze.isAcrossWall(i), 760, 672);
    }
    @Test
    public final void testHoleOpenings() {
        testOneCount((i)->maze.isHole(i), 400, 100);
    }

    private void testOneCount(Predicate<Integer> p, int expectedCnt, int expectedOpCnt) {
        int cnt = 0;
        int opCnt = 0;
        for (int w=0; w < maze.getGraph().getWallCount(); w++) {
            if (p.test(w)) {
                cnt++;
                if (maze.getPath().isWallClosed(w)) {
                    opCnt++;
                }
            }
        }
        assertEquals(expectedCnt, cnt);
        assertEquals(expectedOpCnt, opCnt);
    }


}
