package com.github.sladecek.maze.jmaze.properties;

import com.github.sladecek.maze.jmaze.makers.spheric.EllipsoidMazeDescription;
import org.junit.Before;
import org.junit.Test;

import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;

public class MazeDescriptionTest {

    @Before
    public void setUp() {
        md = new MazeDescription() {
            @Override
            public boolean canBePrintedIn2D() {
                return false;
            }

            @Override
            public boolean canBePrintedIn3D() {
                return false;
            }

            @Override
            public String getName() {
                return null;
            }

            @Override
            public Class getMazeClass() {
                return null;
            }
        };
    }

    @Test
    public void getOwnOptions() throws Exception {
        assertEquals(0, md.getOwnOptions().size());
    }

    @Test
    public void getOwnProperties() throws Exception {
        assert(md.getOwnProperties().isEmpty());
    }

    @Test
    public void getDefaultProperties() throws Exception {
        MazeProperties mp = md.getDefaultProperties();
        assert(!mp.isEmpty());
        assertEquals(0, mp.getInt("randomSeed"));
        assertEquals(true, mp.getBoolean("stl"));
        assertEquals(true, mp.getBoolean("printSolution"));
    }

    @Test
    public void getUniversalOptions() throws Exception {
        List<MazeOption> mo = md.getUniversalOptions();
        assertEquals(1, mo.size());
    }

    @Test
    public void get2dOptions() throws Exception {
        List<MazeOption> mo = md.get2dOptions();
        assertEquals(16, mo.size());
    }

    @Test
    public void get3dOptions() throws Exception {
        List<MazeOption> mo = md.get3dOptions();
        assertEquals(6, mo.size());
    }

    @Test
    public void convertAndValidate() throws Exception {
        MazeProperties mp = md.getDefaultProperties();
        MazeValidationErrors me = md.convertAndValidate(mp, "innerWallWidth", Locale.US);
        assert(me.isEmpty());

        mp.put("innerWallWidth", new Integer(-1));
        me = md.convertAndValidate(mp, "innerWallWidth", Locale.US);
        assert(!me.isEmpty());
    }


    @Test(expected = InternalError.class)
    public void findOptionFail() throws Exception {
        MazeOption mo = md.findOwnOption("stlxxx");
    }

    @Test
    public void findOptionSucceed() throws Exception {
        MazeDescription mde  = new EllipsoidMazeDescription();
        MazeOption mo = mde.findOwnOption("eggness");
        assertEquals("eggness", mo.getName());
    }

    private MazeDescription md;

}