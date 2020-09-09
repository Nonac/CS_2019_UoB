from function import *


def multiplierReduction(childrenList, edges):
    # Group the children vertex and enable the connected
    # vertex by ignoring the father. This way you can find
    # out the premises where Multiplier Reduction can be used.
    groups = groupEdges(childrenList, edges)
    # Calculate the children's degree of Local while considering
    # only the Local degree. this can determine the case where N(x)
    # has children connected to the outside.
    childrenLocalDegree = countEdgesInLocal(edges)
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
        return cnt >= 2
    else:
        return False


def advanceMultiplierReduction(childrenList, edges):
    # Counting the number of N(x) connected to
    # the external vertex
    cnt_NX = 0
    # Counting the number of N(x) connected to
    # the external vertex by two edges. In the next iteration
    # this case will run case 2 to smaller N(x), and then count
    # the branch which much smaller than it used to be.
    cnt_NNx = 0
    childrenLocalDegree = countEdgesInLocal(edges)
    for i in range(len(childrenList)):
        if childrenLocalDegree[i] != childrenList[i].getD():
            cnt_NX += 1
        if (childrenList[i].getD() - childrenLocalDegree[i]) == 2:
            cnt_NNx += 1
        elif (childrenList[i].getD() - childrenLocalDegree[i]) == 1:
            cnt_NNx += 1
    return (cnt_NX == 2) or (cnt_NX == 1 and ((cnt_NNx == 1) or (cnt_NNx == 2)))


def connected(childrenList, edges):
    degrees = []
    for each in childrenList:
        degrees.append(each.getD())

    localDegrees = countEdgesInLocal(edges)

    for i in range(len(degrees)):
        if degrees[i] != localDegrees[i]:
            return False
    return True


def algorithmC5(father, childrenList, edges):
    # case 1
    if father.getD() == 0 | len(childrenList) == 0:
        return False
    # case 2
    elif connected(childrenList, edges):
        return False
    # case 4
    elif multiplierReduction(childrenList, edges):
        return False
    # case 5
    elif advanceMultiplierReduction(childrenList, edges):
        return False

    # Case 3 is a simplified version of Algorithm 5,
    # so it is covered by this algorithm.
    return True
