package com.github.sladecek.maze.jmaze.hexagonal;

import java.util.Vector;
import java.util.logging.Logger;

import com.github.sladecek.maze.jmaze.maze.IMazeStructure;
import com.github.sladecek.maze.jmaze.maze.Maze;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.IMazeShape.ShapeType;
import com.github.sladecek.maze.jmaze.shapes.ShapeContext;
import com.github.sladecek.maze.jmaze.shapes.WallShape;

public class Hexagonal2DMaze extends Maze implements IMazeStructure {

	    private int size;
	    
	    public Hexagonal2DMaze(int size) {
	        super();
	        this.size = size;
	        buildMaze();
	    }

	    // Perimeter of the hexagon.
        static final int hP = 10;
        
        /// Halb-height of the hexagon
        static final int hH = (int)Math.floor(hP*Math.sqrt(3f)/2f);

	    

	    // parameters of six walls of a hexagon
	    static final int[] walllXOffs = { hP/2, -hP/2, -hP, -hP/2, hP/2, hP  };      
	    static final int[] wallYOffs = { -hH, -hH, 0, hH, hH, 0 };
	    static final int[] neigbourRoomX = { 0, -1, -1, 0, 1, 1  };
	    static final int[] neigbourRoomYOdd = { -1, 0, 1, 1, 1, 0  };
	    static final int[] neigbourRoomYEven = { -1, -1, 0, 1, 0, -1  };
	        	    
	    private void buildMaze() {

            final int height = hH*(2*size+1);
            final int width = hP*(3*size-1);
            final boolean isPolar = false;
            setContext(new ShapeContext(isPolar, height, width, 1, 0, 50));
            
	        
	        final int roomsPerRow = 2 * size - 1;

	        Vector<Integer> mapXY2room = new Vector<Integer>();
	        
	        // make rooms
	        for (int x = 0; x < roomsPerRow ; x++) {
	            boolean isOdd = x % 2 == 1;
	            for (int y = 0; y < size; y++) {	            
	                
	                // make room (topology)
	                int r = addRoom();
	                mapXY2room.add(r);
	                
	                // compute centre of the room 	                
	                int xc = (hP *  (2 + x * 3)) / 2;
	                int yc = hH * (2*y+1);
	                if (isOdd) {
	                    yc += hH;
	                }
	                
	                LOGGER.info("addRoom "+r+" y="+y+" x="+x+" yc="+yc+" xc="+xc);
	                
	                String floorId = "r" + Integer.toString(r);
	                final FloorShape floor = new FloorShape(yc, xc, false, floorId);
	                linkRoomToFloor(r, floor);
	                addShape(floor);
	                
	                // make walls
	                for (int w = 0; w < 6; w++) {
	                    int w2 = (w+1) % 6;
	                    
	                    // wall endpoints
	                    int x1 = xc + walllXOffs[w];
	                    int y1 = yc + wallYOffs[w];
                        int x2 = xc + walllXOffs[w2];
                        int y2 = yc + wallYOffs[w2];
                        
                        // the other room
                        int ox = x + neigbourRoomX[w];
                        int oy = y;
                        if (isOdd) {
                            oy += neigbourRoomYOdd[w];
                        } else {
                            oy += neigbourRoomYEven[w];                        
                        }
                        
                        // if the other room does not exist then this is a border wall
                        if (ox < 0 || ox >= roomsPerRow || oy < 0 || oy >= size) {                            
                            addShape(new WallShape(ShapeType.outerWall,  y1, x1, y2, x2));
                        } else if (w < 3) {
                            int r2 = mapXY2room.get(ox*size+oy);
                            int id = addWall(r, r2);
                            WallShape ws = new WallShape(ShapeType.innerWall, y1, x1, y2, x2);
                            LOGGER.info("addWallAndShape room1="+r+" room2="+r2+" y1="+y1+" y2="+y2+" x1="+x1+" x2="+x2);
                            addShape(ws);
                            linkShapeToId(ws, id);
                        }
	                    
	                }
	                
	                
	            }
	        }
	            
	        
	        setStartRoom(0);
	        final int lastRoom = size*roomsPerRow-1;
	        setTargetRoom(lastRoom);
	    }

	    public int getSize() {
	        return size;
	    }
	    
	    private static final Logger LOGGER =  Logger.getLogger("maze.jmaze");
	}
