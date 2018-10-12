# colony.py
# Class for a colony of cells in the Game of Life.

from cell import *

class Colony:
    # Create a colony, with a given number of rows and columns.
    def __init__(self, rows, columns):
        # Remember the numbers of rows and columns.
        self.rows = rows
        self.columns = columns

        self.cells = []

        # Append each row.
        for row in range(rows):
            self.cells.append([])

            # Append each column to the row.
            for column in range(columns):
                self.cells[row].append(Cell(row, column))

    # Draw a colony of cells.
    def draw(self):
        # Draw each row of cells.
        for row in range(self.rows):
            # Have each cell in the row draw itself.
            for column in range(self.columns):
                self.cells[row][column].draw()


    # Called when a click occurs.
    def flip_cell(self, x, y):
        # Compute the row and column number corresponding to the mouse position.
        row = y // CELL_SIZE
        column = x // CELL_SIZE

        if 0 <= row < self.rows and 0 <= column < self.columns:
            self.cells[row][column].flip()
            # self.cells[row][column].draw()

    # Compute the next generation of cells.
    def compute_generation(self):
        # Initialize a list of lists of counts of living neighbors.
        #living_neighbors = []
        #for row in range(self.rows):
        #    living_neighbors.append([0] * self.columns)

        # Go through the entire colony, incrementing the living neighbor
        # count of each neighbor of each living cell.
        copy = Colony(self.rows, self.columns)
        for row in range(self.rows):
            for column in range(self.columns):


                # It's living, so increment.
                row_above = (row-1) % self.rows
                row_below = (row+1) % self.rows
                column_left = (column-1) % self.columns
                column_right = (column+1) % self.columns


                living_neighbors = 0
                living_neighbors += int(self.cells[row_above][column_left].is_living())
                living_neighbors += int(self.cells[row_above][column].is_living())
                living_neighbors += int(self.cells[row_above][column_right].is_living())
                living_neighbors += int(self.cells[row][column_left].is_living())
                living_neighbors += int(self.cells[row][column_right].is_living())
                living_neighbors += int(self.cells[row_below][column_left].is_living())
                living_neighbors += int(self.cells[row_below][column].is_living())
                living_neighbors += int(self.cells[row_below][column_right].is_living())



        # Now go back through the entire colony, killing cells with too few
        # or too many living neighbors, and making cells with 3 living neighbors
        # be alive.


                if living_neighbors == 3:
                    copy.cells[row][column].revive()
                elif living_neighbors == 2:
                    copy.cells[row][column].emulate(self.cells[row][column])

        self.cells = copy.cells






