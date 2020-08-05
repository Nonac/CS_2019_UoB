from minS import minS
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

    def worstCase(self,graphList):
        for each in graphList:
            each.countBranch()
            self.branchList.append(each.getBranch())
        print(1)

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


w=worstCase(16,3)
l=w.genGraphList()
w.worstCase(l)