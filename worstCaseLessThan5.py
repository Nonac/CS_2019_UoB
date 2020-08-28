# When d= 2.001 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 9
# the worst branch number is ( 3.6182 , 2.4976000000000003 )
# worst time is 1.257804059359566
#
# When d= 2.401 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 10
# the worst branch number is ( 3.6182 , 3.2874 )
# worst time is 1.22251373499618
#
# When d= 2.501 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 11
# the worst branch number is ( 4.0771999999999995 , 3.2874 )
# worst time is 1.2080327417290537
#
# When d= 2.667 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 12
# the worst branch number is ( 4.0771999999999995 , 1.7078 )
# worst time is 1.2907221675250096
#
# When d= 3.001 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 15
# the worst branch number is ( 5.3260000000000005 , 4.5362 )
# worst time is 1.151286616353966
#
# When d= 3.201 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 16
# the worst branch number is ( 5.3260000000000005 , 2.9566 )
# worst time is 1.1881228889925255
#
# When d= 3.429 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 17
# the worst branch number is ( 5.785 , 2.9566 )
# worst time is 1.1790860291433938
#
# When d= 3.501 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 18
# the worst branch number is ( 6.703 , 2.1668000000000003 )
# worst time is 1.189195278439272
#
# When d= 3.601 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 19
# the worst branch number is ( 6.703 , 2.1668000000000003 )
# worst time is 1.189195278439272
#
# When d= 3.751 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 20
# the worst branch number is ( 7.162 , 2.1668000000000003 )
# worst time is 1.181358455995635
#
# When d= 4.001 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 23
# the worst branch number is ( 7.4928 , 2.6258 )
# worst time is 1.161681006250839
#
# When d= 4.138 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 24
# the worst branch number is ( 7.4928 , 2.6258 )
# worst time is 1.161681006250839
#
# When d= 4.286 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 25
# the worst branch number is ( 7.9518 , 3.4156 )
# worst time is 1.1381770991643323
#
# When d= 4.445 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 26
# the worst branch number is ( 7.9518 , 2.6258 )
# worst time is 1.1557818581370585
#
# When d= 4.501 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 27
# the worst branch number is ( 8.869800000000001 , 2.6258 )
# worst time is 1.1454273528787546
#
# When d= 4.572 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 28
# the worst branch number is ( 12.5418 , 2.6258 )
# worst time is 1.1164553933080918
#
# When d= 4.667 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 29
# the worst branch number is ( 13.0008 , 2.6258 )
# worst time is 1.1137614062966699
#
# When d= 4.801 f(n, m)= 0.3308  * n +  0.2295  * m
# Minimum S = 30
# the worst branch number is ( 0.0 , 0.0 )
# worst time is 1.0
import math
from worstCase import *

# print("Please input A and B as potential function f(n, m) = a * n + b * m")
# A_str = input("A = ")
# A = float(A_str)
# B_str = input("B = ")
# B = float(B_str)
k = [3.5, 3.75, 4, 4 + 4 / 29, 4 + 4 / 9, 4 + 4 / 7, 4.8, 5]
A = [0.1961, 0.3308, 0.4461, 0.8755, 0.9139, 0.9517, 0.9841, 1.0143]
B = [0.2680, 0.2295, 0.1987, 0.0914, 0.0821, 0.0736, 0.0665, 0.0602]
S = 0
cnt = 0
for i in range(3000, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    while d > k[cnt]:
        cnt += 1



    def countProcess(minS):
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        w.worstCase(w.genGraphList(), A[cnt], B[cnt])
        print("When d=", d, "f(n, m)=", A[cnt], " * n + ", B[cnt], " * m")
        print("Minimum S =", minS.getS())
        print("the worst branch number is (", w.getBranchCheck(), ")")
        print("the worst branch number is (", w.getWorstIn(),
              ",", w.getWorstOut(), ")")
        print("worst time is", w.getWorstTime(), "\n")
        del minS


    if S == 0:
        S = minS.getS()
        countProcess(minS)
    elif minS.getS() != S:
        S = minS.getS()
        if S==15:
            print(1)
        countProcess(minS)
