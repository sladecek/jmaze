package com.github.sladecek.maze.jmaze.properties;

import org.junit.Test;

import java.util.ArrayList;

import static org.junit.Assert.*;

public class MazeValidationErrorsTest {
    @Test
    public void getAll() throws Exception {
        MazeValidationErrors mve = new MazeValidationErrors();
        mve.addError("prefx", "kej", "massage1");
        mve.addError("", "kej", "massage2");
        ArrayList<MazeValidationError> res = new ArrayList<>();
        for (MazeValidationError e: mve.getAll()) {
            res.add(e);
        }

        assertEquals(2,res.size());

        assertEquals("massage1", res.get(0).getMessage());
        assertEquals("massage2", res.get(1).getMessage());

        assertEquals("prefx_kej", res.get(0).getField());
        assertEquals("kej", res.get(1).getField());

    }

    @Test
    public void addError() throws Exception {
        MazeValidationErrors mve = new MazeValidationErrors();
        mve.addError("prefx", "kej", "massage");
        assert(!mve.isEmpty());
    }

    @Test
    public void isEmpty() throws Exception {
        MazeValidationErrors mve = new MazeValidationErrors();
        assert(mve.isEmpty());
    }

    @Test
    public void hasErrorForField() throws Exception {
        MazeValidationErrors mve = new MazeValidationErrors();
        assert(!mve.hasErrorForField("prefx_kej"));
        mve.addError("prefx", "kej", "massage");
        assert(mve.hasErrorForField("prefx_kej"));
    }

    @Test
    public void errorsForField() throws Exception {
        MazeValidationErrors mve = new MazeValidationErrors();
        mve.addError("prefx", "kej1", "massage1");
        mve.addError("prefx", "kej2", "massage2");
        mve.addError("prefx", "kej1", "massage3");
        ArrayList<String> res = mve.errorsForField("prefx_kej1");
        assertEquals(2,res.size());
        assertEquals("massage1", res.get(0));
        assertEquals("massage3", res.get(1));
    }

}