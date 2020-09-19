# Goal
Create a full sudoku application to keep coding knowledge fresh.

# Progress (Updated every time I push new code)
1. I started off with an application that forced single numbers in each cell.
2. My next step was to add a settings class to track a user's active settings.
3. While pursuing menu options, I made multiple updates to object types and my general approach for tracking all the cells.
4. After succesfully adding a few unimplemented options to the menu, I implemented an empty style for empty cells.
5. Adding duplicate checking proved quite difficult. After a lot of time, the new code I pushed was refactored, contained a logger, and I believe my hanlding of all objects is finalized.
6. Finished my first attempt at creating new boards. It was a massive failure. I believe it is impossible to know you have created a solveable sudoku board without first solving it, let alone creating a unique board (Only 1 way to finish the board).
7. Successfully solved random boards and removed cells based on difficulty. Cells are removed at random and it is not garunteed to have only 1 win state.
8. Redesigned game menu for selecting difficulties based on Minesweeper's menu.
9. Unique feature now implemented.
10. Refactor: comments, TODOs, and documentation.
11. Fixed bug 3: Only 1-9 can be added now. Attempted Things to try #1: First attempt resulted in illegal state modication. May research more later.
Attempted minor performance improvement: don't perform comparisons when not needed - the setText() operation triggers both the removeUpdate and insertUpdate listeners. As such, there is currently no way to distinguish between a user's remove and a program's remove. Update info in TODO.
12. Fixed bug 1: Duplicate checking always updates correctly. Also simplified/reduced code considerably. Easier to read and removed a method.


# TODO
* "Reset"/"Retry" - save board states so a player can retry the same board.
* "Mark Correct Cells" - Add another option for showing when the correct value is placed in a cell. Will be exclusive for unique boards.
* Clock timer - Add a timer to track how long it takes a player to finish.
* Statistics - Track games played, games finished, mistakes, etc. 
* Success Message - Add a message area at the bottom to congratulate the player when a board is solved.
* Random difficulty count - Use a range to randomize how many cells are removed for each difficulty.
* "Special Boards" - Create a file containing patterned or other interesting boards and allow that as a "difficulty".
* "Help" Menu - Menu with options for explaining how sudoku works and what each option in the game does.
* Refactor - Refactor code every few steps to ensure well organized code.
* Menu minimization fix - Prevent the menu from minimizing after any click.
* Minor performance improvement: Don't perform comparisons when not needed (Flag for user vs program removeUpdates)
* Major performance improvement: Change how boards are unsolved to ensure retries are not attempted.
* Bug #2/Major experience improvement: Add new techniques for unsolving a board. This will make unique expert boards possible and truly expert boards.
* Duplicate checking: Update cell styles when duplicate checking setting is changed.
* Things to try #1: Instead of using a runnable to make updates to board while the player is playing, try setting the cell to uneditable and then make the update.
* Things to try #2: Update the unsolving code to remove a random column for each row.


# Known Bugs
1. ~~If cell x creates bad cells and a cell other than x is changed from bad to good, color update can be wrong (Caused by how the check is done).~~
2. A unique expert board is likely to cause a stack overflow error. Not sure if successful creation is possible based on current implementation.
3. ~~If a non-number or 0 is added to a cell, an error message will be displayed to console.~~
