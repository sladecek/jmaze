package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.util.MazeGenerationException;
import eu.mihosoft.jcsg.CSG;
import eu.mihosoft.jcsg.Polyhedron;
import eu.mihosoft.vvecmath.Vector3d;
import org.neo4j.cypher.internal.compiler.v2_1.ast.Collection$;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Creates STL file using JCSG library.
 */
public class StlMazePrinter implements IMaze3DPrinter {

    @Override
    public void printBlocks(IBlockMaker blockMaker, boolean showSolution, OutputStream stream) throws MazeGenerationException {

        blockMaker.makeBlocks();
        CSG scene = null;

        int i = 0;
        for (Block b : blockMaker.getBlocks()) {
            i++;
            //if (i==2 || i == 1)
            {
                System.out.println(b.getPolyhedron());
                CSG block = block2csg(b);
                if (scene == null) {
                    scene = block;
                } else if (block != null) {
                    scene = scene.union(block);
                }
            }
        }
        try {
            if (scene != null) {
                String output = scene.toStlString();
                OutputStreamWriter osw = new OutputStreamWriter(stream);
                osw.write(output);
                osw.flush();
            }

        } catch (IOException ioe) {
            throw new MazeGenerationException("Stl3DPrinter failed", ioe);
        }
    }

    private CSG block2csg(final Block b) {
        final Integer[][] faces =  {
                {0, 3, 2}, {0, 1, 3 },
                {0, 5, 1}, {0, 4, 5 },
                {5, 6, 7}, {5, 4, 6 },
                {6, 3, 7}, {6, 2, 3 },
                {1, 7, 3}, {1, 5, 7 },
                {6, 0, 2}, {6, 4, 0 }
        };

        Stream<Vector3d> sv = b.getPolyhedron().stream().map(p -> Vector3d.xyz(p.getX(), p.getY(), p.getZ()));
        Vector3d[] vertices =  sv.toArray(sz -> new Vector3d[sz]);
        Polyhedron p = new Polyhedron(vertices, faces);
        return p.toCSG();
    }
}
