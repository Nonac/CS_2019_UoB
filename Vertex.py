class Vertex:
    def __init__(self, key):
        self.id = key
        self.connectedTo = {}
        self.degree = 0

    def addDegree(self):
        self.degree = self.degree + 1

    def subDegree(self):
        self.degree = self.degree - 1

    def addNeighbor(self, nbr, weight=0):
        self.connectedTo[nbr] = weight

    def deleteNeighbor(self, nbr):
        self.connectedTo.pop(nbr)

    def __str__(self):
        return str(self.id) + ' connectedTo: ' + str([x.id for x in self.connectedTo])

    def getConnections(self):
        return self.connectedTo.keys()

    def getId(self):
        return self.id

    def getWeight(self, nbr):
        return self.connectedTo[nbr]

    def getDegree(self):
        return self.degree
