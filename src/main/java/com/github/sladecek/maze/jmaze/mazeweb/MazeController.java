package com.github.sladecek.maze.jmaze.mazeweb;
import java.io.IOException;
import java.io.StringWriter;
import java.io.Writer;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.github.sladecek.maze.jmaze.generator.DepthFirstMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.IMazeGenerator;
import com.github.sladecek.maze.jmaze.generator.MazeRealization;
import com.github.sladecek.maze.jmaze.print.SvgMazePrinter;
import com.github.sladecek.maze.jmaze.print.XmlWriter;
import com.github.sladecek.maze.jmaze.rectangular.Rectangular2DMaze;

@Controller
public class MazeController {

    @RequestMapping("/maze")
    public String greeting(Model model) {
    
		Rectangular2DMaze maze = new Rectangular2DMaze(27, 27);
    	IMazeGenerator g = new DepthFirstMazeGenerator();
    	MazeRealization r = g.generateMaze(maze);
    	String data = "";
    	Writer writer = new StringWriter();
    	
    	XmlWriter x;
		try {
			x = new XmlWriter(writer);
    	SvgMazePrinter smp = new SvgMazePrinter();
    	
    	smp.printMaze(maze, r,  x);

    	
    	data = writer.toString(); // now it works fine
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

    	
        model.addAttribute("svgmaze", data);
        return "maze";
    }

}
