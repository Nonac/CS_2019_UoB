from Vertex import Vertex


class Graph:
    def __init__(self):
        self.vertexList = []
        self.S = 0
        self.father = Vertex(0)
        self.m_in = 0
        self.n_in = 0
        self.m_out = 0
        self.n_out = 0
        self.branch = []

    def appendFatherVertex(self, v):
        self.father = v
        self.S += v.getD()

    def appendVertex(self, v):
        self.vertexList.append(v)
        self.S += v.getD()

    def getVertex(self):
        return self.vertexList

    def getS(self):
        return self.S

    def recountS(self):
        self.S = self.father.getD()
        for each in self.vertexList:
            self.S += each.getD()

    def countBranch(self):
        self.n_out = 1
        self.m_out = 2 * self.father.getD()
        self.n_in = (len(self.getVertex()) + 1)
        self.m_in = 2 * (self.S - self.father.getD())
        for each in self.vertexList:
            if each.getD() == 2:
                self.n_out += 1
                self.m_out += 2
        self.branch = [self.n_in, self.m_in, self.n_out, self.m_out]

    def getBranch(self):
        return self.branch
