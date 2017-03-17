package com.github.sladecek.maze.jmaze.print3d;

import com.github.sladecek.maze.jmaze.model3d.Model3d;

import java.io.FileOutputStream;

/**
 * Created by sla on 3/17/17.
 */
public interface IMaze3DPrinter {
    void printModel(Model3d model, FileOutputStream f);
}
