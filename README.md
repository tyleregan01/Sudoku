# Goal
A sudoku application and sudoku solver to showcase code I have written.

# Progress
1. I started off with an application that forced single numbers in each cell.
2. My next step was to add a settings class to track a user's active settings.
3. While pursuing menu options, I made multiple updates to object types and my general approach for tracking all the cells.
4. After succesfully adding a few unimplemented options to the menu, I implemented an empty style for empty cells.
5. Adding duplicate checking proved quite difficult. After a lot of time, the new code I pushed was refactored, contained a logger, and I believe my hanlding of all objects is finalized.
6. Finished my first attempt at creating new boards. It was a massive failure. I believe it is impossible to know you have created a solveable sudoku board without first solving it, let alone creating a unique board (Only 1 way to finish the board).
7. Successfully solved random boards and removed cells based on difficulty. Cells are removed at random and it is not garunteed to have only 1 win state.
