# from Vertex import Vertex
#
#
# # # from old.genGraph import *
# # #
# # # makeGraph = genGraph()
# # # graph = makeGraph.initGraph(8)
# # # makeGraph.gen(graph.getVertex('0'), graph, 8, 2)
# # # collection = makeGraph.getCollection()
# # # for g in collection:
# # #     g.draw()
# #
# from sympy import *
#
# x = Symbol('x', real=True)
# r = solve([(x ** (17)) - (x ** (14)) - 1], [x])
# for each in r:
#     if each[0] > 0:
#         print(each[0])

from scipy.optimize import fsolve

a=3
b=2

def func(i):
    x= i[0]
    return [
        (x ** (a)) - (x ** (a-b)) - 1
    ]


r = fsolve(func,1)
print(r[0])

# # #
# # # def isLoop(edges):
# # #     for each in edges:
# # #         for edge in each:
# # #             if edge == 0:
# # #                 return True
# # #     return False
# # #
# # # edges = []
# # # first = True
# # # for i in range(5):
# # #     newEdgesList = []
# # #     for j in range(i + 1):
# # #         newEdgesList.append(0)
# # #     edges.append(newEdgesList)
# # # while(isLoop(edges)):
# # #     if first:
# # #         first = False
# # #         continue
# # #     for i in range(len(edges)):
# # #         for j in range(len(edges[i])):
# # #             if edges[i][j] == 0:
# # #                 edges[i][j] = 1
# # #                 break
# # #             else:
# # #                 edges[i][j] = 0
# # #         else:
# # #             continue
# # #         break
# # #     print(edges)
# #
# #
# # # def multiplierReduction(edges,childrenList):
# # #     groups = []
# # #     for i in range(len(edges)):
# # #         for j in range(len(edges[i])):
# # #             if edges[i][j] == 1:
# # #                 if len(groups) == 0:
# # #                     groups.append([j, i + 1])
# # #                 else:
# # #                     for each in groups:
# # #                         if i + 1 in each:
# # #                             if j not in each:
# # #                                 each.append(j)
# # #                             break
# # #                         if j in each:
# # #                             if i+1 not in each:
# # #                                 each.append(i + 1)
# # #                             break
# # #                     else:
# # #                         groups.append([j, i + 1])
# # #     for i in range(childrenList):
# # #         for each in groups:
# # #             if i in each:
# # #                 break
# # #         else:
# # #             groups.append([i])
# # #     print(groups)
# #
# # def conutEdgesInLocal(edges):
# #     local = []
# #     for i in range(len(edges) + 1):
# #         cnt = 0
# #         if i > 0:
# #             for each in edges[i - 1]:
# #                 if each == 1:
# #                     cnt += 1
# #         if i < (len(edges) + 1):
# #             for group in edges:
# #                 if len(group) > i:
# #                     if group[i] == 1:
# #                         cnt += 1
# #         local.append(cnt)
# #     print(local)
# #
# #
# # a = [[0],[0,0]]
# # b = 4
# # conutEdgesInLocal(a)
# def groupEdges(childrenList, edges):
#     groups = []
#     for i in range(len(edges)):
#         for j in range(len(edges[i])):
#             if edges[i][j] == 1:
#                 if len(groups) == 0:
#                     groups.append([j, i + 1])
#                 else:
#                     for each in groups:
#                         if i + 1 in each:
#                             if j not in each:
#                                 each.append(j)
#                             break
#                         if j in each:
#                             if i + 1 not in each:
#                                 each.append(i + 1)
#                             break
#                     else:
#                         groups.append([j, i + 1])
#
#     # Adding vertex that is not connected to
#     # other children as a separate group
#     for i in range(len(childrenList)):
#         for each in groups:
#             if i in each:
#                 break
#         else:
#             groups.append([i])
#     print(groups)
#
#
# childrenList = []
# for i in range(2):
#     newVertex = Vertex(i)
#     newVertex.setD(2)
#     childrenList.append(newVertex)
# edges = [[0], [0, 0]]
# groupEdges(childrenList, edges)
