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
        self.edges = []
        self.first = True

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
        self.buildEdges()
        while self.isLoop():
            self.reBuildEdges()
            if self.edgesIsValid():
                if self.algorithmC5(self.father, self.vertexList, self.edges):
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

    def buildEdges(self):
        for i in range(len(self.vertexList)):
            newEdgesList = []
            for j in range(i + 1):
                newEdgesList.append(0)
            self.edges.append(newEdgesList)

    def isLoop(self):
        for each in self.edges:
            for edge in each:
                if edge == 0:
                    return True
        return False

    def isFirst(self):
        for each in self.edges:
            for edge in each:
                if edge == 1:
                    return False
        return True

    def reBuildEdges(self):
        if self.isFirst() & self.first:
            self.first = False
            return
        for i in range(len(self.edges)):
            for j in range(len(self.edges[i])):
                if self.edges[i][j] == 0:
                    self.edges[i][j] = 1
                    break
                else:
                    self.edges[i][j] = 0
            else:
                continue
            break

    def edgesIsValid(self):
        for i in range(len(self.vertexList)):
            d = 0
            for j in range(len(self.edges)):
                if i < len(self.edges[j]):
                    if self.edges[j][i] == 1:
                        d += 1
            if i > 1:
                for each in self.edges[i - 1]:
                    if each == 1:
                        d += 1
            if d > self.vertexList[i].getD():
                return False
        return True

    def algorithmC5(self, father, childrenList, edges):
        if father.getD() == 0 | len(childrenList) == 0:
            return False
        elif self.multiplierReduction(childrenList, edges):
            return False

        return True

    def multiplierReduction(self, childrenList, edges):
        groups = []
        for i in range(len(edges)):
            for j in range(len(edges[i])):
                if edges[i][j] == 1:
                    if len(groups) == 0:
                        groups.append([j, i + 1])
                    else:
                        for each in groups:
                            if i + 1 in each:
                                if j not in each:
                                    each.append(j)
                                break
                            if j in each:
                                if i + 1 not in each:
                                    each.append(i + 1)
                                break
                        else:
                            groups.append([j, i + 1])
        for i in range(len(childrenList)):
            for each in groups:
                if i in each:
                    break
            else:
                groups.append([i])
        return len(groups) == 2
