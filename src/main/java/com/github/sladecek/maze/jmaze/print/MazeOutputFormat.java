package com.github.sladecek.maze.jmaze.print;
//REV1

/**
 * Output formats for maze printout.
 */
public enum MazeOutputFormat {
	svg, pdf, json2d, json3d , stl, scad;

	public boolean is2D() {
	    return (this == svg || this == pdf || this == json2d);
    }

    public boolean is3D() {
        return !is2D();
    }

    public String fileExtension() {
	    return this.name();
    }

    public String contentType() {
	    switch (this) {
	        // TODO
            case svg: return "cosi/svg";
            case scad: return "application/openscad";
            case json2d:
            case json3d:return "application/json";
            default: return "application/"+this.name();
        }
    }

    public boolean isJson() {
	    return this == json2d || this == json3d;
    }
}
