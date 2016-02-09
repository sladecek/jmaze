package com.github.sladecek.maze.jmaze.print2d;

import java.io.OutputStream;

import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.batik.transcoder.Transcoder;
import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.fop.svg.PDFTranscoder;

import com.github.sladecek.maze.jmaze.shapes.IMazeShape;
import com.github.sladecek.maze.jmaze.shapes.ShapeContainer;

/*
 * Print 2D maze to SVG or PDF. 
 */
public final class SvgMazePrinter  {

    public SvgMazePrinter() {
        super();
    }

    public void printShapes(ShapeContainer shapes, MazeOutputFormat format,
            OutputStream output, boolean showSolution) {
        SvgDocument sd = createSvgDocument(shapes, showSolution);
        printSvgDocument(format, output, sd);
    }

    public void printSvgDocument(MazeOutputFormat format, OutputStream output,
            SvgDocument sd) throws TransformerFactoryConfigurationError {
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

        } catch (TransformerConfigurationException e) {
            e.printStackTrace();
        } catch (TransformerException e) {
            e.printStackTrace();
        } catch (TranscoderException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public SvgDocument createSvgDocument(ShapeContainer shapes,
            boolean showSolution) {
        SvgDocument sd = new SvgDocument(shapes.getContext());
        for (IMazeShape shape : shapes.getShapes()) {
            if (showSolution
                    || shape.getShapeType() != IMazeShape.ShapeType.solution) {

                shape.print2D(sd);
            }
        }
        return sd;
    }
}
