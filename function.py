# This method works by grouping the children vertex.
# Make the children vertex in the same group as the
# father to form a single connected component.
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


# The concept of local is to ignore the children's edges
# connected to the external vertex and focus only on the
# father-children local graph. This method yields the
# internal degree of local.
def countEdgesInLocal(edges, withFather=True):
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
        if withFather:
            cnt += 1
        local.append(cnt)
    return local


def recountEdges(i, tempEdges):
    if i < len(tempEdges):
        for j in range(len(tempEdges)):
            if i < len(tempEdges[j]):
                if tempEdges[j][i] == 1:
                    tempEdges[j][i] = 0
                    return tempEdges
    if i > 0:
        for j in range(len(tempEdges[i - 1])):
            if tempEdges[i - 1][j] == 1:
                tempEdges[i - 1][j] = 0
                return tempEdges
    return tempEdges


def isRemove(childrenDegreeList, removeSwitch):
    for i in range(len(childrenDegreeList)):
        if childrenDegreeList[i] == 1 and (not removeSwitch[i]):
            # print(local,childrenDegreeList)
            return True
    return False


def isFinal(graph):
    for each in graph.getVertex():
        if each.getD() != 5:
            return False
    return True


def rebuild(graph):
    for each in graph.getVertex():
        if each.getD() != 5:
            each.addD()
            return graph
        else:
            each.setD(2)
    return graph


def isLoop(graph):
    for each in graph.getVertex():
        if each.getD() != 5:
            return True
    return False
