from cs1lib import *

WINDOW_WIDTH = 400
WINDOW_HEIGHT = 400
PADDLE_SPEED = 10                                       # rate at which the location of the paddles changes

PADDLE_WIDTH = 20
PADDLE_HEIGHT= 80

XSPEEDBALL = 3                                          #rate at which the x location of the ball changes
YSPEEDBALL = 3                                          #rate at which the y location of the ball changes

right_paddle_initial_x = WINDOW_WIDTH - PADDLE_WIDTH    #x coordinate of the right paddle
right_paddle_initial_y = WINDOW_HEIGHT - PADDLE_HEIGHT  #y coordinate of the right paddle

left_paddle_initial_x = 0                               #x coordinate of the left paddle
left_paddle_initial_y = 0                               #y coordinate of the left paddle

initial_ball_x = WINDOW_HEIGHT // 2                     #initial x coordinate of the ball
initial_ball_y = WINDOW_WIDTH // 2                      #initial y coordinate of the ball
BALL_RADIUS = 10                                        #radius of the pong ball

pressed_a = False                                       # is 'a' key currently pressed?
pressed_z = False                                       # is 'z' key currently pressed?
pressed_k = False                                       # is 'k' key currently pressed?
pressed_m = False                                       # is 'm' key currently pressed?
pressed_space = True                                    # is ' ' key currently pressed?
pressed_q = False                                       # is 'q' key currently pressed?

right_boundary = WINDOW_WIDTH - BALL_RADIUS         # the value furthest to the right where the ball is still visible
left_boundary = BALL_RADIUS                         # the value furthest to the left where the ball is still visible

def draw_rectangle_right(x1, y1, w, h):
    disable_stroke()
    set_fill_color(0, 0, 0)
    draw_rectangle(x1, y1, w, h)

def draw_rectangle_left(x2, y2, w, h):
    disable_stroke()
    set_fill_color(0, 0, 0)
    draw_rectangle(x2, y2, w, h)

def draw_pong_ball(x3, y3, r):
    disable_stroke()
    set_fill_color(1, 1, 1)
    draw_circle(x3, y3, r)

def hit_paddle_right(ball_right_outer, ball_left_outer, y_center_ball, right_paddle_outer, top_paddle_right, \
                     bottom_paddle_right):

    if (bottom_paddle_right>= y_center_ball >= top_paddle_right) and \
            (ball_left_outer <= right_paddle_outer <= ball_right_outer):
        return True

def hit_paddle_left(ball_right_outer, ball_left_outer, y_center_ball, left_paddle_outer, top_paddle_left, \
                    bottom_paddle_left):
    if (bottom_paddle_left >= y_center_ball >= top_paddle_left) and \
            (ball_right_outer >= left_paddle_outer >= ball_left_outer):
        return True

def hit_top(x_ball_center, ball_top_outer, left_boundary, right_boundary ):
    if (right_boundary >= x_ball_center >= left_boundary) and (ball_top_outer <= 0):
        return True

def hit_bottom(x_ball_center, ball_bottom_outer, left_boundary, right_boundary):
    if (right_boundary >= x_ball_center >= left_boundary) and (ball_bottom_outer >= WINDOW_HEIGHT):
        return True

def key_down(key):
    global pressed_a, pressed_z, pressed_k, pressed_m, pressed_space, pressed_q

    if key == "a":
        pressed_a = True

    elif key == "z":
        pressed_z = True

    elif key == "k":
        pressed_k = True

    elif key == "m":
        pressed_m = True

    elif key == " ":
        pressed_space = True

    elif key == "q":
        pressed_q = True

# When a key is released, if it is one of the four keys of
# interest, record that it was released.

def key_up(key):
    global pressed_a, pressed_z, pressed_k, pressed_m, pressed_space, pressed_q

    if key == "a":
        pressed_a = False
    elif key == "z":
        pressed_z = False
    elif key == "k":
        pressed_k = False
    elif key == "m":
        pressed_m = False
    elif key == " ":
        pressed_space = False
    elif key == "q":
        pressed_q = False


