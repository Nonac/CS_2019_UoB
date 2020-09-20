# result of minimum S(k) in different k range
# minimum S(k) is 3  when k>= 1  and d(F)= 1
# minimum S(k) is 6  when k>= 2  and d(F)= 2
# minimum S(k) is 9  when k>= 12/5  and d(F)= 3
# minimum S(k) is 10  when k>= 5/2  and d(F)= 3
# minimum S(k) is 11  when k>= 8/3  and d(F)= 3
# minimum S(k) is 12  when k>= 3  and d(F)= 3
# minimum S(k) is 15  when k>= 16/5  and d(F)= 4
# minimum S(k) is 16  when k>= 24/7  and d(F)= 4
# minimum S(k) is 17  when k>= 7/2  and d(F)= 4
# minimum S(k) is 18  when k>= 18/5  and d(F)= 4
# minimum S(k) is 19  when k>= 15/4  and d(F)= 4
# minimum S(k) is 20  when k>= 4  and d(F)= 4
# minimum S(k) is 23  when k>= 120/29  and d(F)= 5
# minimum S(k) is 24  when k>= 30/7  and d(F)= 5
# minimum S(k) is 25  when k>= 40/9  and d(F)= 5
# minimum S(k) is 26  when k>= 9/2  and d(F)= 5
# minimum S(k) is 27  when k>= 32/7  and d(F)= 5
# minimum S(k) is 28  when k>= 14/3  and d(F)= 5
# minimum S(k) is 29  when k>= 24/5  and d(F)= 5
# minimum S(k) is 30  when k>= 5  and d(F)= 5

from fractions import Fraction

S = 0
for i in range(1000, 5000):
    from minS import minS

    d = Fraction(i, 1000)
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
