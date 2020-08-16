# from old.genGraph import *
#
# makeGraph = genGraph()
# graph = makeGraph.initGraph(8)
# makeGraph.gen(graph.getVertex('0'), graph, 8, 2)
# collection = makeGraph.getCollection()
# for g in collection:
#     g.draw()

# from sympy import *
#
# x = Symbol('x', real=True)
# r = solve([(x ** (22)) - (x ** (13)) - 1], [x])
# for each in r:
#     if each[0] > 0:
#         print(each[0])

def isLoop(edges):
    for each in edges:
        for edge in each:
            if edge == 0:
                return True
    return False

edges = []
first = True
for i in range(5):
    newEdgesList = []
    for j in range(i + 1):
        newEdgesList.append(0)
    edges.append(newEdgesList)
while(isLoop(edges)):
    if first:
        first = False
        continue
    for i in range(len(edges)):
        for j in range(len(edges[i])):
            if edges[i][j] == 0:
                edges[i][j] = 1
                break
            else:
                edges[i][j] = 0
        else:
            continue
        break
    print(edges)



