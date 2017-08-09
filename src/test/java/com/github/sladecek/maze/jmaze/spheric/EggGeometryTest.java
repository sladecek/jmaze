package com.github.sladecek.maze.jmaze.spheric;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import org.junit.Test;

import com.github.sladecek.maze.jmaze.geometry.OrientationVector2D;
import com.github.sladecek.maze.jmaze.geometry.SouthNorth;

public class EggGeometryTest {

	static final double delta = 0.0000001;

	@Test
	public void testComputeYNoDeformationLeftSide() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		assertEquals(3, e.computeY(0), delta);
	}

	@Test
	public void testComputeYNoDeformationRightSide() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		assertEquals(0, e.computeY(4), delta);
	}

	@Test
	public void testComputeYNoDeformationMiddle() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		final double xm = 2;
		final double ym = e.computeY(xm);
		assertEquals(1, xm*xm/(4*4)+ym*ym/(3*3) , delta);
	}
	
	@Test
	public void testComputeYWithDeformationLeftSide() {
		EggGeometry e = new EggGeometry(4, 3, 0.2);
		assertEquals(3, e.computeY(0), delta);
	}

	@Test
	public void testComputeYWithDeformationRightSide() {
		EggGeometry e = new EggGeometry(4, 3, 0.2);
		assertEquals(0, e.computeY(4), delta);
	}

	@Test
	public void testComputeYWithDeformationMiddle() {
		EggGeometry e = new EggGeometry(4, 3, 0.2);
		final double xm = 2;
		final double ym = e.computeY(xm);
		assertEquals(1, xm*xm/(4*4)+ym*ym/(3*3)*1/(1-0.2*xm/4) , delta);
	}
	

	@Test
	public void testDivideMeridian() {
		EggGeometry e = new EggGeometry(4, 3, 0.2);
		final double b = 0.1;
		
		for (SouthNorth sn: SouthNorth.values())
		{
		
			ArrayList<Double> v = e.divideMeridian(b, sn);
			if (sn == SouthNorth.south) {
				assertEquals(54,  v.size());
			} else {
				assertEquals(52,  v.size());
			}			
			
			
			// all distances must be in 50% tolerance
			for (int i = 0; i < v.size()-1; i++) {
				final double x0 = v.get(i);
				final double y0 = e.computeY(x0);
				final double x1 = v.get(i+1);
				final double y1 = e.computeY(x1);
				final double dx = x1-x0;
				final double dy = y1-y0;
				final double d = Math.sqrt(dx*dx+dy*dy);
				assertEquals(b, d, 0.5*b);
				
				// all distances must be on the proper hemisphere
				if (sn == SouthNorth.south) {
					assert(x1 < 0);
				} else {
					assert(x1 > 0);
				}				
			}
			
			// first distance must be on equator
			assertEquals(0, v.get(0), delta);
		}
	}

	
	@Test
	public void testComputeNormalVectorLeftSide() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		OrientationVector2D ov = e.computeNormalVector(0);
		assertEquals(0, ov.getX(), delta);
		assertEquals(1, ov.getY(), delta);
	}

	@Test
	public void testComputeNormalVectorRightSide() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		OrientationVector2D ov = e.computeNormalVector(4);
		assertEquals(1, ov.getX(), delta);
		assertEquals(0, ov.getY(), delta);
	}

	@Test
	public void testComputeNormalVectorMiddleSide() {
		EggGeometry e = new EggGeometry(4, 3, 0);
		OrientationVector2D ov = e.computeNormalVector(2);
		assertEquals(1, ov.getX()*ov.getX()+ov.getY()*ov.getY(), delta);
	}

	@Test
	public void testComputeBaseRoomSizeInmm() {
		EggGeometry e = new EggGeometry(4, 3, 0.2);
		assertEquals(2*Math.PI*3/100, e.computeBaseRoomSizeInmm(100) , delta);
	}

}
