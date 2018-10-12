# life.py
# Plays the Game of Life.
# A colony of cells undergoes generations.
# Each cell is either alive (blue) or dead (yellow).
# In each generation, count the number of living neighbors
# of each cell:
#    If 0 or 1, the cell dies of exposure.
#    If 4 or more, the cell dies of overcrowding.
#    If 3, the cell is revived.
#    If 2, the cell does not change.

from cs1lib import *
from colony import *
from cell import CELL_SIZE

WINDOW_WIDTH = 400
WINDOW_HEIGHT = 400

GAME_OFF_FRAME_RATE = 40        # frame rate while flipping cells
DEFAULT_GAME_ON_FRAME_RATE = 1  # default frame rate while computing generations
current_frame_rate = GAME_OFF_FRAME_RATE    # frame right right now
game_on = False                 # have we started the game?
hive = Colony(WINDOW_HEIGHT // CELL_SIZE,
              WINDOW_WIDTH // CELL_SIZE)

# Callback for mouse clicks.  Flips the cell
# at the location of the click, if there's a
# cell there.
def on_click(mx, my):
    hive.flip_cell(mx, my)
    
# Callback for key releases.
def key_up(key):
    global game_on, current_frame_rate
    # If space bar is pressed, start the animation.
    # If f ("faster") key is pressed, halve the time between generations.
    # If s ("slower") key is pressed, double the time between generations.
    if key == ' ':  # switch from game off to game on
        game_on = True
        current_frame_rate = DEFAULT_GAME_ON_FRAME_RATE
    elif key == 'f':
        current_frame_rate *= 2
    elif key == 's':
        current_frame_rate /= 2
    set_framerate(current_frame_rate)

# Main function for running the game.
def main():
    clear()
    hive.draw()

    if game_on:
        hive.compute_generation()

start_graphics(main, title = "Life", width = WINDOW_WIDTH, height = WINDOW_HEIGHT,
               mouse_release = on_click, key_release = key_up, framerate = GAME_OFF_FRAME_RATE)