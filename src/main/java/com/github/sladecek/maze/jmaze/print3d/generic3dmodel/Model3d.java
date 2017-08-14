package com.github.sladecek.maze.jmaze.print3d.generic3dmodel;



import java.util.ArrayList;
import java.util.Collection;
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
        if (f == null) {
            throw new IllegalArgumentException("Model cannot have null faces.");
        }
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
    private final ArrayList<MPoint> points = new ArrayList<>();
    private final ArrayList<MEdge> edges = new ArrayList<>();
    private final ArrayList<MFace> faces = new ArrayList<>();
    private final ArrayList<MBlock> blocks = new ArrayList<>();

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
