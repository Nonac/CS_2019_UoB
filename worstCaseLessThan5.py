# When d= 3.001 f(n, m)= 0.1961  * n +  0.268  * m Minimum S = 15
# the worst branch is ( [2, 10, 5, 18] )
# the worst branch number is ( 5.8045 , 3.0722 )
# \tau is 1.1754138473615692
#
# When d= 3.201 f(n, m)= 0.1961  * n +  0.268  * m Minimum S = 16
# the worst branch is ( [1, 8, 5, 20] )
# the worst branch number is ( 6.3405000000000005 , 2.3401 )
# \tau is 1.1891856117367177
#
# When d= 3.429 f(n, m)= 0.1961  * n +  0.268  * m Minimum S = 17
# the worst branch is ( [1, 8, 5, 20] )
# the worst branch number is ( 6.3405000000000005 , 2.3401 )
# \tau is 1.1891856117367177
#
# When d= 3.501 f(n, m)= 0.3308  * n +  0.2295  * m Minimum S = 18
# the worst branch is ( [1, 8, 5, 22] )
# the worst branch number is ( 6.703 , 2.1668000000000003 )
# \tau is 1.189195278439272
#
# When d= 3.601 f(n, m)= 0.3308  * n +  0.2295  * m Minimum S = 19
# the worst branch is ( [1, 8, 5, 22] )
# the worst branch number is ( 6.703 , 2.1668000000000003 )
# \tau is 1.189195278439272
#
# When d= 3.751 f(n, m)= 0.4461  * n +  0.1987  * m Minimum S = 20
# the worst branch is ( [1, 8, 5, 24] )
# the worst branch number is ( 6.9993 , 2.0357 )
# \tau is 1.1892354698194136
#
# When d= 4.001 f(n, m)= 0.8755  * n +  0.0914  * m Minimum S = 23
# the worst branch is ( [1, 10, 6, 26] )
# the worst branch number is ( 7.6294 , 1.7894999999999999 )
# \tau is 1.189192112943713
#
# When d= 4.138 f(n, m)= 0.9139  * n +  0.0821  * m Minimum S = 24
# the worst branch is ( [1, 10, 6, 28] )
# the worst branch number is ( 7.7822000000000005 , 1.7349 )
# \tau is 1.1891939959022877
#
# When d= 4.286 f(n, m)= 0.9139  * n +  0.0821  * m Minimum S = 25
# the worst branch is ( [1, 10, 6, 28] )
# the worst branch number is ( 7.7822000000000005 , 1.7349 )
# \tau is 1.1891939959022877
#
# When d= 4.445 f(n, m)= 0.9517  * n +  0.0736  * m Minimum S = 26
# the worst branch is ( [1, 10, 6, 30] )
# the worst branch number is ( 7.918200000000001 , 1.6877 )
# \tau is 1.1892060029243312
#
# When d= 4.501 f(n, m)= 0.9517  * n +  0.0736  * m Minimum S = 27
# the worst branch is ( [1, 10, 6, 30] )
# the worst branch number is ( 7.918200000000001 , 1.6877 )
# \tau is 1.1892060029243312
#
# When d= 4.572 f(n, m)= 0.9841  * n +  0.0665  * m Minimum S = 28
# the worst branch is ( [1, 10, 6, 32] )
# the worst branch number is ( 8.0326 , 1.6491 )
# \tau is 1.1892178401235916
#
# When d= 4.667 f(n, m)= 0.9841  * n +  0.0665  * m Minimum S = 29
# the worst branch is ( [1, 10, 6, 32] )
# the worst branch number is ( 8.0326 , 1.6491 )
# \tau is 1.1892178401235916
#
# When d= 4.801 f(n, m)= 1.0143  * n +  0.0602  * m Minimum S = 30
# the worst branch is ( [1, 10, 6, 34] )
# the worst branch number is ( 8.1326 , 1.6162999999999998 )
# \tau is 1.189222576940108


import math
from worstCase import *

k = [3,3.5, 3.75, 4, 4 + 4 / 29, 4 + 4 / 9, 4 + 4 / 7, 4.8, 5]
A = [1,0.1961, 0.3308, 0.4461, 0.8755, 0.9139, 0.9517, 0.9841, 1.0143]
B = [-2,0.2680, 0.2295, 0.1987, 0.0914, 0.0821, 0.0736, 0.0665, 0.0602]
S = 0
cnt = 0
for i in range(2001, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    while d > k[cnt]:
        cnt += 1


    def countProcess(minS):
        w = worstCase(minS.getS(), math.ceil(minS.getK()))
        w.worstCase(w.genGraphList(), A[cnt], B[cnt])
        print("When d=", d, "f(n, m)=", A[cnt], " * n + ", B[cnt], " * m","Minimum S =", minS.getS())
        print("the worst branch is (", w.getBranchCheck(), ")")
        print("the worst branch number is (", w.getWorstIn(),",", w.getWorstOut(), ")")
        print("\\tau is", w.getWorstTime(), "\n")
        del minS


    if S == 0:
        S = minS.getS()
        countProcess(minS)
    elif minS.getS() != S:
        S = minS.getS()
        countProcess(minS)
