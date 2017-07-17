package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;

import java.util.ArrayList;
import java.util.Collection;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * Complete 3d maze model.
 */
public class Model3d implements IModel3d {

    @Override
    public Collection<MPoint> getPoints() {
        return points;
    }

    @Override
    public Collection<MEdge> getEdges() {
        return edges;
    }

    @Override
    public Collection<MFace> getFaces() {
        return faces;
    }

    @Override
    public Collection<MBlock> getBlocks() {
        return blocks;
    }

    public void addFace(MFace f) {
        faces.add(f);
    }

    public void addFaces(Collection<? extends MFace> newFaces) {
        faces.addAll(newFaces);
    }

    public void addEdges(Collection<? extends MEdge> newEdges) {
        edges.addAll(newEdges);
    }

    public void addEdge(MEdge e) {
        edges.add(e);
    }

    public void addPoints(Collection<? extends MPoint> newPoints) {
        points.addAll(newPoints);
    }

    public void addPoint(MPoint p) {
        points.add(p);
    }

    public void addBlock(MBlock block) {
        blocks.add(block);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    private String name = "";
    private ArrayList<MPoint> points = new ArrayList<>();
    private ArrayList<MEdge> edges = new ArrayList<>();
    private ArrayList<MFace> faces = new ArrayList<>();
    private ArrayList<MBlock> blocks = new ArrayList<>();

    @Override
    public String toString() {
        return "Model3d{" +
                "name='" + name + '\n' +
                "\n points=" + points +
                "\n\n edges=" + edges +
                "\n\n faces=\n" + facesToString() +
                "\n\n blocks=" + blocks +
                '}';
    }

    private String facesToString() {
        return faces
                .stream()
                .map(f->"   "+f.toString())
                .collect(Collectors.joining("\n"));
    }
}
