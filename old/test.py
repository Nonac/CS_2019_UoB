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

a=0.2680
b=0.1961

branchs=[[1,8,5,18],[2,10,5,18]]
for branch in branchs:

    b_in=branch[0]*a+branch[1]*b
    b_out=branch[2]*a+branch[3]*b
    print(b_in,b_out)

    def func(i):
        x= i[0]
        return [
            (x ** (b_in)) - (x ** (b_in-b_out)) - 1
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
def conutEdgesInLocal(edges):
    local = []
    for i in range(len(edges) + 1):
        cnt = 0
        if i > 0:
            for each in edges[i - 1]:
                if each == 1:
                    cnt += 1
        if i < (len(edges) + 1):
            for group in edges:
                if len(group) > i:
                    if group[i] == 1:
                        cnt += 1
        # father to child edge
        cnt += 1
        local.append(cnt)
    return local
#
def case5(childrenList, edges):
    # Counting the number of children connected to
    # the external vertex
    cnt_NX = 0
    # Counting the number of N(x) connected to
    # the external vertex by two edges. In the next iteration
    # this case will run case 2 to smaller N(x), and then count
    # the branch which much smaller than it used to be.
    cnt_NNx = 0
    childrenLocalDegree = conutEdgesInLocal(edges)
    for i in range(len(childrenList)):
        if childrenLocalDegree[i] != childrenList[i].getD():
            cnt_NX += 1
        if (childrenList[i].getD() - childrenLocalDegree[i]) == 2:
            cnt_NNx += 1
        elif (childrenList[i].getD() - childrenLocalDegree[i]) == 1:
            cnt_NNx += 1
    return (cnt_NX == 2) or (cnt_NX == 1 and ((cnt_NNx == 1) or (cnt_NNx == 2)))

def groupEdges(childrenList, edges):
    groups = []
    for i in range(len(edges)):
        for j in range(len(edges[i])):
            if edges[i][j] == 1:
                if len(groups) == 0:
                    groups.append([j, i + 1])
                else:
                    for each in groups:
                        if i + 1 in each:
                            if j not in each:
                                each.append(j)
                            break
                        if j in each:
                            if i + 1 not in each:
                                each.append(i + 1)
                            break
                    else:
                        groups.append([j, i + 1])

    # Adding vertex that is not connected to
    # other children as a separate group
    for i in range(len(childrenList)):
        for each in groups:
            if i in each:
                break
        else:
            groups.append([i])
    return groups

def conutEdgesInLocal(edges):
    local = []
    for i in range(len(edges) + 1):
        cnt = 0
        if i > 0:
            for each in edges[i - 1]:
                if each == 1:
                    cnt += 1
        if i < (len(edges) + 1):
            for group in edges:
                if len(group) > i:
                    if group[i] == 1:
                        cnt += 1
        # father to child edge
        cnt += 1
        local.append(cnt)
    return local

def multiplierReduction(childrenList, edges):
    # Group the children vertex and enable the connected
    # vertex by ignoring the father. This way you can find
    # out the premises where Multiplier Reduction can be used.
    groups = groupEdges(childrenList, edges)
    # Calculate the children's degree of Local while considering
    # only the Local degree. this can determine the case where N(x)
    # has children connected to the outside.
    childrenLocalDegree = conutEdgesInLocal(edges)
    if len(groups) >= 2:
        # Counting the number of connected components.
        # Calculations are done in units of connected
        # components, not in individual vertex.
        cnt = 0
        for group in groups:
            for each in group:
                if childrenLocalDegree[each] \
                        != childrenList[each].getD():
                    break
            else:
                cnt += 1
        return cnt >= 1
    else:
        return False




from Vertex import Vertex
#
childrenList = []
degree=[3,3,3,2]
for i in range(4):
    newVertex = Vertex(i)
    newVertex.setD(degree[i])
    childrenList.append(newVertex)
edges = [[1], [0, 1], [0, 0, 0]]
print(multiplierReduction(childrenList, edges))
