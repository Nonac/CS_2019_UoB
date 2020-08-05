# This is the code 1 to find minimum S(k) with given k.


import math
from Vertex import Vertex


class minS:
    def __init__(self, k):
        self.k = k
        self.S = 0
        self.first = True
        self.begin = True
        self.minK = 0

    def minS(self):
        for d_0 in range(math.ceil(self.k), 6):
            graph = []
            for i in range(0, d_0):
                vertex = Vertex(i)
                vertex.setD(2)
                graph.append(vertex)
            while self.isLoop(graph):
                graph = self.rebuild(graph)
                S = self.countS(graph) + d_0
                k = self.countAlpha(d_0, graph) / self.countBeta(d_0, graph)
                if self.first & (self.k <= k):
                    self.S = S
                    self.first = False
                    self.minK = k
                elif (self.S > S) & (self.k <= k):
                    self.S = S
                    self.minK = k

    def rebuild(self, graph):
        if self.isBegin(graph) & self.begin:
            self.begin = False
            return graph
        else:
            for each in graph:
                if each.getD() != 5:
                    each.addD()
                    return graph
                else:
                    each.setD(2)
            return graph

    def getS(self):
        return self.S

    def getK(self):
        return self.minK

    def countAlpha(self, d_0, graph):
        cnt = d_0
        for each in graph:
            if each.getD() < d_0:
                cnt += 1
        return cnt

    def countBeta(self, d_0, graph):
        beta = 1
        for each in graph:
            if each.getD() < d_0:
                beta = beta + (1 / each.getD())
        return beta

    def countS(self, graph):
        S = 0
        for each in graph:
            S = S + each.getD()
        return S

    def isLoop(self, graph):
        for each in graph:
            if each.getD() != 5:
                return True
        return False

    def isBegin(self, graph):
        for each in graph:
            if each.getD() != 2:
                return False
        return True



