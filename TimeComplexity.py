import math
from worstCase import *
from scipy.optimize import fsolve

Kai_i = 1
b_i = 1
k_i = 3.0
S = 0


def compare(tau):
    a = 4
    b = 4

    def func1(i):
        x = i[0]
        if b > 0 and a >= b:
            return [(x ** a) - (x ** (a - b)) - 1]
        elif a > 0 and b >= a:
            return [(x ** b) - (x ** (b - a)) - 1]
        elif b <= a and a < 0:
            return [1 - (x ** (- b) - (x ** (-a)))]
        elif a < 0 and b > 0:
            return [(x ** b) - (x ** (b - a)) - 1]
        elif a <= b and b < 0:
            return [1 - (x ** (- b) - (x ** (-a)))]

    r1 = fsolve(func1, [1])[0]
    return tau > r1


for i in range(3000, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()


    def countProcess(minS, Kai_p, b_p, k_p):
        tau = 0
        a_min = 0
        b_min = 0
        Kai_min = 0
        Kai = Kai_p + (minS.getK() - k_p) * b_p
        branch = []
        for iterB in range(1, 1000):
            w = worstCase(minS.getS(), math.ceil(minS.getK()))
            b = iterB / 1000
            a = Kai - (minS.getK() * b)
            w.worstCase(w.genGraphList(), a, b)
            # if tau == 0:
            #     tau = w.getWorstTime()
            #     a_min = a
            #     b_min = b
            #     Kai_min = Kai
            #     branch=w.getBranchCheck()
            # elif w.getWorstTime() < tau:
            if compare(w.getWorstTime()):
                tau = w.getWorstTime()
                a_min = a
                b_min = b
                Kai_min = Kai
                branch = w.getBranchCheck()
            else:
                break
            # print(w.worstTime)
        print("When d=", d, "f(n, m)=", a_min, " * n + ", b_min, " * m", "Minimum S =", minS.getS(), "K= ", minS.getK())
        print("the worst branch is (", branch, ")")
        # print("the worst branch number is (", w.getWorstIn(), ",", w.getWorstOut(), ")")
        # print("\\tau is", w.getWorstTime(), "\n")
        print("\\final is", tau, "\n")
        del minS
        return [Kai_min, b_min]


    if S == 0:
        S = minS.getS()
        list = countProcess(minS, Kai_i, b_i, k_i)
        Kai_i = list[0]
        b_i = list[1]
        print(Kai_i, b_i)
    elif minS.getS() != S:
        S = minS.getS()
        list = countProcess(minS, Kai_i, b_i, k_i)
        Kai_i = list[0]
        b_i = list[1]
        print(Kai_i, b_i)
