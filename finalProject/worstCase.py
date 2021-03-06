# This is the code 2 to find that given f, k, d and S,
# work out the worst-case branches for f branching on
# a vertex of degree d and size S.


from Vertex import Vertex
from Graph import Graph
import copy
from scipy.optimize import fsolve
from function import *
import matplotlib.pyplot as plt
import networkx as nx
from sovleTau import *

isDraw = False


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
        self.edgesList = []
        self.edgesCheck = []
        self.graphCheck = []
        self.graphCntList = []
        self.nxG = nx.Graph()

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
        while isLoop(graph):
            if graph.getS() == self.S:
                self.graphList.append(copy.deepcopy(graph))
            graph = rebuild(graph)
            graph.recountS()
        if isFinal(graph):
            if graph.getS() == self.S:
                self.graphList.append(copy.deepcopy(graph))
        return self.graphList

    # The worst-case in and out branches are computed for
    # each element in the graph list and compared,
    # keeping the minimum worst-case branch.
    def worstCase(self, graphList, funcA, funcB):
        global MIN
        MIN = 0
        cnt = 0
        for each in graphList:
            each.countBranch()
            for eachBranch in each.getBranch():
                self.branchList.append(eachBranch)
                self.graphCntList.append(cnt)
            for eachEdgesCheck in each.getEdgesCheck():
                self.edgesList.append(eachEdgesCheck)
            cnt += 1
        worstIn = 0.0
        worstOut = 0.0
        first = True
        if not isDraw:
            self.branchList = self.deduplication()
        cnt = 0
        for each in self.branchList:
            tempOut = each[0] * funcA + each[1] * funcB
            tempIn = each[2] * funcA + each[3] * funcB
            if first:
                worstIn = tempIn
                worstOut = tempOut
                first = False
                self.branchCheck = each
                self.edgesCheck = self.edgesList[cnt]
                self.graphCheck = self.graphList[self.graphCntList[cnt]]
            elif (worstIn > tempIn) & (worstOut > tempOut):
                worstIn = tempIn
                worstOut = tempOut
                self.branchCheck = each
                self.edgesCheck = self.edgesList[cnt]
                self.graphCheck = self.graphList[self.graphCntList[cnt]]
            else:
                r1 = sovleTau(worstIn, worstOut)
                r2 = sovleTau(tempIn, tempOut)
                if (r2 > r1) and (r1 > 0):
                    worstIn = tempIn
                    worstOut = tempOut
                    self.branchCheck = each
                    self.edgesCheck = self.edgesList[cnt]
                    self.graphCheck = self.graphList[self.graphCntList[cnt]]
            cnt += 1
        self.worstIn = worstIn
        self.worstOut = worstOut
        self.countWorstTime()
        if isDraw:
            self.draw()

    def draw(self):
        labeldict = {}
        labeldict[self.graphCheck.getFather()] = 'F:' + str(self.graphCheck.getFather().getD())
        for each in self.graphCheck.getVertex():
            self.nxG.add_edge(self.graphCheck.getFather(), each)
            labeldict[each] = each.getD()
        for i in range(len(self.edgesCheck)):
            for j in range(len(self.edgesCheck[i])):
                if self.edgesCheck[i][j] == 1:
                    self.nxG.add_edge(self.graphCheck.getVertex()[i + 1],
                                      self.graphCheck.getVertex()[j])
        for v in list(self.nxG.nodes):
            b = list(self.nxG.neighbors(v))
            for i in range(v.getD() - len(b)):
                self.nxG.add_edge(Vertex(0), v)
        nx.draw_kamada_kawai(self.nxG, labels=labeldict, with_labels=True, edge_color='b', node_color='g',
                             node_size=1000)
        plt.savefig(str(self.graphCheck.getS()))
        plt.show()

    def getBranchCheck(self):
        return self.branchCheck

    def countWorstTime(self):
        self.worstTime = sovleTau(self.worstIn, self.worstOut)

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
