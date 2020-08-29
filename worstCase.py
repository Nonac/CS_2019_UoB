# This is the code 2 to fi·nd that given f, k, d and S,
# work out the worst-case branches for f branching on
# a vertex of degree d and size S.


from Vertex import Vertex
from Graph import Graph
import copy
from scipy.optimize import fsolve


class worstCase:
    # Initialize the module, S for S(x), d for d(F)
    def __init__(self, S, d):
        self.S = S
        self.d = d
        self.graphList = []
        self.first = True
        self.branchList = []
        self.worstIn = 0.0
        self.worstOut = 0.0
        self.worstTime = 0.0
        self.branchCheck = []

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
        if self.isFinal(graph):
            if graph.getS() == self.S:
                self.graphList.append(copy.deepcopy(graph))
        return self.graphList

    # The worst-case in and out branches are computed for
    # each element in the graph list and compared,
    # keeping the minimum worst-case branch.
    def worstCase(self, graphList, funcA, funcB):
        global MIN
        MIN = 0
        for each in graphList:
            each.countBranch()
            for eachBranch in each.getBranch():
                self.branchList.append(eachBranch)
        worstIn = 0.0
        worstOut = 0.0
        first = True
        self.branchList = self.deduplication()

        for each in self.branchList:
            tempOut = each[0] * funcA + each[1] * funcB
            tempIn = each[2] * funcA + each[3] * funcB
            if first:
                worstIn = tempIn
                worstOut = tempOut
                first = False
                self.branchCheck = each
            elif (worstIn > tempIn) & (worstOut > tempOut):
                worstIn = tempIn
                worstOut = tempOut
                self.branchCheck = each
            else:
                def func1(i):
                    x = i[MIN]
                    return [(x ** worstIn) - (x ** (worstIn - worstOut)) - 1]

                def func2(i):
                    x = i[MIN]
                    return [(x ** tempIn) - (x ** (tempIn - tempOut)) - 1]

                r1 = fsolve(func1, [1])[MIN]
                r2 = fsolve(func2, [1])[MIN]
                if (r2 > r1) and (r1 > 0):
                    worstIn = tempIn
                    worstOut = tempOut
                    self.branchCheck = each
        self.worstIn = worstIn
        self.worstOut = worstOut
        self.countWorstTime()

    def getBranchCheck(self):
        return self.branchCheck

    def countWorstTime(self):
        a = self.worstIn
        b = self.worstOut

        def func(i):
            x = i[0]
            return [((x ** (a)) - (x ** (a - b)) - 1)]

        self.worstTime = fsolve(func, [1])[0]

    def getWorstTime(self):
        return self.worstTime

    def deduplication(self):
        new = []
        for each in self.branchList:
            if each not in new:
                new.append(each)
        return new

    def getWorstIn(self):
        return self.worstIn

    def getWorstOut(self):
        return self.worstOut

    def isLoop(self, graph):
        for each in graph.getVertex():
            if each.getD() != 5:
                return True
        return False

    def isFinal(self,graph):
        for each in graph.getVertex():
            if each.getD()!=5:
                return False
        return True

    def rebuild(self, graph):
        for each in graph.getVertex():
            if each.getD() != 5:
                each.addD()
                return graph
            else:
                each.setD(2)
        return graph

