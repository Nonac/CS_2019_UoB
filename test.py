from sympy import *
import math
from worstCase import *

k_str = input("Please input d：")
k = float(k_str)
print("Please input A and B as potential function f(n, m) = a * n + b * m\n")
A_str = input("A = ")
A = float(A_str)
B_str = input("B = ")
B = float(B_str)

minS = minS(k)
minS.minS()

for d in range(math.ceil(minS.getK()), 6):
    w = worstCase(minS.getS(), d)
    list = w.genGraphList()
    w.worstCase(list, A, B)
    x = Symbol('x')
    print(solve([(x ** w.getWorstIn()) - (x ** (w.getWorstIn()-w.getWorstOut()))-1], [x]))
