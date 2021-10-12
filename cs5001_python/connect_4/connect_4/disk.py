class Disk():
    def __init__(self, UI, UNIT_LENGTH, color='red', column=0, y=50):
        self.UI = UI
        self.UNIT_LENGTH = UNIT_LENGTH
        self.DIAMETER = self.UNIT_LENGTH * 9/10
        self.YELLOW = (1.0, 1.0, 0)
        self.RED = (1.0, 0, 0)
        self.y_vel = 0
        self.y = y
        self.column = column
        self.color = color
        self.napTime = 20000000

    def display(self, x, y, my_turn):
        """Display the disk based on turns"""
        if my_turn:
            self.draw_disk('red', x, y)
        elif not my_turn:
            self.draw_disk('yellow', x, y)

    def drop_disk(self, stop_row, start_column, my_turn):
        """Drops a disk"""
        VEL = 10
        ACCELERATION_RATE = 1.1

        self.y_vel = VEL
        self.y = (self.y + self.y_vel)*ACCELERATION_RATE
        drop_x = self.UNIT_LENGTH/2 + start_column*self.UNIT_LENGTH
        stop_y = self.UNIT_LENGTH/2 + stop_row*self.UNIT_LENGTH +\
            self.UNIT_LENGTH
        if not my_turn:
            while self.napTime > 0:
                self.napTime -= 1
            if self.y < stop_y:
                self.display(drop_x, self.y, my_turn)
        else:
            if self.y < stop_y:
                self.display(drop_x, self.y, my_turn)

    def draw_disk(self, color, x, y):
        """Draws a disk"""
        noStroke()
        if color == 'red':
            fill(*self.RED)
        elif color == 'yellow':
            fill(*self.YELLOW)
        ellipse(x, y, self.DIAMETER, self.DIAMETER)
