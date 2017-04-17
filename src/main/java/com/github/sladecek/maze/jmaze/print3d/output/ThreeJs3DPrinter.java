package com.github.sladecek.maze.jmaze.print3d.output;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;


public class ThreeJs3DPrinter implements IMaze3DPrinter {

    public ThreeJs3DPrinter() {
        super();

    }

    /*
        @Override
        public final void printBlocks(final IBlockMaker blockMaker, boolean showSolution,
                final OutputStream stream) throws MazeGenerationException {
            blockMaker.makeBlocks();
            try (ThreeJsComposer tjs = new ThreeJsComposer(stream)) {
                printPureBlocks(tjs, blockMaker);
                printMarks(tjs, blockMaker, showSolution);
                printColors(tjs);
            } catch (IOException ioe) {
                throw new MazeGenerationException("printBlocsk failed", ioe);
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
            final boolean insertComma = true;
            tjs.closeList(insertComma);
        }
    */
    private void printColors(ThreeJsComposer tjs)
            throws IOException {

        tjs.printColor("clearColor", printStyle.getThreeJsClearColor(), true);
        tjs.printColor("meshColor", printStyle.getThreeJsMeshColor(), true);
        tjs.printColor("ambientLightColor", printStyle.getThreeJsAmbientLightColor(), true);
        tjs.printColor("pointLightColor", printStyle.getThreeJsPointLightColor(), false);
    }

    @Override
    public void printModel(Model3d model, FileOutputStream f) {

    }


    private IPrintStyle printStyle;

    private static final Logger LOG = Logger.getLogger("maze.jmaze");
}
