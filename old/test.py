from old.genGraph import *

makeGraph = genGraph()
graph = makeGraph.initGraph(8)
makeGraph.gen(graph.getVertex('0'), graph, 8, 2)
collection = makeGraph.getCollection()
for g in collection:
    g.draw()
