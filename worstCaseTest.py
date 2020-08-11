from sympy import *
import math
from worstCase import *
from minS import minS

k_str = input("Please input d：")
k = float(k_str)
print("Please input A and B as potential function f(n, m) = a * n + b * m")
A_str = input("A = ")
A = float(A_str)
B_str = input("B = ")
B = float(B_str)

minS = minS(k)
minS.minS()

for d in range(math.ceil(minS.getK()), 6):
    # Initialize the module, S for S(x), d for d(F)
    w = worstCase(minS.getS(), d)
    # Generate a list of all the graphs that match the S and d possibilities.
    list = w.genGraphList()
    # The worst-case in and out branches are computed for
    # each element in the graph list and compared,
    # keeping the minimum worst-case branch.
    w.worstCase(list, A, B)
    print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
    print("Minimum S is ", minS.getS())
    print("worst branch number is ", int(w.getWorstIn()), int(w.getWorstOut()))

    # Find the positive real solution to the equation
    # x = Symbol('x', real=True)
    # r = solve([(x ** w.getWorstIn()) - (x ** (w.getWorstIn() - w.getWorstOut())) - 1], [x])
    # for each in r:
    #     if each[0] > 0:
    #         print(each[0])
