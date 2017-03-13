package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.geometry.Axis;
import com.github.sladecek.maze.jmaze.geometry.Direction;

import java.util.ArrayList;

/**
 * Builds all faces of a cube.
 */
public class FacesOfCube {
    private ArrayList<RoomFace> faces = new ArrayList<>();

    public FacesOfCube() {

        // Generate one corner and rotate it 4 times.
        for (int quadrants = 0; quadrants < 4; quadrants++) {
            generateCorner(quadrants);
        }

        // generate central floor face
        RoomFace cfc = new RoomFace();
        cfc.setNormalAxis(Axis.Z);
        cfc.setOrientation(false);
        cfc.setCorner(RoomPosition.inSmall, RoomPosition.inBig, RoomPosition.inSmall, RoomPosition.inBig);
        cfc.setCondition(new WallCondition(WallConditionType.wall, Direction.FLOOR));
        faces.add(cfc);
    }

    private void generateCorner(int rotation) {
        Rotator r = new Rotator(rotation);
        makeHorizontalFaces(r, RoomPosition.outBig, false, WallConditionType.wall,  WallConditionType.wallOrPreceding);
        makeHorizontalFaces(r, RoomPosition.inSmall, true, WallConditionType.notWall,  WallConditionType.notWallAndNotPreceding);

        makeFrontFaces(r, RoomPosition.outSmall, true, WallConditionType.wallOnlyOuter,  WallConditionType.wallOnlyOuter,
                                                 WallConditionType.wallOnlyOuter,  WallConditionType.wallOnlyOuter);
        makeFrontFaces(r, RoomPosition.inSmall, false, WallConditionType.wall, WallConditionType.notPreceding,
                WallConditionType.notPrecedingAndNotFloor, WallConditionType.notFloor);

        makeSideFaces(r, RoomPosition.outSmall, true, WallConditionType.precedingOuter,  WallConditionType.precedingOuter);
        makeSideFaces(r, RoomPosition.inSmall, false, WallConditionType.notWall,  WallConditionType.notWallAndNotFloor);

    }


    private void makeHorizontalFaces(Rotator r, RoomPosition normalPos, boolean orientation, WallConditionType wcFront, WallConditionType wcCorner) {
        RoomFace front = new RoomFace();
        front.setNormalAxis(Axis.Z);
        front.setNormalPosition(normalPos);
        front.setOrientation(orientation);
        front.setCorner(RoomPosition.outSmall, RoomPosition.inSmall, RoomPosition.inSmall, RoomPosition.inBig);
        front.setCondition(new WallCondition(wcFront, Direction.NORTH));
        faces.add(front.rotate(r));

        RoomFace corner = new RoomFace();
        corner.setNormalAxis(Axis.Z);
        corner.setNormalPosition(normalPos);
        corner.setOrientation(orientation);
        corner.setCorner(RoomPosition.outSmall, RoomPosition.inSmall, RoomPosition.outSmall, RoomPosition.inSmall);
        corner.setCondition(new WallCondition(wcCorner, Direction.NORTH));
        faces.add(corner.rotate(r));
    }


    private void makeFrontFaces(Rotator r, RoomPosition normalPos, boolean orientation,
                                WallConditionType cInUp, WallConditionType cCornerUp,
                                WallConditionType cCornerDown, WallConditionType cInDown) {

  // TODO corners
        RoomFace inUp = new RoomFace();
        inUp.setNormalAxis(Axis.X);
        inUp.setNormalPosition(normalPos);
        inUp.setOrientation(orientation);
        inUp.setCorner(RoomPosition.outSmall, RoomPosition.inSmall, RoomPosition.inSmall, RoomPosition.inBig);
        inUp.setCondition(new WallCondition(cInUp, Direction.NORTH));
        faces.add(inUp.rotate(r));

        RoomFace cornerUp = new RoomFace();
        cornerUp.setNormalAxis(Axis.X);
        inUp.setNormalPosition(normalPos);
        cornerUp.setOrientation(orientation);
        cornerUp.setCorner(RoomPosition.outSmall, RoomPosition.inSmall, RoomPosition.outSmall, RoomPosition.inSmall);
        cornerUp.setCondition(new WallCondition(cCornerUp, Direction.NORTH));
        faces.add(cornerUp.rotate(r));

        RoomFace inDown = new RoomFace();
        inDown.setNormalAxis(Axis.X);
        inDown.setNormalPosition(normalPos);
        inDown.setOrientation(orientation);
        inDown.setCorner(RoomPosition.inSmall, RoomPosition.inSmall, RoomPosition.inSmall, RoomPosition.inBig);
        inDown.setCondition(new WallCondition(cInDown, Direction.NORTH));
        faces.add(inDown.rotate(r));

        RoomFace cornerDown = new RoomFace();
        cornerDown.setNormalAxis(Axis.X);
        inDown.setNormalPosition(normalPos);
        cornerDown.setOrientation(orientation);
        cornerDown.setCorner(RoomPosition.outSmall, RoomPosition.inSmall, RoomPosition.outSmall, RoomPosition.inSmall);
        cornerDown.setCondition(new WallCondition(cCornerDown, Direction.NORTH));
        faces.add(cornerDown.rotate(r));
    }


    private void makeSideFaces(Rotator r, RoomPosition inSmall, boolean orientation,
                               WallConditionType cUp, WallConditionType cDown) {
    }

}
