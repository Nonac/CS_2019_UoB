# Combining code 1 and code 2
# Please input A and B as potential function f(n, m) = a * n + b * m
# A = -1
# B = 1
# When d= 1.0 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 3
# worst branch number is ( 2 2 )
# When d= 1.001 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 6
# worst branch number is ( 5 5 )
# When d= 2.001 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 9
# worst branch number is ( 8 8 )
# When d= 2.401 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 10
# worst branch number is ( 10 7 )
# When d= 2.501 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 11
# worst branch number is ( 12 7 )
# When d= 2.667 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 12
# worst branch number is ( 14 7 )
# When d= 3.001 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 15
# worst branch number is ( 17 10 )
# When d= 3.201 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 16
# worst branch number is ( 19 9 )
# When d= 3.429 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 17
# worst branch number is ( 21 9 )
# When d= 3.501 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 18
# worst branch number is ( 23 9 )
# When d= 3.601 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 19
# worst branch number is ( 25 8 )
# When d= 3.751 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 20
# worst branch number is ( 27 8 )
# When d= 4.001 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 23
# worst branch number is ( 30 11 )
# When d= 4.138 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 24
# worst branch number is ( 32 11 )
# When d= 4.286 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 25
# worst branch number is ( 34 10 )
# When d= 4.445 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 26
# worst branch number is ( 36 10 )
# When d= 4.501 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 27
# worst branch number is ( 38 10 )
# When d= 4.572 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 28
# worst branch number is ( 40 9 )
# When d= 4.667 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 29
# worst branch number is ( 42 9 )
# When d= 4.801 f(n, m)= -1.0  * n +  1.0  * m
# Minimum S = 30
# worst branch number is ( 0 0 )
import math
from worstCase import *


print("Please input A and B as potential function f(n, m) = a * n + b * m")
A_str = input("A = ")
A = float(A_str)
B_str = input("B = ")
B = float(B_str)
S = 0
for i in range(3001, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    if S == 0:
        S = minS.getS()
        print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
        print("Minimum S =", minS.getS())
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        list = w.genGraphList()
        w.worstCase(list, A, B)
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
