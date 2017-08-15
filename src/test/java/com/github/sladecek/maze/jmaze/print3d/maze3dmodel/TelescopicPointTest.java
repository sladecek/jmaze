package com.github.sladecek.maze.jmaze.print3d.maze3dmodel;

import com.github.sladecek.maze.jmaze.geometry.Point2DDbl;
import com.github.sladecek.maze.jmaze.geometry.Point2DInt;
import com.github.sladecek.maze.jmaze.geometry.Point3D;
import com.github.sladecek.maze.jmaze.print3d.IMaze3DMapper;

/**
 *
 * TODO replace with a real test
 * Test class for TelescopicPoint.
 */
class TelescopicPointTest {

    private IMaze3DMapper fakeMapper = new IMaze3DMapper() {
        @Override
        public Point3D map(Point2DDbl image, Altitude altitude) {
            return new Point3D(image.getX() * 2, image.getY() * 4, altitude.getValue() * 7);
        }

        @Override
        public double inverselyMapLengthAt(Point2DDbl center, double v) {
            return 1;
        }


        @Override
        public ILocalCoordinateSystem createLocalCoordinateSystem(Point2DInt center) {
            return new TrivialCoordinateSystem();
        }
    };


}