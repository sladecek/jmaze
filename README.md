# jmaze

WORK IN PROGRESS

jmaze is software for generating 2D and 3D mazes. It can be used
directly as a command line application or by a larger project as a
library.

## Build

```
mvn clean package

```

creates executable jar (with all dependencies) in the targer directory.

## Usage

The command:

```
java -jar jmaze.jar rectangular 
```

generates rectangular maze with default parameters. All maze types are
'rectangular', 'circular', 'hexagonal', 'moebius', 'egg', 'triangular', 'voronoi'.

All maze properties can be specified on the command line. For example:

```
java -jar jmaze.jar circular printSolution=false pdf=true layerCount=27 
```

generates circular maze with 27 layers not showing the solution and saves the results to in the .pdf
file format.


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
|debugWallColor| eeeeff|
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
|equatorCells| 8| 2| 64 |
|ellipseMajor| 10.0| 0.1| 50.0|
|ellipseMinor| 10.0| 0.1| 50|
|eggness| 0.5| 0.0| 0.9|


![Egg 3D Maze](readme_images/egg_3d.png?raw=true "Egg 3D Maze")


## Implementation

### Maze Graph and Maze Generation
### Maze Shapes
### 3D Model
### Printing

## TODOs


