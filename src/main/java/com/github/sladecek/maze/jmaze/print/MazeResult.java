package com.github.sladecek.maze.jmaze.print;
//REV1
import java.io.Serializable;

/**
 * Result of maze generation. Can be saved to a file or used by web application.
 */
public class MazeResult implements Serializable {

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

    private final String fileName;
    private final MazeOutputFormat format;
    private final byte[] content;
}
