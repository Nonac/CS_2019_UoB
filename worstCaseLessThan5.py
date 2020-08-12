# Combining code 1 and code 2

import math
from worstCase import *


print("Please input A and B as potential function f(n, m) = a * n + b * m")
A_str = input("A = ")
A = float(A_str)
B_str = input("B = ")
B = float(B_str)
S = 0
for i in range(1000, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    if S == 0:
        S = minS.getS()
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        list = w.genGraphList()
        w.worstCase(list, A, B)
        print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
        print("Minimum S =", minS.getS())
        print("worst branch number is (", int(w.getWorstIn()), int(w.getWorstOut()),")")
        del minS
    elif minS.getS() != S:
        S = minS.getS()
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        list = w.genGraphList()
        w.worstCase(list, A, B)
        print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
        print("Minimum S =", minS.getS())
        print("worst branch number is (", int(w.getWorstIn()), int(w.getWorstOut()), ")")
        del minS
