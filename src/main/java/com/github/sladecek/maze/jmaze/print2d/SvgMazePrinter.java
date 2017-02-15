package com.github.sladecek.maze.jmaze.print2d;

import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import com.github.sladecek.maze.jmaze.printstyle.DefaultPrintStyle;
import com.github.sladecek.maze.jmaze.printstyle.IPrintStyle;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;
import com.github.sladecek.maze.jmaze.util.MazeGenerationException;

/*
 * Print 2D maze to SVG or PDF. 
 */
public final class SvgMazePrinter  {

    public SvgMazePrinter() {
    }

    public void printShapes(final ShapeContainer shapes, final MazeOutputFormat format,
            final OutputStream output, final boolean showSolution) throws MazeGenerationException {
        SvgDocument sd = createSvgDocument(shapes, showSolution);
        printSvgDocument(format, output, sd);
    }

    public void printSvgDocument(final MazeOutputFormat format, final OutputStream output,
            final SvgDocument sd) throws  MazeGenerationException {
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

        } catch ( TransformerException | TranscoderException e) {
            throw new MazeGenerationException("Printing SVG failed",e);
        }
    }

    public SvgDocument createSvgDocument(final ShapeContainer shapes,
            final boolean showSolution) {
        SvgDocument sd = new SvgDocument(shapes.getContext());
        for (IMazeShape shape : shapes.getShapes()) {
            if (showSolution
                    || shape.getShapeType() != IMazeShape.ShapeType.solution) {

                shape.print2D(sd, printStyle);
            }
        }
        return sd;
    }
    
    private IPrintStyle printStyle = new DefaultPrintStyle(); 
}
