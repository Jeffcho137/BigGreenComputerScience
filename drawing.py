# Program to draw green eggs and ham.
# CS 1 example of getting used to using cs1lib.
# Author Jeffrey Cho, September 15, 2018.

from cs1lib import *

#function to set the fill color to white
def set_fill_white():
    set_fill_color(1, 1, 1)

#function to set the fill color to green
def set_fill_green():
    set_fill_color(0, 1, 0)

#function that sets the stroke color to blue.
def set_stroke_blue():
    set_stroke_color(0, 0, 1)
    set_stroke_width(2)

#function that sets the stroke color to black.
def set_stroke_black():
    set_stroke_color(0, 0, 0)
    set_stroke_width(2)

#function that makes the background red.
def make_background_red():
    clear()
    set_clear_color(1, 0, 0)

#function that makes the entire drawing of the green eggs and ham.
def draw_green_eggs_and_ham():

    #making background red
    make_background_red()

    #drawing the triangular place
    enable_stroke()
    set_stroke_black()
    set_fill_white()
    draw_triangle(50, 250, 330, 250, 265, 50)

    #drawing outer part of both eggs
    draw_ellipse(125, 225, 30, 20)
    draw_ellipse(200, 225, 30, 20)

    #drawing both yolks
    disable_stroke()
    set_fill_green()
    draw_circle(125, 225, 11)
    draw_circle(200, 225, 11)

    #drawing ham without bone
    set_fill_green()
    draw_ellipse(230, 150, 50, 30)

    #drawing white bone in the center of ham
    set_fill_white()
    draw_circle(230, 150, 5)

    #drawing handle of the fork
    enable_stroke()
    set_stroke_blue()
    draw_line(205, 60, 205, 135)

    #drawing the holder of the tines
    draw_line(199, 135, 211, 135)

    #drawing tines
    draw_line(199, 135, 199, 150)
    draw_line(203, 135, 203, 150)
    draw_line(207, 135, 207, 150)
    draw_line(211, 135, 211, 150)

    #signing name
    set_stroke_black()
    draw_text("Jeffrey Cho", 15, 385)

start_graphics(draw_green_eggs_and_ham)
