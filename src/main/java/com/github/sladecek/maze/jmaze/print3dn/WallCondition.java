package com.github.sladecek.maze.jmaze.print3dn;

/*import com.github.sladecek.maze.jmaze.geometry.Direction;
import com.github.sladecek.maze.jmaze.shapes.FloorShape;
import com.github.sladecek.maze.jmaze.shapes.WallType;

/**
 * Conditions for face presence.
 * /
public class WallCondition {

    public WallCondition(WallConditionType type, Direction direction) {
        this.type = type;
        this.direction = direction;
    }

    public Direction getDirection() {

        return direction;
    }

    public WallConditionType getType() {

        return type;
    }

    private WallConditionType type;
    private Direction direction;

    public boolean test(FloorShape room) {
        boolean w = room.getWallType(direction) != WallType.noWall;
        boolean ow = room.getWallType(direction) == WallType.outerWall;
        boolean f = room.getWallType(Direction.FLOOR) != WallType.noWall;
        Direction preceding = new Rotator(-1).rot(direction);
        boolean p = room.getWallType(preceding) != WallType.noWall;
        boolean op = room.getWallType(preceding) == WallType.outerWall;
        switch (type) {
            case wall: return w;
            case wallOnlyOuter: return ow;
            case wallOrPreceding: return w || p;
            case notWall: return !w;
            case notWallAndNotPreceding: return !w && !p;
            case notPreceding: return !p;
            case notFloor: return f;
            case notPrecedingAndNotFloor: return !p && !f;
            case notWallAndNotFloor: return !w & !f;
            case precedingOuter: return op;
            default: return false;
        }
    }
}
*/