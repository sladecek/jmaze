# jmaze

WORK IN PROGRESS

jmaze is software for generating 2D and 3D mazes. It can be used
directly as a command line application or by a larger project as a
library.

## Build

```
mvn clean package

```

creates executable jar (with all dependencies) in the target directory.

## Usage

The command:

```
java -jar jmaze.jar rectangular 
```

generates rectangular maze with default parameters. Known maze types
are 'rectangular', 'circular', 'hexagonal', 'moebius', 'egg',
'triangular', and 'voronoi'.

All maze properties can be specified on the command line. For example:

```
java -jar jmaze.jar circular printSolution=false pdf=true layerCount=27 
```

generates circular maze with 27 layers not showing the solution and
saves the results to in the .pdf file format.

All available properties for circular  maze type can be printed by
```
java -jar jmaze.jar -list circular 
```


### Universal Properties

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|randomSeed| 0| 

### 2D Properties

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|printSolution| true |
|printAllWalls| false |
|startMarkColor| ff0000 |
|targetMarkColor| 00ff00 |
|solutionMarkColor| 777777 |
|innerWallWidth| 1| 1| 100 |
|outerWallWidth| 2| 1| 100 |
|startMarkWidth| 4| 1| 100 |
|targetMarkWidth| 4| 1| 100|
|solutionMarkWidth| 2| 1| 100|
|outerWallColor| 000000|
|innerWallColor| 000000|
r|debugWallColor| eeeeff|
|pdf| true|
|svg| false|

### 3D Properties

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|margin| 10| 0| 100|
|wallHeight| 30.0| 1.0| 100.0|
|cellSize| 10.0| 1.0| 100.0| 
|wallSize| 2.0| 1.0| 100.0|
|stl| true |
|scad| false|
|js| false|


### Maze Types and Their Properties

#### Rectangular 2D Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|width| 20| 2| 100000 |
|height| 20| 2| 100000 |

![Rectangular 2D Maze](readme_images/rectangular_2d.png?raw=true "Rectangular 2D Maze")

#### Rectangular 3D Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|width| 20| 2| 100000 |
|height| 20| 2| 100000 |

![Rectangular 3D Maze](readme_images/rectangular_3d.png?raw=true "Rectangular 3D Maze")

#### Triangular Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|size| 10| 2| 100000|

![Triangular Maze](readme_images/triangular_2d.png?raw=true "Triangular Maze")

#### Hexagonal Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|size| 6| 2| 1000|


![Hexagonal Maze](readme_images/hexagonal_2d.png?raw=true "Hexagonal Maze")

#### Circular Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|layerCount| 4| 1| 1000|


![Circular Maze](readme_images/circular_2d.png?raw=true "Circular Maze")

#### Irregular Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|size| 10| 2| 100000 |
|width| 20| 2| 100000 |
|height| 20| 2| 100000 |
|roomCount| 100| 2| 1000000 |
|loydCount| 10| 1| 10000 |

![Irregular Maze](readme_images/voronoi_2d.png?raw=true "Irregular Maze")

#### Moebius 3D Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|sizeAlong| 40| 2| 10000|
|sizeAcross| 4| 2| 1000 |

![Moebius 3D Maze](readme_images/moebius_3d.png?raw=true "Moebius 3D Maze")

#### Egg 3D Maze

| Property | Default | Minimum | Maximum |
| ---------- | --------- | --------- | --------- |
|width| 20| 2| 100000 |
|height| 20| 2| 100000 |
A|equatorCells| 8| 2| 64 |
|ellipseMajor| 10.0| 0.1| 50.0|
|ellipseMinor| 10.0| 0.1| 50|
|eggness| 0.5| 0.0| 0.9|


![Egg 3D Maze](readme_images/egg_3d.png?raw=true "Egg 3D Maze")


## Implementation

For each generated maze type, there is a package in the directory
`makers`. For example, rectangular mazes are implemented in the
`makers.rectangular` package and triangular mazes in the
`makers.triangular` package. The spherical and ellipsoid mazes are just
a special case of the egg-shaped maze, they share the package
`makers.spherical`. All other packages in the projects are shared 
between all maze types.

Each maker package contains at least the maker class derived from
`Maze` which implements `IMaze` interface, for example rectangular maze
is generated by `RectangularMaze` class and an auxiliary class derived
from `MazeDescription` which contains list of parameters needed when
the maze is constructed. For instance, the `RectangularMazeDescription`
class defines, that the rectangular maze is planar (2D) and has two
parameters - namely a width and  a height.

The `Maze` class combines two sets of responsibilities: firstly, each
`Maze` class implements the `IMaze` interface which defines functions
for maze creation. Moreover, an object of `Maze` class contains
concrete data structures holding maze data, such as the lists of rooms
and walls, maze shapes (floor a wall shapes) and 3D model. These
structures are provided by `MazeData` which is the base class of
`Maze`.

