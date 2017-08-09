package com.github.sladecek.maze.jmaze.print2d;

import com.github.sladecek.maze.jmaze.printstyle.PrintStyle;
import com.github.sladecek.maze.jmaze.properties.MazeProperties;
import com.github.sladecek.maze.jmaze.shapes.IPrintableMazeShape2D;
import com.github.sladecek.maze.jmaze.shapes.Shapes;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;
import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import javax.xml.transform.*;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.OutputStream;

/*
 * Print 2D maze to SVG or PDF. 
 */
public final class SvgMazePrinter {

    public SvgMazePrinter(MazeProperties properties) {
        printStyle.configureFromProperties(properties);
    }

    public void printShapes(final Shapes shapes, final MazeOutputFormat format,
                            final OutputStream output) throws MazeGenerationException {
        SvgDocument sd = createSvgDocument(shapes);
        printSvgDocument(format, output, sd);
    }

    public void printSvgDocument(final MazeOutputFormat format, final OutputStream output,
                                 final SvgDocument sd) throws MazeGenerationException {
        try {
            switch (format) {
                case svg:
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

    public SvgDocument createSvgDocument(final Shapes shapes) {
        SvgDocument sd = new SvgDocument(shapes.getContext());
        for (IPrintableMazeShape2D shape : shapes.getShapes()) {
            shape.print2D(sd, printStyle);
        }
        return sd;
    }

    private final PrintStyle printStyle = new PrintStyle();
}
