package com.github.sladecek.maze.jmaze.print3d;

import java.io.IOException;
import java.io.OutputStream;

public class ThreeJs3DPrinter implements IMaze3DPrinter {

    @Override
    public final void printBlocks(final IBlockMaker blockMaker,
            final OutputStream stream) {
        blockMaker.makeBlocks();
        try (ThreeJsComposer tjs = new ThreeJsComposer(stream)) {
            printPureBlocks(tjs, blockMaker);
            printMarks(tjs, blockMaker);
            tjs.close();
        } catch (IOException ioe) {
            System.out.println(ioe.getMessage());
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

    private void printMarks(ThreeJsComposer tjs, final IBlockMaker blockMaker)
            throws IOException {
        tjs.beginList("marks");
        for (Block b : blockMaker.getBlocks()) {
            if (b.isMark()) {
                assert b.getPolyhedron().size() == 1 : "mark blocks must have exactly one point";
                tjs.printMark(b.getPolyhedron().get(0), b.getComment(),
                        b.getColor());
            }
        }
        final boolean insertComma = false;
        tjs.closeList(insertComma);
    }

}
