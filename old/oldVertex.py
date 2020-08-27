class Vertex:
    def __init__(self, key):
        self.id = key
        self.connectedTo = {}
        self.previous = None

    def addPrevious(self, v):
        self.previous = v

    def getPrevious(self):
        return self.previous

    def removePrevious(self):
        self.previous = None

    def addNeighbor(self, nbr, weight=0):
        self.connectedTo[nbr] = weight

    def removeNeighbor(self, nbr):
        self.connectedTo.pop(nbr)

    def __str__(self):
        return str(self.id) + ' connectedTo: ' + str([x.id for x in self.connectedTo])

    def getConnections(self):
        return list(self.connectedTo.keys())

    def getId(self):
        return self.id

    def getWeight(self, nbr):
        return self.connectedTo[nbr]

    def getDegree(self):
        return len(self.connectedTo)
