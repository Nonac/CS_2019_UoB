from Vertex import Vertex
import matplotlib.pyplot as plt
import networkx as nx


class Graph:
    def __init__(self):
        self.vertList = {}
        self.numVertices = 0
        self.S = 0
        self.degreeF = 0
        self.nxG = nx.Graph()

    def addVertex(self, key):
        self.numVertices = self.numVertices + 1
        newVertex = Vertex(key)
        self.vertList[key] = newVertex
        return newVertex

    def getVertex(self, n):
        if n in self.vertList:
            return self.vertList[n]
        else:
            return None

    def __contains__(self, n):
        return n in self.vertList

    def addS(self):
        self.S = 0
        for v in self.vertList:
            self.S = self.S + self.vertList[v].getDegree()

    def addEdge(self, f, t, cost=0):
        if f not in self.vertList:
            nv = self.addVertex(f)
        if t not in self.vertList:
            nv = self.addVertex(t)
        self.vertList[f].addNeighbor(self.vertList[t], cost)
        self.vertList[t].addNeighbor(self.vertList[f], cost)
        self.addS()
        self.getDegreeF()
        self.nxG.add_edge(f, t)

    def removeEdge(self, f, t):
        self.vertList[f].removeNeighbor(self.vertList[t])
        self.vertList[t].removeNeighbor(self.vertList[f])
        self.addS()
        self.getDegreeF()
        self.nxG.remove_edge(f, t)

    def getVertices(self):
        return self.vertList.values()

    def __iter__(self):
        return iter(self.vertList.values())

    def getS(self):
        return self.S

    def getDegreeF(self):
        maxDegree = 0
        for v in self.vertList:
            if self.vertList[v].getDegree() > maxDegree:
                maxDegree = self.vertList[v].getDegree()
        self.degreeF = maxDegree
        return self.degreeF

    def draw(self):
        nx.draw(self.nxG, with_labels=True, edge_color='b', node_color='g', node_size=1000)
        plt.show()
