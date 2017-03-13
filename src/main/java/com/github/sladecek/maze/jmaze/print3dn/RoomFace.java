package com.github.sladecek.maze.jmaze.print3dn;

import com.github.sladecek.maze.jmaze.geometry.Axis;
import com.github.sladecek.maze.jmaze.geometry.Direction;

import java.util.Dictionary;

import static com.github.sladecek.maze.jmaze.geometry.Axis.Z;

/**
 * One room face defined by a mesh. In the original coordinates (before mapping), the face is a rectangle. The mapper
 * turns the face into polygonal mesh. A {@code Room} is drawn from several dozens of such faces, Their visibility
 * depends of presence of particular walls.
 */
public class RoomFace {


    private Axis[] axes = new Axis[2];
    private Axis normalAxis;
    private RoomPosition[][] corner = new RoomPosition[2][2];
    private boolean orientation;

    private WallCondition condition;
    private RoomPosition normalPosition;


    public Axis[] getAxes() {
        return axes;
    }


    public Axis getNormalAxis() {
        return normalAxis;
    }

    public void setNormalAxis(Axis normalAxis) {
        this.normalAxis = normalAxis;
        this.axes = new Axis[2];
        switch (normalAxis) {
            case Z:
                this.axes[0] = Axis.X;
                this.axes[1] = Axis.Y;
            case X:
                this.axes[0] = Axis.Y;
                this.axes[1] = Axis.Z;
            case Y:
                this.axes[0] = Axis.X;
                this.axes[1] = Axis.Z;
        }
    }

    public void setNormalPosition(RoomPosition normalPosition) {
        this.normalPosition = normalPosition;
    }

    public RoomPosition[][] getCorner() {
        return corner;
    }


    public void setCorner(RoomPosition ax1Small, RoomPosition ax1Big, RoomPosition ax2Small, RoomPosition ax2Big) {
        this.corner = new RoomPosition[2][2];
        this.corner[0][0] = ax1Small;
        this.corner[0][1] = ax1Big;
        this.corner[1][0] = ax2Small;
        this.corner[1][1] = ax2Big;
    }
    public RoomFace rotate(Rotator r) {
        RoomFace rf = new RoomFace();
        rf.setOrientation(r.rot(isOrientation()));
        rf.setNormalAxis(r.rot(getNormalAxis()));
        rf.setCondition(new WallCondition(getCondition().getType(), r.rot(getCondition().getDirection())));
        for (int i = 0; i < 2; i++) {
            for (int j = 0; j < 2; j++) {
                rf.corner[i][j]= r.rot(corner[i][j]);
            }
        }
        return  rf;
    }


    public boolean isOrientation() {
        return orientation;
    }

    public void setOrientation(boolean orientation) {
        this.orientation = orientation;
    }

    public WallCondition getCondition() {
        return condition;
    }

    public void setCondition(WallCondition condition) {
        this.condition = condition;
    }


}