def graphics():
    global right_paddle_initial_x, right_paddle_initial_y, PADDLE_WIDTH, PADDLE_HEIGHT, left_paddle_initial_x, \
        left_paddle_initial_y, initial_ball_x, initial_ball_y, BALL_RADIUS, XSPEEDBALL, YSPEEDBALL, left_boundary, \
        right_boundary

    right_outer_ball = initial_ball_x + BALL_RADIUS
    left_outer_ball = initial_ball_x - BALL_RADIUS

    bottom_outer_ball = initial_ball_y + BALL_RADIUS
    top_outer_ball = initial_ball_y - BALL_RADIUS

    top_paddle_right = right_paddle_initial_y
    top_paddle_left = left_paddle_initial_y

    bottom_paddle_right = right_paddle_initial_y + PADDLE_HEIGHT
    bottom_paddle_left = left_paddle_initial_y + PADDLE_HEIGHT

    left_paddle_outer = left_paddle_initial_x + PADDLE_WIDTH
    right_paddle_outer = right_paddle_initial_x

    # Draw a yellow background.
    set_clear_color(1, 0.9167, 0)
    clear()

    draw_rectangle_right(right_paddle_initial_x, right_paddle_initial_y, PADDLE_WIDTH, PADDLE_HEIGHT)

    draw_rectangle_left(left_paddle_initial_x, left_paddle_initial_y, PADDLE_WIDTH, PADDLE_HEIGHT)

    if pressed_a and left_paddle_initial_y > 0:
        left_paddle_initial_y -= PADDLE_SPEED                               #Moves the left paddle up

    if pressed_z and left_paddle_initial_y < WINDOW_HEIGHT - PADDLE_HEIGHT:
        left_paddle_initial_y += PADDLE_SPEED                               #Moves left paddle down

    if pressed_k and  right_paddle_initial_y > 0:
        right_paddle_initial_y -= PADDLE_SPEED                              #Moves right paddle up

    if pressed_m and right_paddle_initial_y < WINDOW_HEIGHT - PADDLE_HEIGHT:
        right_paddle_initial_y += PADDLE_SPEED                              #Moves right paddle down

    if pressed_space:                                                       #resets position and restarts the game of
                                                                            #pong when pressed space
        enable_stroke()
        set_stroke_color(0, 0, 0)
        draw_text("Press SPACE to start game", WINDOW_WIDTH//2 - 90, WINDOW_HEIGHT//2 - 30)

        initial_ball_x = WINDOW_HEIGHT // 2                                 # initial x coordinate of the ball
        initial_ball_y = WINDOW_WIDTH // 2                                  # initial y coordinate of the ball

        draw_pong_ball(initial_ball_x, initial_ball_y, BALL_RADIUS)
        XSPEEDBALL = 3
        YSPEEDBALL = 3

        initial_ball_x += XSPEEDBALL
        initial_ball_y += YSPEEDBALL

    if not pressed_space:

        draw_pong_ball(initial_ball_x, initial_ball_y, BALL_RADIUS)

        initial_ball_x +=XSPEEDBALL
        initial_ball_y +=YSPEEDBALL

        if hit_paddle_left(right_outer_ball, left_outer_ball, initial_ball_y, left_paddle_outer, top_paddle_left, \
                           bottom_paddle_left):
            XSPEEDBALL = (-1) * XSPEEDBALL
            initial_ball_x = initial_ball_x + BALL_RADIUS

        if hit_paddle_right(right_outer_ball, left_outer_ball, initial_ball_y, right_paddle_outer, top_paddle_right, \
                            bottom_paddle_right):
            XSPEEDBALL = (-1) * XSPEEDBALL
            initial_ball_x = initial_ball_x - BALL_RADIUS

        if hit_bottom(initial_ball_x, bottom_outer_ball, left_boundary, right_boundary):
            YSPEEDBALL = (-1) * YSPEEDBALL
            initial_ball_y = initial_ball_y - BALL_RADIUS

        if hit_top(initial_ball_x, top_outer_ball, left_boundary, right_boundary):
            YSPEEDBALL = (-1) * YSPEEDBALL
            initial_ball_y = initial_ball_y + BALL_RADIUS

        if (right_outer_ball >= right_paddle_outer and right_outer_ball >= WINDOW_HEIGHT) or \
                initial_ball_y >= WINDOW_HEIGHT or left_outer_ball <= 0 or initial_ball_y <= 0:
            XSPEEDBALL = 0
            YSPEEDBALL = 0

            enable_stroke()
            set_stroke_color(0, 0, 0)
            draw_text("Game Over! Press SPACE to play again!", WINDOW_WIDTH//2 - 140, WINDOW_HEIGHT//2 - 30)

    if pressed_q:
        cs1_quit()

# Start the animation.
start_graphics(graphics, title="Pong", width = WINDOW_WIDTH, height = WINDOW_HEIGHT,
               key_press = key_down, key_release = key_up, framerate= 50)

