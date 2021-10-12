from board import Board
from disk import Disk
from random import Random


class GameController():
    """
    Maintains game status and manages interactions of game
    elements.
    """
    def __init__(self, UI, UNIT_LENGTH):
        self.UNIT_LENGTH = UNIT_LENGTH
        self.UI = UI
        self.board = Board(UI, UNIT_LENGTH)
        self.my_turn = True
        self.disk_buffer = []  # Holds the disk that is ready to drop
        self.red_wins = False
        self.yellow_wins = False
        self.is_tie = False
        self.scores = {}  # Stores winner names and scores
        self.show_scores = False
        self.SECTION = 4

    def update(self):
        """Updates the frame"""
        self.check_win(self.board)

        self.do_dropping()

        if not self.red_wins or not self.yellow_wins or not self.is_tie:
            if self.my_turn:
                print("Red's turn.")
                self.outlined_text("Red's turn.", self.UI['w']/2,
                                   self.UNIT_LENGTH/2)
            else:
                self.outlined_text("Yellow's turn.", self.UI['w']/2,
                                   self.UNIT_LENGTH/2)
                print("Yellow's turn.")

        if mousePressed:
            self.handle_mousePressed()
        self.yellow_move()

        # Display the disks that are already placed on game
        # board.
        self.display_disk()

        # Display board
        self.board.draw_board(self.board.ROW, self.board.COLUMN)

        # handle win
        self.handle_win()

        # display scores
        if self.show_scores:
            self.display_scores()

    def handle_mousePressed(self):
        """Handle mousePressed"""
        if self.red_wins or self.yellow_wins or self.is_tie:
            return
        elif (mouseY > 0 and mouseY < self.UNIT_LENGTH and mouseX > 0
              and mouseX < self.UNIT_LENGTH*self.board.COLUMN):
            d = Disk(self.UI, self.UNIT_LENGTH)
            d.display(mouseX, mouseY, self.my_turn)

    def handle_mouseReleased(self):
        """Handle mouseReleased"""
        if self.red_wins or self.yellow_wins or self.is_tie:
            return
        elif (mouseX > 0 and mouseX < self.UNIT_LENGTH*self.board.COLUMN
              and mouseY > 0 and mouseY < self.UNIT_LENGTH):
            j = self.board.find_column(mouseX, mouseY)
            if self.my_turn:
                self.disk_buffer.append(Disk(self.UI,
                                             self.UNIT_LENGTH,
                                             'red', j))

    def handle_mouseClicked(self):
        """Handles mouse clicking"""
        if not self.show_scores:
            if self.red_wins:
                self.update_scores(1)
                self.my_turn = True
                self.red_wins = False
                self.yellow_wins = False
                self.is_tie = False
                self.disk_buffer = []
                self.board.reset_board()
            elif self.yellow_wins or self.is_tie:
                self.update_scores(0)
                self.my_turn = True
                self.red_wins = False
                self.yellow_wins = False
                self.is_tie = False
                self.disk_buffer = []
                self.board.reset_board()
        else:
            self.show_scores = False

    def yellow_move(self):
        """Yellow's turn"""
        if self.red_wins or self.yellow_wins or self.is_tie:
            return
        elif not self.my_turn:

            best_column = self.pick_next_move(self.board)
            self.disk_buffer.append(Disk(self.UI,
                                         self.UNIT_LENGTH,
                                         'yellow', best_column))

    def do_dropping(self):
        """Display a dropping disk if there is one"""
        if self.red_wins or self.yellow_wins or self.is_tie:
            return
        if len(self.disk_buffer) != 0:
            start_column = self.disk_buffer[0].column
            stop_row = self.board.find_stop_row(self.board.disk_list,
                                                start_column)
            if stop_row == -1:
                self.disk_buffer = []
                return
            self.disk_buffer[0].drop_disk(stop_row, start_column, self.my_turn)
            if self.disk_buffer[0].y >= (self.UNIT_LENGTH/2 +
                                         stop_row*self.UNIT_LENGTH +
                                         self.UNIT_LENGTH):
                self.disk_buffer = []
                if self.my_turn:
                    self.board.disk_list[stop_row][start_column] = 1
                    self.my_turn = False
                elif not self.my_turn:
                    self.board.disk_list[stop_row][start_column] = 2
                    self.my_turn = True

    def display_disk(self):
        """Displays a disk"""
        for i in range(self.board.ROW):
            for j in range(self.board.COLUMN):
                x = self.UNIT_LENGTH/2 + j*self.UNIT_LENGTH
                y = self.UNIT_LENGTH/2 + i*self.UNIT_LENGTH + self.UNIT_LENGTH
                if self.board.disk_list[i][j] == 1:
                    d = Disk(self.UI, self.UNIT_LENGTH)
                    d.draw_disk('red', x, y)
                elif self.board.disk_list[i][j] == 2:
                    d = Disk(self.UI, self.UNIT_LENGTH, 'yellow')
                    d.draw_disk('yellow', x, y)

    def announce_winner(self, color):
        """Displays game result"""
        LARGE_TEXT = 50
        SMALL_TEXT = 30
        TEXT_X = self.UI['w']/2
        TEXT_Y = self.UI['h']/2

        textAlign(CENTER)
        if color == 'red':
            t1 = "Congratulations! Red wins!"
            self.outlined_text(t1, TEXT_X, TEXT_Y, LARGE_TEXT)
            t2 = "Click anywhere."
            self.outlined_text(t2, TEXT_X, TEXT_Y+LARGE_TEXT)
        else:
            if color == 'yellow':
                t1 = "Yellow wins!"
                self.outlined_text(t1, TEXT_X, TEXT_Y, LARGE_TEXT)
                t2 = "Click anywhere."
                self.outlined_text(t2, TEXT_X, TEXT_Y+LARGE_TEXT)
            elif color == 'tie':
                t1 = "It's a tie!"
                self.outlined_text(t1, TEXT_X, TEXT_Y, LARGE_TEXT)
                t2 = "Click anywhere."
                self.outlined_text(t2, TEXT_X, TEXT_Y+LARGE_TEXT)

    def winning_rule(self, disk_list, value=None):
        """Defines the winning rule"""
        if value is None:
            # check tie
            for i in range(len(disk_list)):
                for j in range(len(disk_list[0])):
                    if disk_list[i][j] == 0:
                        return  # return None if board still has empty spot
            return -1  # return -1 if is_tie
        else:
            # check horizontal win
            for i in range(len(disk_list)):
                for j in range(len(disk_list[0])-self.SECTION+1):
                    if (disk_list[i][j] == value and
                        disk_list[i][j+1] == value and
                        disk_list[i][j+2] == value and
                            disk_list[i][j+3] == value):
                        return value

            # check vertical win
            for i in range(len(disk_list)-self.SECTION+1):
                for j in range(len(disk_list[0])):
                    if (disk_list[i][j] == value and
                        disk_list[i+1][j] == value and
                        disk_list[i+2][j] == value and
                            disk_list[i+3][j] == value):
                        return value

            # check forward diagonal win
            for i in range(len(disk_list)-self.SECTION+1):
                for j in range(len(disk_list[0])-self.SECTION+1):
                    if (disk_list[i][j] == value and
                        disk_list[i+1][j+1] == value and
                        disk_list[i+2][j+2] == value and
                            disk_list[i+3][j+3] == value):
                        return value

            # check backward diagonal win
            for i in range(len(disk_list)-self.SECTION+1, len(disk_list)):
                for j in range(len(disk_list[0])-self.SECTION+1):
                    if (disk_list[i][j] == value and
                        disk_list[i-1][j+1] == value and
                        disk_list[i-2][j+2] == value and
                            disk_list[i-3][j+3] == value):
                        return value

    def check_win(self, board):
        """Checks if there is a win"""
        if self.winning_rule(board.disk_list, 1) == 1:
            self.red_wins = True
        elif self.winning_rule(board.disk_list, 2) == 2:
            self.yellow_wins = True
        elif self.winning_rule(board.disk_list) == -1:
            self.is_tie = True

    def handle_win(self):
        """Handles win based on game result"""
        if self.red_wins:
            self.announce_winner('red')
        elif self.yellow_wins:
            self.announce_winner('yellow')
        elif self.is_tie:
            self.announce_winner('tie')

    def update_scores(self, value):
        """Updates score"""
        name = self.input('enter your name')

        # handle invalid name
        if name is None or name == '' or name == ' ':
            self.show_scores = True
        elif name in self.scores:
            self.scores[name] += value
        else:
            self.scores[name] = value
        self.show_scores = True

        # save to scores.txt
        score_list = self.sorted_score(self.scores)
        f = open('scores.txt', 'w')
        f.write('SCORES\n')
        for item in score_list:
            f.write(item[0] + ': ' + str(item[1]) + '\n')
        f.close()

    def display_scores(self):
        """Displays top 10 high scores"""
        SMALL_TEXT = 20
        LENGTH = 500
        RADIIA = 9
        TEXT_X = self.UI['w']/2
        TEXT_Y = self.UNIT_LENGTH*2
        LIST_LENGTH = 10

        noStroke()
        fill(1)
        rect(self.UNIT_LENGTH, self.UNIT_LENGTH, LENGTH, LENGTH, RADIIA)
        fill(0)
        textSize(SMALL_TEXT)
        textAlign(CENTER)
        text('Top 10 High Scores', TEXT_X, TEXT_Y)
        score_list = self.sorted_score(self.scores)
        if len(score_list) >= LIST_LENGTH:
            r = LIST_LENGTH
        else:
            r = len(score_list)
        for i in range(r):
            if score_list[i]:
                textAlign(RIGHT)
                text(score_list[i][0] + ': ', TEXT_X, TEXT_Y+SMALL_TEXT*(2+i))
                textAlign(LEFT)
                text(' ' + str(score_list[i][1]), TEXT_X,
                     TEXT_Y+SMALL_TEXT*(2+i))
        textAlign(CENTER)
        text('Click anywhere to start again.', TEXT_X,
             self.UI['h']-TEXT_Y)

    def sorted_score(self, dictionary):
        """Sorts scores from high to low"""
        return sorted(dictionary.items(),
                      key=lambda x: x[1],
                      reverse=True)

    def outlined_text(self, t, X, Y, size=30):
        """Generates outlined test given input string t and
        location X, Y"""
        textAlign(CENTER)
        textSize(size)
        fill(0)
        for i in range(-1, 2):
            for j in range(-1, 2):
                text(t, X+i, Y+j)
        fill(1)
        text(t, X, Y)

    def input(self, message=''):
        """Gets input from user"""
        from javax.swing import JOptionPane
        return JOptionPane.showInputDialog(frame, message)

    def get_move_score(self, disk_list, row, column):
        """Gets the move score if disk dropped at
        disk_list[row, column]"""
        score = 0

        # get horizontal score
        row_list = disk_list[row]
        for j in range(len(disk_list[0]) - self.SECTION+1):
            window = row_list[j: j+self.SECTION]
            # if column is in the range of window
            if column >= j and column < j+self.SECTION:
                current_score = self.score_rule(window)
                # update best_score in the horizontal section
                # containing current column
                if current_score > score:
                    score = current_score

        # get vertical score
        column_list = []
        for i in range(len(disk_list)):
            column_list.append(disk_list[i][column])
        for i in range(len(disk_list) - self.SECTION+1):
            window = column_list[i: i+self.SECTION]
            if row >= i and row < i+self.SECTION:
                current_score = self.score_rule(window)
                if current_score > score:
                    score = current_score

        # get forward diagonal score
        for i in range(len(disk_list)-self.SECTION+1):
            for j in range(len(disk_list[0])-self.SECTION+1):
                window = [disk_list[i][j],
                          disk_list[i+1][j+1],
                          disk_list[i+2][j+2],
                          disk_list[i+3][j+3]]
                window_index = [[i, j], [i+1, j+1], [i+2, j+2], [i+3, j+3]]
                if [row, column] in window_index:
                    current_score = self.score_rule(window)
                    if current_score > score:
                        score = current_score

        # get backward diagonal score
        for i in range(len(disk_list)-self.SECTION+1, len(disk_list)):
            for j in range(len(disk_list[0])-self.SECTION+1):
                window = [disk_list[i][j],
                          disk_list[i-1][j+1],
                          disk_list[i-2][j+2],
                          disk_list[i-3][j+3]]
            window_index = [[i, j], [i-1, j+1], [i-2, j+2], [i-3, j+3]]
            if [row, column] in window_index:
                current_score = self.score_rule(window)
                if current_score > score:
                    score = current_score

        return score

    def score_rule(self, window):
        """Defines score rule.
        Returns the score of a list with length of 4."""
        current_score = 0
        if window.count(2) == 3:
            current_score = 200
        elif window.count(1) == 3:
            current_score = 100
        elif window.count(1) == 2:
            current_score = 20
        elif window.count(2) == 2:
            current_score = 15
        elif window.count(2) == 1 or window.count(2) == 1:
            current_score = 10
        elif window.count(2) == 0:
            current_score = 5
        return current_score

    def pick_next_move(self, board):
        """Picks the best next move, returns an column index"""
        best_score = 0
        best_column = -1
        columns = []  # stores column(s) with best score(s)

        # check each column
        for c in range(board.COLUMN):
            # get the available row
            stop_row = board.find_stop_row(board.disk_list, c)
            if stop_row != -1:
                # check and update best_score if disk dropped
                # from current column
                score = self.get_move_score(board.disk_list, stop_row, c)
                # If there are more than one best scores,
                # record these locations and rambomly pick one,
                # this will make computer less predictable.
                if score > best_score:
                    best_score = score
                    columns = []
                    columns.append(c)
                elif score == best_score:
                    columns.append(c)
        if len(columns) == 1:
            best_column = columns[0]
        else:
            rand = Random()
            best_column = columns[rand.randint(0, len(columns)-1)]
        return best_column
