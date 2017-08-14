package com.github.sladecek.maze.jmaze.print3d.output;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;

import java.io.IOException;
import java.io.OutputStream;

/**
 * Print 3D maze into a file.
 */
interface IMaze3DPrinter {
    void printModel(Model3d model, OutputStream stream) throws IOException;
}
