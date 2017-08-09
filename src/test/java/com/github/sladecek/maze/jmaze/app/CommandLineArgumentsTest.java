package com.github.sladecek.maze.jmaze.app;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Tests <code>{@link CommandLineArguments}</code> class.
 */
public class CommandLineArgumentsTest {

    @Test
    public void defaultIsError() throws Exception {
        CommandLineArguments cla = new CommandLineArguments( new String[] {});
        assertTrue(cla.hasErrors());
    }

    @Test
    public void parseEmptyArguments() throws Exception {
        CommandLineArguments cla = new CommandLineArguments( new String[] {});
        cla.parseArguments();
        assertTrue(cla.hasErrors());
    }

    @Test
    public void parseHelp() throws Exception {
        CommandLineArguments cla = new CommandLineArguments( new String[] {"--help"});
        cla.parseArguments();
        assertFalse(cla.hasErrors());
        assertTrue(cla.isUsageRequired());
    }

    @Test
    public void parseTypeOnly() throws Exception {
        CommandLineArguments cla = new CommandLineArguments( new String[] {"rect"});
        cla.parseArguments();
        assertFalse(cla.hasErrors());
        assertFalse(cla.isUsageRequired());
        assertEquals("rect", cla.getMazeType());
        assertFalse(cla.isPropertiesListRequired());
        assertTrue(cla.getProperties().isEmpty());
    }

    @Test
    public void parseListRequest() throws Exception {
        CommandLineArguments cla = new CommandLineArguments( new String[] {"rect", "--list"});
        cla.parseArguments();
        assertFalse(cla.hasErrors());
        assertFalse(cla.isUsageRequired());
        assertEquals("rect", cla.getMazeType());
        assertTrue(cla.isPropertiesListRequired());
    }



}