Certain makers need more than one class to implement all maze features.

### Maze Graph and Maze Generation

The package `maze` contains basic interfaces and definitions. 

Each maze consists of rooms connected by walls. The rooms and walls
are numbered with integers. One room is the start room, another
room is the target room. The rooms of the maze make nodes of an undirected
graph and walls make its edges. This graph structure is described by
interface `IMazeGraph` with concrete implementation in the class
`MazeGraph`. 

Each concrete maze maker builds a maze with all rooms and walls. Such
maze is of course useless - there is no path between start and target
because all rooms are completely surrounded by walls.. Therefore a
random generator (`IMazeGenerator` in package `generators`) must
remove certain walls to make the puzzle solvable with an unique
solution. The generator creates `MazePath` object containing both the
set of opened walls and the solution (which may be shown to the user
if she wants to).

Currently, the project contains only one `IMazeGenerator`
implementation, namely `DepthFirstMazeGenerator` which builds unique
solution by random depth-first walk over the maze space. Other algorithms
are possible, for example A-start walk.


### Maze Shapes

Not only must be the maze generated, it should also be
printed. Therefore each room and wall must have geometric coordinates
assigned in the process of maze making. In case of planar (2D)
mazes, the rooms can be drawn to screen or paper in a straightforward
way. Three-dimensional mazes such as Möbius or spherical mazes are not
so simple. Their planar coordinates make sense only in certain region
of the maze and several maze regions must be cleverly glued together
when printing the maze. The `ILocalCoordinateSystem` interface is
responsible for this.

The classes related to the planar coordinates are defined in the
`shapes` package. They depend on general geometric objects such as
points and directions which are defined in the `geometry` package.

Planar coordinates may be either Cartesian (*x*, *y*, used by most
maze types) or polar (radius and angle, used by circular maze only).
Each maze maker generates a collection (class `Shapes`) of shapes such
as walls and floors. Certain part of shape information, such as canvas
size or margin width, is shared by all shapes in the collection and
stored in the collection `ShapeContext`.

### 3D Model

3d printing classes are organized in the `3dprint` package. 

Generic 3D model, in the `generic3dmodel` sub-directory, can describe
the boundary of any three dimensional solid using points, edges and
faces.

The generic 3D model is further refined by the `maze3dmodel`
package. This package defines special types of faces such as `MPillar`
for corners of maze rooms, `MWall` for maze walls, and `MRoom` for the
inside floor of the room. All these faces are derived from `FloorFace`
class. There are two auxiliary classes `WallEnd` and `RoomCorner`
which function as joining classes for relations between 3D objects.

The 3D model is created from the 2D shape model by the
`ModelFromShapes` class. This class first creates pillars from room
corners using `PillarMakerClass`. Then it connects the pillars to make
walls and finally assigns floor identifiers to room floors.

Each maze is first created on the floor plane. There are three kinds
of faces in the floor - room faces, wall faces and corner (pillar)
faces. Then the floor faces are elevated (extruded) to required height
(altitude).  Floor faces remain on the base altitude. Pillar bases are
extruded into pillars. Open wall remain in the base altitude; closed
walls get extruded. There is the `TelescopicPoint` class which
contains the data for extrusion. The telescopic point initially
represents one point in 2D. When the maze gets extruded, a telescopic
point is either moved to a proper altitude or converted into several
points at different altitudes. The extrusion is implemented in the
`VerticalFaceMaker` class.

Then the planar coordinates are converted into 3-D coordinates with
respect to peculiarities of particular maze type. There is an
interface `IMaze3DMapper` implemented in each maze maker package. The
local coordinate system created by a `IMaze3DMapper` is represented by
the interface `ILocalCoordinateSystem`. There exists special
coordinate systems for Möbius and spherical mazes. Any planar maze can
be transformed into 3D maze using the `TrivialCoordinateSystem` which
only 

When all vertices, edges and faces of maze boundary model are created,
they can be converted to any particular 3D file format. Currently, the
library generates STL files and JavaScript representation suitable for
the Three-JS library.  The converters live in the `print3d.output` package.

For solid modeling (SCAD modeling tools), the boundary model is not
sufficient. It is necessary to create solid model of all blocks
(pillars, walls, floors) created during extrusion using the `MBlock`
objects.

### 2D Printing

Package `2dprint` contains 2D printing code. A collection of shapes is
transformed into pdf or svg document using the Apache Batik library.

### Application

Each maze type requires certain input parameters, such as maze width
or maze height. The package `properties` contains methods and classes
for defining and validating the properties and options.

The package `app` contains class `MazeApp` which serves as main class
when the project is executed as standalone library. The list of all mazes
known by the project is implemented in the `AllMazeTypes` class in the
`maze` package.

## TODOs


