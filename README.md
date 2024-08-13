## PathFinder: Maze Solving Algorithm in Java

<img width="380" height = "380" alt="maze1" src="https://github.com/user-attachments/assets/4e2e4bbc-2f25-40b0-9f19-6130cb69bf47">
<img width="380" height = "380" alt="maze1" src="https://github.com/user-attachments/assets/6efec8e2-df5d-4eb7-b980-6aa9cb02205a">

### Overview

This project implements a Java program to solve mazes using graph theory. The maze is represented as an undirected graph, where nodes correspond to rooms and edges represent corridors or doors that may require a specific number of coins to open. The goal is to find a path from the entrance to the exit, considering the coin constraints.

### Input File Format
The maze is defined in a text file with the following format:

1. S: Scale factor for display.
2. A: Width of the maze.
3. L: Length of the maze.
4. k: Number of coins available.
5. Maze Grid: Each line represents a row in the maze grid. Symbols include:
* 's': Entrance
* 'x': Exit
* 'o': Room
* 'c': Corridor
* 'w': Wall
* '0'-'9': Door requiring a specific number of coins
