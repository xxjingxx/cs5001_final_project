################################################################
# Jing Xie
# GitHub repo folder:
# https://github.ccs.neu.edu/CS-5001-SEA-Spring2021/student-JingXie/hw12
################################################################
"""
Project: Connect Four (Continued)

A fully playable Connect Four game implemented with Processing
Prthon mode
"""

from game_controller import GameController
from board import Board
from disk import Disk

UI = {'w': 700, 'h': 700}
UNIT_LENGTH = 100

game_controller = GameController(UI, UNIT_LENGTH)


def setup():
    """Screen setups"""
    size(UI['w'], UI['h'])
    colorMode(RGB, 1)


def draw():
    """Initiates background and updates frame"""
    GREY = (0.75, 0.75, 0.75)

    background(*GREY)
    game_controller.update()


def mouseReleased():
    game_controller.handle_mouseReleased()


def mouseClicked():
    game_controller.handle_mouseClicked()
