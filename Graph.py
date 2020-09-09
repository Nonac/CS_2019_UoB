from Vertex import Vertex
import copy
from AlgorithmC5ByDahllof import *
from function import *


class Graph:
    # Data structure:
    # When I want to represent a father with d(x) = 4,
    # carrying children vertex = {x_0, x_1,x_2, x_3}
    # and degrees 3, 3, 3, 4, and edges = {x_0x_1, x_0x_2, x_2x_3},
    # the data structure looks like this:

    # father vertex d=4
    # vertexList={x_0, x_1,x_2, x_3}
    # edges={[1],[1, 0],[0, 0, 1]}

    # edges[0][0] for x_0x_1,
    # edges[1][0] for x_0x_2,
    # and edges[2][2] for x_2x_3.

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
        self.edgesCheck = []

    def getVertex(self):
        return self.vertexList

    def getEdgesCheck(self):
        return self.edgesCheck

    def appendFatherVertex(self, v):
        self.father = v
        self.S += v.getD()

    def appendVertex(self, v):
        self.vertexList.append(v)
        self.S += v.getD()

    def getFather(self):
        return self.father

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
                if algorithmC5(self.father, self.vertexList, self.edges):
                    self.n_in = (len(self.getVertex()) + 1)
                    self.m_in = 2 * (self.S - self.father.getD())
                    for group in self.edges:
                        for each in group:
                            if each == 1:
                                self.m_in -= 2
                    self.removeEdges(self.vertexList, self.edges)
                    self.worstCaseBranch(self.n_in, self.m_in, self.n_out, self.m_out)

    def worstCaseBranch(self, n_in, m_in, n_out, m_out):
        self.branch.append([n_out, m_out, n_in, m_in])
        self.edgesCheck.append(copy.deepcopy(self.edges))

    def removeEdges(self, childrenList, edges):
        local = countEdgesInLocal(edges)
        childrenDegreeList = []
        removeSwitch = []
        tempEdges = copy.deepcopy(edges)

        for each in childrenList:
            childrenDegreeList.append(each.getD())
            removeSwitch.append(False)

        # first remove father
        for i in range(len(local)):
            local[i] -= 1
            childrenDegreeList[i] -= 1
        self.n_out = 1
        self.m_out = 2 * self.father.getD()

        # second if children with degree 1
        # connected to other vertex, remove it

        while isRemove(childrenDegreeList, removeSwitch):
            for i in range(len(childrenDegreeList)):
                if childrenDegreeList[i] == 1:
                    self.n_out += 1
                    self.m_out += 2
                    tempEdges = recountEdges(i, tempEdges)
                    tempLocal = countEdgesInLocal(tempEdges, False)
                    if tempLocal == local:
                        childrenDegreeList[i] = 0
                        removeSwitch[i] = True
                    else:
                        for j in range(len(tempLocal)):
                            if tempLocal[j] == local[j] - 1:
                                childrenDegreeList[j] -= 1
                                if childrenDegreeList[j] == 0:
                                    removeSwitch[j] = True
                        local = tempLocal

        # third if children with degree 0 remove it
        for i in range(len(local)):
            if childrenDegreeList[i] == 0 and (not removeSwitch[i]):
                self.n_out += 1
                removeSwitch[i] = True

    def getBranch(self):
        return self.branch

    def buildEdges(self):
        if len(self.vertexList) > 1:
            for i in range(len(self.vertexList) - 1):
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
        # As a counter, exhaust all possible edge alignments. Example.
        # [[0],[0,0]]
        # [[1],[0,0]]
        # [[0],[1,0]]
        # ........
        # [[1],[1,1]]
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
        # Determine if the exhausted edge array
        # satisfies the children degree requirement
        # after this instantiation.
        for i in range(len(self.vertexList)):
            d = 0
            for j in range(len(self.edges)):
                if i < len(self.edges[j]):
                    if self.edges[j][i] == 1:
                        d += 1
            if i > 0:
                for each in self.edges[i - 1]:
                    if each == 1:
                        d += 1
            if d + 1 > self.vertexList[i].getD():
                return False
        return True
