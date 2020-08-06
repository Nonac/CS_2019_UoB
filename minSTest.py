# result of minimum S(k) in different k range
# minimum S(k) is 3  when k>= 1.0  and d(F)= 1
# minimum S(k) is 6  when k>= 2.0  and d(F)= 2
# minimum S(k) is 9  when k>= 2.4  and d(F)= 3
# minimum S(k) is 10  when k>= 2.5  and d(F)= 3
# minimum S(k) is 11  when k>= 2.6666666666666665  and d(F)= 3
# minimum S(k) is 12  when k>= 3.0  and d(F)= 3
# minimum S(k) is 15  when k>= 3.2  and d(F)= 4
# minimum S(k) is 16  when k>= 3.230769230769231  and d(F)= 4
# minimum S(k) is 17  when k>= 3.5000000000000004  and d(F)= 4
# minimum S(k) is 18  when k>= 3.6000000000000005  and d(F)= 4
# minimum S(k) is 19  when k>= 3.75  and d(F)= 4
# minimum S(k) is 20  when k>= 4.0  and d(F)= 4
# minimum S(k) is 23  when k>= 4.137931034482758  and d(F)= 5
# minimum S(k) is 24  when k>= 4.153846153846154  and d(F)= 5
# minimum S(k) is 25  when k>= 4.319999999999999  and d(F)= 5
# minimum S(k) is 26  when k>= 4.5  and d(F)= 5
# minimum S(k) is 27  when k>= 4.571428571428571  and d(F)= 5
# minimum S(k) is 28  when k>= 4.666666666666667  and d(F)= 5
# minimum S(k) is 29  when k>= 4.8  and d(F)= 5
# minimum S(k) is 30  when k>= 5.0  and d(F)= 5

S = 0
for i in range(0, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()
    if S == 0:
        S = minS.getS()
        print("minimum S(k) is", minS.getS(), " when k>=", minS.getK(), " and d(F)="
              , minS.getD())
        del minS
    elif minS.getS() != S:
        S = minS.getS()
        print("minimum S(k) is", minS.getS(), " when k>=", minS.getK(), " and d(F)="
              , minS.getD())
        del minS
