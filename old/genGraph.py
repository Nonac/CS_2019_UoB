from old.Graph import Graph
import copy


class genGraph:
    def __init__(self):
        # self.S_x = S_x
        # self.d_F = d_F
        self.collection = []

    def initGraph(self, S):
        graph = Graph()
        for i in range(0, int((S + 2) / 2 + 1)):
            graph.addVertex(str(i))
        return graph

    def getCollection(self):
        return self.collection

    def gen(self, v, graph, S, d):
        if v.getId() == '0':
            for i in range(1, d + 1):
                graph.addEdge(v.getId(), str(i))
                graph.getVertex(str(i)).addPrevious(v)
            for vi in v.getConnections():
                self.gen(vi, graph, S, d)
        else:
            if (graph.getS() > S) | (graph.getDegreeF() > d):
                return
            elif graph.getS() == S:
                newGraph = copy.deepcopy(graph)
                self.collection.append(newGraph)
                return
            elif graph.getS() < S:
                flag = True
                for vi in graph.getVertices():
                    if vi.getConnections():
                        flag = flag & (vi.getDegree() == d)
                if flag:
                    newGraph = copy.deepcopy(graph)
                    self.collection.append(newGraph)
                    return
                else:
                    for vi in graph.getVertices():
                        if (vi not in v.getConnections()) & (vi != v):
                            graph.addEdge(v.getId(), vi.getId())
                            graph.getVertex(vi.addPrevious(v))
                            for vj in graph.getVertices():
                                if (len(vj.getConnections()) != 0) \
                                        & (vj != v.getPrevious()):
                                    self.gen(vj, graph, S, d)
                            graph.removeEdge(v.getId(), vi.getId())
