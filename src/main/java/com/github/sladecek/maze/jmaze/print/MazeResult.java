package com.github.sladecek.maze.jmaze.print;

import java.io.Serializable;

/**
 * Result of maze generation.
 */
public class MazeResult implements Serializable {

    public MazeResult(String fileName, MazeOutputFormat format, String content) {
        this.fileName = fileName;
        this.format = format;
        this.content = content;
    }

    public String getFileName() {
        return fileName;
    }

    public MazeOutputFormat getFormat() {
        return format;
    }

    public String getContent() {
        return content;
    }

    String fileName;
    MazeOutputFormat format;
    private String content;
}
