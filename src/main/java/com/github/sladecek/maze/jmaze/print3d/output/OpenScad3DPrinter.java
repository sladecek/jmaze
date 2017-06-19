package com.github.sladecek.maze.jmaze.print3d.output;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;

import java.io.FileOutputStream;
import java.util.logging.Logger;

/**
 * Save maze model in open scad file format.
 */

public class OpenScad3DPrinter implements IMaze3DPrinter {

    @Override
    public void printModel(Model3d model, FileOutputStream f) {

    }

    /*
        public final void printBlocks(final IBlockMaker blockMaker, boolean showSolution,
                final OutputStream stream) throws MazeGenerationException {
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
                throw new MazeGenerationException("OpenScad3DPrinter failed", ioe);
            }
        }
    */
    private static final Logger LOG = Logger.getLogger("maze.jmaze");
}
