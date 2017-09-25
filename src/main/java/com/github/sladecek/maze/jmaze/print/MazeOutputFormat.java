package com.github.sladecek.maze.jmaze.print;

public enum MazeOutputFormat {
	svg, pdf, json, stl, scad;

	public boolean is2D() {
	    return (this == svg || this == pdf);
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
            default: return "application/"+this.name();
        }
    }

}
