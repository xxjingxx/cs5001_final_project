class Board():
    def __init__(self, UI, UNIT_LENGTH, size='full'):
        self.UI = UI
        self.UNIT_LENGTH = UNIT_LENGTH
        if size == 'mini':
            ROW = 2
            COLUMN = 2
        else:
            ROW = 6
            COLUMN = 7
        self.ROW = ROW
        self.COLUMN = COLUMN
        self.disk_list = []
        for i in range(self.ROW):
            self.disk_list.append([])
            for j in range(self.COLUMN):
                self.disk_list[i].append(0)

    def draw_board(self, ROW, COLUMN):
        """Draw game disk_list"""
        BLUE = (0, 0, 255)

        strokeWeight(20)
        stroke(*BLUE)
        for j in range(ROW + 1):
            line(0, (j+1)*self.UNIT_LENGTH, COLUMN*self.UNIT_LENGTH,
                 (j+1)*self.UNIT_LENGTH)
        for i in range(COLUMN + 1):
            line(i*self.UNIT_LENGTH, self.UNIT_LENGTH, i*self.UNIT_LENGTH,
                 (ROW+1)*self.UNIT_LENGTH)

    def find_stop_row(self, disk_list, j):
        """Finds the row that a disk should stop at"""
        for i in range(len(disk_list)-1, -1, -1):
            if disk_list[i][j] == 0:
                return i
        return -1

    def find_column(self, mouseX, mouseY):
        """Finds the column on the game disk_list based on mouse
        location"""
        if mouseY > 0 and mouseY < self.UNIT_LENGTH:
            return mouseX // self.UNIT_LENGTH

    def reset_board(self):
        """Resets disk list to initial value"""
        self.disk_list = []
        for i in range(self.ROW):
            self.disk_list.append([])
            for j in range(self.COLUMN):
                self.disk_list[i].append(0)
