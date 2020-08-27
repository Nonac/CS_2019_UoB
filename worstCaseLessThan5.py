#
# Please input A and B as potential function f(n, m) = a * n + b * m
# A = 0.1961
# B = 0.2680
# When d= 2.001 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 9
# the worst branch number is ( 3 , 2 )
# worst time is 1.2414075421617707
# When d= 2.401 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 10
# the worst branch number is ( 4 , 3 )
# worst time is 1.2109386742105677
# When d= 2.501 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 11
# the worst branch number is ( 4 , 3 )
# worst time is 1.1963496565093807
# When d= 2.667 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 12
# the worst branch number is ( 4 , 1 )
# worst time is 1.254819606149398
# When d= 3.001 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 15
# the worst branch number is ( 6 , 4 )
# worst time is 1.140691466337118
# When d= 3.201 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 16
# the worst branch number is ( 6 , 3 )
# worst time is 1.1664113975120762
# When d= 3.429 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 17
# the worst branch number is ( 6 , 3 )
# worst time is 1.1584868836973228
# When d= 3.501 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 18
# the worst branch number is ( 7 , 2 )
# worst time is 1.1714414328865062
# When d= 3.601 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 19
# the worst branch number is ( 7 , 2 )
# worst time is 1.1639947315940835
# When d= 3.751 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 20
# the worst branch number is ( 9 , 2 )
# worst time is 1.151221871592978
# When d= 4.001 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 23
# the worst branch number is ( 10 , 2 )
# worst time is 1.1294262742212724
# When d= 4.138 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 24
# the worst branch number is ( 9 , 3 )
# worst time is 1.1211399845621461
# When d= 4.286 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 25
# the worst branch number is ( 10 , 3 )
# worst time is 1.1171573264690735
# When d= 4.445 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 26
# the worst branch number is ( 11 , 2 )
# worst time is 1.1177659794659625
# When d= 4.501 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 27
# the worst branch number is ( 12 , 2 )
# worst time is 1.1144204084780251
# When d= 4.572 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 28
# the worst branch number is ( 12 , 2 )
# worst time is 1.1112969039850182
# When d= 4.667 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 29
# the worst branch number is ( 13 , 2 )
# worst time is 1.1083725992192937
# When d= 4.801 f(n, m)= 0.1961  * n +  0.268  * m
# Minimum S = 30
# the worst branch number is ( 0 , 0 )
# worst time is 1.0
import math
from worstCase import *

print("Please input A and B as potential function f(n, m) = a * n + b * m")
A_str = input("A = ")
A = float(A_str)
B_str = input("B = ")
B = float(B_str)
S = 0
for i in range(2001, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    if S == 0:
        S = minS.getS()
        print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
        print("Minimum S =", minS.getS())
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        w.worstCase(w.genGraphList(), A, B)
        print("the worst branch number is (", int(w.getWorstIn()),
              ",", int(w.getWorstOut()), ")")
        print("worst time is", w.getWorstTime())
        del minS
    elif minS.getS() != S:
        S = minS.getS()
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        list = w.genGraphList()
        w.worstCase(list, A, B)
        print("When d=", d, "f(n, m)=", A, " * n + ", B, " * m")
        print("Minimum S =", minS.getS())
        print("the worst branch number is (", int(w.getWorstIn()),
              ",", int(w.getWorstOut()), ")")
        print("worst time is", w.getWorstTime())
        del minS
