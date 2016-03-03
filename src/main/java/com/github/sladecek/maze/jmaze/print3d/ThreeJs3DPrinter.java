package com.github.sladecek.maze.jmaze.print3d;

import java.io.IOException;
import java.io.OutputStream;
import java.util.logging.Logger;

public class ThreeJs3DPrinter implements IMaze3DPrinter {

    @Override
    public final void printBlocks(final IBlockMaker blockMaker, boolean showSolution,
            final OutputStream stream) {
        blockMaker.makeBlocks();
        try (ThreeJsComposer tjs = new ThreeJsComposer(stream)) {
            printPureBlocks(tjs, blockMaker);
            printMarks(tjs, blockMaker, showSolution);
            tjs.close();
        } catch (IOException ioe) {
        	LOG.severe("OpenScad3DPrinter failed " + ioe.getMessage());
        }
    }

    private void printPureBlocks(ThreeJsComposer tjs,
            final IBlockMaker blockMaker) throws IOException {
        tjs.beginList("blocks");
        for (Block b : blockMaker.getBlocks()) {
            if (!b.isMark()) {
                tjs.printPolyhedron(b.getPolyhedron(), b.getComment(),
                        b.getColor());
            }

        }
        final boolean insertComma = true;
        tjs.closeList(insertComma);
    }

    private void printMarks(ThreeJsComposer tjs, final IBlockMaker blockMaker, boolean showSolution)
            throws IOException {
        tjs.beginList("marks");
        if (showSolution) {
            for (Block b : blockMaker.getBlocks()) {
                if (b.isMark()) {
                    assert b.getPolyhedron().size() == 1 : "mark blocks must have exactly one point";
                    tjs.printMark(b.getPolyhedron().get(0), b.getComment(),
                            b.getColor());
                }
            }
        }
        final boolean insertComma = false;
        tjs.closeList(insertComma);
    }

    private static final Logger LOG = Logger.getLogger("maze.jmaze");
}
