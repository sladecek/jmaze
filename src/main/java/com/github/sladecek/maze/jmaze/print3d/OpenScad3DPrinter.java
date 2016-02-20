package com.github.sladecek.maze.jmaze.print3d;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

/**
 * Save maze blocks in open scad file format.
 */
public class OpenScad3DPrinter implements IMaze3DPrinter {

    public final void printBlocks(final IBlockMaker blockMaker,
            final OutputStream stream) {
        blockMaker.makeBlocks();
        try (OpenScadComposer scad = new OpenScadComposer(stream)) {

            scad.beginUnion();
            for (Block b : blockMaker.getBlocks()) {
                // no marks in scad
                if (!b.isMark()) {
                    scad.printPolyhedron(b.getPolyhedron(), b.getComment(),
                            b.getColor());
                }
            }

            scad.closeUnion();

        } catch (IOException ioe) {
            LOG.severe("OpenScad3DPrinter failed " + ioe.getMessage());
        }
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");
}