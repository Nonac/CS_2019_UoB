# This is the code 2 to find that given f, k, d and S,
# work out the worst-case branches for f branching on
# a vertex of degree d and size S.


from Vertex import Vertex
from Graph import Graph
import copy


class worstCase:
    def __init__(self, S, d):
        self.S = S
        self.d = d
        self.graphList = []
        self.first = True
        self.branchList = []
        self.worstIn = 0
        self.worstOut = 0

    def genGraphList(self):
        graph = Graph()
        v_0 = Vertex(0)
        v_0.setD(self.d)
        graph.appendFatherVertex(v_0)
        for i in range(self.d):
            newVertex = Vertex(i)
            newVertex.setD(2)
            graph.appendVertex(newVertex)
        while self.isLoop(graph):
            if graph.getS() == self.S:
                self.graphList.append(copy.deepcopy(graph))
            graph = self.rebuild(graph)
            graph.recountS()
        return self.graphList

    def worstCase(self, graphList, funcA, funcB):
        for each in graphList:
            each.countBranch()
            self.branchList.append(each.getBranch())
        worstIn = 0.0
        worstOut = 0.0
        first = True
        for each in self.branchList:
            if first:
                worstIn = each[0] * funcA + each[1] * funcB
                worstOut = each[2] * funcA + each[3] * funcB
                first=False
            elif (worstIn > each[0] * funcA + each[1] * funcB) \
                    & (worstOut > each[2] * funcA + each[3] * funcB):
                worstIn = each[0] * funcA + each[1] * funcB
                worstOut = each[2] * funcA + each[3] * funcB
        self.worstIn = worstIn
        self.worstOut = worstOut

    def getWorstIn(self):
        return self.worstIn

    def getWorstOut(self):
        return self.worstOut

    def isLoop(self, graph):
        for each in graph.getVertex():
            if each.getD() != 5:
                return True
        return False

    def rebuild(self, graph):
        for each in graph.getVertex():
            if each.getD() != 5:
                each.addD()
                return graph
            else:
                each.setD(2)
        return graph

# w = worstCase(16, 3)
# # l = w.genGraphList()
# # w.worstCase(l)
