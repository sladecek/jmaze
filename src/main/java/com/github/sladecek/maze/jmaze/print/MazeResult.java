package com.github.sladecek.maze.jmaze.print;

/**
 * Result of maze generation.
 */
public class MazeResult {

    public MazeResult(String fileName, MazeOutputFormat format, byte[] content) {
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

    public byte[] getContent() {
        return content;
    }

    String fileName;
    MazeOutputFormat format;
    private byte[] content;
}
