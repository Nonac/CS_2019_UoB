from Vertex import Vertex
import copy


class Graph:
    # Data structure:
    # When I want to represent a father with d(x) = 4,
    # carrying children vertex = {x_0, x_1,x_2, x_3}
    # and degrees 3, 3, 3, 4, and edges = {x_0x_1, x_0x_2, x_2x_3},
    # the data structure looks like this:

    # father vertex d=4
    # vertexList={x_0, x_1,x_2, x_3}
    # edges={[1],[1, 0],[0, 0, 1]}

    # edges[0][0] for x_0x_1,
    # edges[1][0] for x_0x_2,
    # and edges[2][2] for x_2x_3.

    def __init__(self):
        self.vertexList = []
        self.S = 0
        self.father = Vertex(0)
        self.m_in = 0
        self.n_in = 0
        self.m_out = 0
        self.n_out = 0
        self.branch = []
        self.edges = []
        self.first = True
        self.edgesCheck = []

    def getVertex(self):
        return self.vertexList

    def getEdgesCheck(self):
        return self.edgesCheck

    def appendFatherVertex(self, v):
        self.father = v
        self.S += v.getD()

    def appendVertex(self, v):
        self.vertexList.append(v)
        self.S += v.getD()

    def getVertex(self):
        return self.vertexList

    def getS(self):
        return self.S

    def recountS(self):
        self.S = self.father.getD()
        for each in self.vertexList:
            self.S += each.getD()

    def countBranch(self):
        self.buildEdges()
        while self.isLoop():
            self.reBuildEdges()
            if self.edgesIsValid():
                if self.algorithmC5(self.father, self.vertexList, self.edges):
                    self.n_in = (len(self.getVertex()) + 1)
                    self.m_in = 2 * (self.S - self.father.getD())
                    for group in self.edges:
                        for each in group:
                            if each == 1:
                                self.m_in -= 2
                    self.removeEdges(self.vertexList, self.edges)
                    self.worstCaseBranch(self.n_in, self.m_in, self.n_out, self.m_out)

    def worstCaseBranch(self, n_in, m_in, n_out, m_out):
        self.branch.append([n_in, m_in, n_out, m_out])
        self.edgesCheck.append(copy.deepcopy(self.edges))

    def removeEdges(self, childrenList, edges):
        local = self.conutEdgesInLocal(edges)
        childrenDegreeList = []
        removeSwitch = []
        tempEdges = copy.deepcopy(edges)

        for each in childrenList:
            childrenDegreeList.append(each.getD())
            removeSwitch.append(False)

        # first remove father
        for i in range(len(local)):
            local[i] -= 1
            childrenDegreeList[i] -= 1
        self.n_out = 1
        self.m_out = 2 * self.father.getD()

        # second if children with degree 1
        # connected to other children, remove it

        while self.isRemove(childrenDegreeList, local):
            for i in range(len(local)):
                if local[i] == 1 and childrenDegreeList[i] == 1:
                    self.n_out += 1
                    self.m_out += 2
                    tempEdges = self.recountEdges(i, tempEdges)
                    tempLocal = self.conutEdgesInLocalWithoutFather(tempEdges)
                    for j in range(len(tempLocal)):
                        if tempLocal[j] == local[j] - 1:
                            childrenDegreeList[j] -= 1
                            removeSwitch[j] = True
                    local = tempLocal

        # third if children with degree 0 remove it
        for i in range(len(local)):
            if childrenDegreeList[i] == 0 and removeSwitch[i] == False:
                self.n_out += 1
                removeSwitch[i] = True

    def isRemove(self, childrenDegreeList, local):
        for i in range(len(local)):
            if local[i] == 1 and childrenDegreeList[i] == 1:
                # print(local,childrenDegreeList)
                return True
        return False

    def recountEdges(self, i, tempEdges):
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

    def getBranch(self):
        return self.branch

    def buildEdges(self):
        if len(self.vertexList) > 1:
            for i in range(len(self.vertexList) - 1):
                newEdgesList = []
                for j in range(i + 1):
                    newEdgesList.append(0)
                self.edges.append(newEdgesList)

    def isLoop(self):
        for each in self.edges:
            for edge in each:
                if edge == 0:
                    return True
        return False

    def isFirst(self):
        for each in self.edges:
            for edge in each:
                if edge == 1:
                    return False
        return True

    def reBuildEdges(self):
        # As a counter, exhaust all possible edge alignments. Example.
        # [[0],[0,0]]
        # [[1],[0,0]]
        # [[0],[1,0]]
        # ........
        # [[1],[1,1]]
        if self.isFirst() & self.first:
            self.first = False
            return
        for i in range(len(self.edges)):
            for j in range(len(self.edges[i])):
                if self.edges[i][j] == 0:
                    self.edges[i][j] = 1
                    break
                else:
                    self.edges[i][j] = 0
            else:
                continue
            break

    def edgesIsValid(self):
        # Determine if the exhausted edge array
        # satisfies the children degree requirement
        # after this instantiation.
        for i in range(len(self.vertexList)):
            d = 0
            for j in range(len(self.edges)):
                if i < len(self.edges[j]):
                    if self.edges[j][i] == 1:
                        d += 1
            if i > 0:
                for each in self.edges[i - 1]:
                    if each == 1:
                        d += 1
            if d + 1 > self.vertexList[i].getD():
                return False
        return True

    def algorithmC5(self, father, childrenList, edges):
        # case 1
        if father.getD() == 0 | len(childrenList) == 0:
            return False
        # case 4
        elif self.multiplierReduction(childrenList, edges):
            return False
        # case 5
        elif self.case5(childrenList, edges):
            return False
        # Since case 2 discusses whether the father is
        # connected or not, and this algorithm assumes
        # that the father is connected to the children,
        # there is no point in discussing it.
        # Case 3 is a simplified version of Algorithm 5,
        # so it is covered by this algorithm.
        return True

    def case5(self, childrenList, edges):
        # Counting the number of N(x) connected to
        # the external vertex
        cnt_NX = 0
        # Counting the number of N(x) connected to
        # the external vertex by two edges. In the next iteration
        # this case will run case 2 to smaller N(x), and then count
        # the branch which much smaller than it used to be.
        cnt_NNx = 0
        childrenLocalDegree = self.conutEdgesInLocal(edges)
        for i in range(len(childrenList)):
            if childrenLocalDegree[i] != childrenList[i].getD():
                cnt_NX += 1
            if (childrenList[i].getD() - childrenLocalDegree[i]) == 2:
                cnt_NNx += 1
            elif (childrenList[i].getD() - childrenLocalDegree[i]) == 1:
                cnt_NNx += 1
        return (cnt_NX == 2) or (cnt_NX == 1 and ((cnt_NNx == 1) or (cnt_NNx == 2)))

    def multiplierReduction(self, childrenList, edges):
        # Group the children vertex and enable the connected
        # vertex by ignoring the father. This way you can find
        # out the premises where Multiplier Reduction can be used.
        groups = self.groupEdges(childrenList, edges)
        # Calculate the children's degree of Local while considering
        # only the Local degree. this can determine the case where N(x)
        # has children connected to the outside.
        childrenLocalDegree = self.conutEdgesInLocal(edges)
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

    def groupEdges(self, childrenList, edges):
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

    def conutEdgesInLocal(self, edges):
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

    def conutEdgesInLocalWithoutFather(self, edges):
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
            local.append(cnt)
        return local
