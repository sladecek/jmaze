package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.print.IMazePrinter;
import com.github.sladecek.maze.jmaze.print.MazeOutputFormat;
import com.github.sladecek.maze.jmaze.print.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.IPrintableMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.maze.MazeGenerationException;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.IOException;
import java.io.OutputStream;

/*
 * Print 2D maze to SVG or PDF. 
 */
public final class SvgMazePrinter implements IMazePrinter
{
    private final MazeOutputFormat format;

    private final Shapes shapes;

    public SvgMazePrinter(MazeProperties properties, MazeOutputFormat format, Shapes shapes) {
        this.format = format;
        this.shapes = shapes;
        printStyle.configureFromProperties(properties);
    }



    @Override
    public void print(OutputStream stream) throws IOException, MazeGenerationException {
        SvgDocument sd = createSvgDocument();
        canvasSize = new Point2DInt(sd.getCanvasWidth(), sd.getCanvasHeight());
        printSvgDocument(format, stream, sd);
    }

    @Override
    public Point2DInt get2dCanvasSize() {

        return canvasSize;
    }

    private void printSvgDocument(final MazeOutputFormat format, final OutputStream output,
                                  final SvgDocument sd) throws MazeGenerationException {
        try {
            switch (format) {
                case svg:
                case json2d:
                    Source source = new DOMSource(sd.getDocument());
                    Result result = new StreamResult(output);
                    TransformerFactory factory = TransformerFactory.newInstance();
                    Transformer transformer = factory.newTransformer();
                    transformer.transform(source, result);
                    break;
                case pdf:
                    Transcoder transcoder = new PDFTranscoder();
                    TranscoderInput transcoderInput = new TranscoderInput(
                            sd.getDocument());
                    TranscoderOutput transcoderOutput = new TranscoderOutput(output);
                    transcoder.transcode(transcoderInput, transcoderOutput);
                    break;
                default:
                    throw new IllegalArgumentException();
            }

        } catch (TransformerException | TranscoderException e) {
            throw new MazeGenerationException("Printing SVG failed", e);
        }
    }

    public SvgDocument createSvgDocument() {
        SvgDocument sd = new SvgDocument(shapes.getContext());
        for (IMazeShape shape : shapes.getShapes()) {
            if (shape instanceof IPrintableMazeShape2D) {
                ((IPrintableMazeShape2D)shape).print2D(sd, printStyle);
            }
        }
        return sd;
    }



    private final PrintStyle printStyle = new PrintStyle();
    private Point2DInt canvasSize;
}
