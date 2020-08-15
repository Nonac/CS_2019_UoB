# This is the code 2 to fi·nd that given f, k, d and S,
# work out the worst-case branches for f branching on
# a vertex of degree d and size S.


from Vertex import Vertex
from Graph import Graph
import copy
from sympy import *


class worstCase:
    # Initialize the module, S for S(x), d for d(F)
    def __init__(self, S, d):
        self.S = S
        self.d = d
        self.graphList = []
        self.first = True
        self.branchList = []
        self.worstIn = 0
        self.worstOut = 0

    # Generate a list of all the graphs that match the S and d possibilities.
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

    # The worst-case in and out branches are computed for
    # each element in the graph list and compared,
    # keeping the minimum worst-case branch.
    def worstCase(self, graphList, funcA, funcB):
        global MIN
        MIN = 0
        for each in graphList:
            each.countBranch()
            self.branchList.append(each.getBranch())
        worstIn = 0.0
        worstOut = 0.0
        first = True
        for each in self.branchList:
            tempIn = each[0] * funcA + each[1] * funcB
            tempOut = each[2] * funcA + each[3] * funcB
            if first:
                worstIn = tempIn
                worstOut = tempOut
                first = False
            elif (worstIn > tempIn) & (worstOut > tempOut):
                worstIn = tempIn
                worstOut = tempOut
            else:
                x = Symbol('x', real=True)
                r1 = solve([(x ** worstIn) - (x ** (worstIn - worstOut)) - 1], [x])
                r2 = solve([(x ** tempIn) - (x ** (tempIn - tempOut)) - 1], [x])
                for result1 in r1:
                    for result2 in r2:
                        if result1[MIN] > result2[MIN] & result2[MIN] > 0:
                            worstIn = tempIn
                            worstOut = tempOut
                            break
                    else:
                        continue
                    break
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
