import math
from worstCase import *
from minS import minS

A = 0.3308
B = 0.2295
d = 3.501
minS = minS(d)
minS.minS()

def countProcess(minS):
    w = worstCase(minS.getS(), math.ceil(minS.getK()))
    w.worstCase(w.genGraphList(), A, B)
    print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
    print("Minimum S =", minS.getS())
    print("the worst branch number is (", w.getWorstIn(),
          ",", w.getWorstOut(), ")")
    print("worst time is", w.getWorstTime(), "\n")
    del minS


S = minS.getS()
countProcess(minS)

