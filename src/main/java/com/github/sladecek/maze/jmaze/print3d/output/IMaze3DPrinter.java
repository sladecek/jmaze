package com.github.sladecek.maze.jmaze.print3d.output;

import com.github.sladecek.maze.jmaze.print3d.generic3dmodel.Model3d;

import java.io.FileOutputStream;

/**
 * Print 3D maze into a file.
 */
public interface IMaze3DPrinter {
    void printModel(Model3d model, FileOutputStream f);
}
