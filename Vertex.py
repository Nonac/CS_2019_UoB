class Vertex:
    def __init__(self, key):
        self.id = key
        self.d = 0

    def setD(self, d):
        self.d = d

    def getD(self):
        return self.d

    def addD(self):
        if self.d < 5:
            self.d += 1
