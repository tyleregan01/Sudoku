# Goal
Create a full sudoku application to keep coding knowledge fresh. I also hope that when I finish this program, I can turn it into an executable and let anyone/everyone have it.

# Progress (Updated every time I push new code)
1. I started off with an application that forced single numbers in each cell.
2. My next step was to add a settings class to track a user's active settings.
3. While pursuing menu options, I made multiple updates to object types and my general approach for tracking all the cells.
4. After successfully adding a few unimplemented options to the menu, I implemented an empty style for empty cells.
5. Adding duplicate checking proved quite difficult. After a lot of time, the new code I pushed was refactored, contained a logger, and I believe my handing of all objects is finalized.
6. Finished my first attempt at creating new boards. It was a massive failure. I believe it is impossible to know you have created a solvable sudoku board without first solving it, let alone creating a unique board (Only 1 way to finish the board).
7. Successfully solved random boards and removed cells based on difficulty. Cells are removed at random, and it is not guaranteed to have only 1 win state.
8. Redesigned game menu for selecting difficulties based on Minesweeper's menu.
9. Unique feature now implemented.
10. Refactor: comments, TODOs, and documentation.
11. Fixed bug 3: Only 1-9 can be added now. Attempted Things to try #1: First attempt resulted in illegal state modification. May research more later.
Attempted minor performance improvement: don't perform comparisons when not needed - the setText() operation triggers both the removeUpdate and insertUpdate listeners. As such, there is currently no way to distinguish between a user's remove and a program's remove. Update info in TODO.
12. Fixed bug 1: Duplicate checking always updates correctly. Also simplified/reduced code considerably. Easier to read and removed a method.
13: Issue 18: Changed board state to track available numbers for every cell rather than every group.

# Everything else
The things I want to do as well as any bugs are now tracked in issues.

# 2025/2026 Plans
I gave up on this for a few years due to the next step I wanted to take. In order to greatly improve the program's ability to create harder to solve boards, I need to create new methods of analyzing the board. I have considered this numerous times since giving up and still haven't decided how I will address this more complex analysis. Eventually, I will come back and do it.
