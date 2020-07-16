from Vertex import Vertex


class Graph:
    def __init__(self):
        self.vertList = {}
        self.numVertices = 0
        self.S = 0
        self.degreeF = 0

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
        self.S = self.S + 2

    def subS(self):
        self.S = self.S - 2

    def addEdge(self, f, t, cost=0):
        if f not in self.vertList:
            nv = self.addVertex(f)
        if t not in self.vertList:
            nv = self.addVertex(t)
        self.vertList[f].addNeighbor(self.vertList[t], cost)
        self.vertList[f].addDegree()
        self.vertList[t].addDegree()
        self.addS()
        self.getDegreeF()

    def deleteEdge(self, f, t):
        if f not in self.vertList:
            return
        if t not in self.vertList:
            return
        self.vertList[f].deleteNeighbor(self.vertList[t])
        self.vertList[f].subDegree()
        self.vertList[t].subDegree()
        self.subS()
        self.getDegreeF()

    def getVertices(self):
        return self.vertList.keys()

    def __iter__(self):
        return iter(self.vertList.values())

    def getS(self):
        return self.S

    def getDegreeF(self):
        maxDegree = 0
        for v in self.vertList:
            if v.getDegree > maxDegree:
                maxDegree = v.getDegree
        self.degreeF = maxDegree
