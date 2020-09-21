# S(x) k_i a_i b_i Kai_i Running Time
# 9 2 -8191700003/4096000000 3276700001/3276800000 2/5 O( 1.0717734488458104 ^n)
# 10 12/5 -20829419387/16384000000 2321709551/3276800000 16383900001/32768000000 O( 1.0905071385665828 ^n)
# 11 5/2k_i  -22060073747/16384000000 603502157/819200000 30380123879/49152000000 O( 1.1130522141175423 ^n)
# 12 8/3 -14737146977/16384000000 192581351/327680000 14150055673/16384000000 O( 1.1614384289590107 ^n)
# 15 3 2885076799/16384000000 206106131/819200000 16075869183/16384000000 O( 1.1853377932145008 ^n)
# 16 16/5 -3984220607/114688000000 1025922509/3276800000 17018068639/16384000000 O( 1.1972090351292717 ^n)
# 17 24/7 -3984220607/114688000000 1025922509/3276800000 243382573491/229376000000 O( 1.201857544804154 ^n)
# 18 7/2 16364575903/114688000000 216106031/819200000 125282015527/114688000000 O( 1.2083957966615932 ^n)
# 19 18/5 16364575903/114688000000 216106031/819200000 64910121089/57344000000 O( 1.216710229427214 ^n)
# 20 15/4 35320386343/114688000000 364512739/1638400000 137383953263/114688000000 O( 1.2306949403182312 ^n)
# 23 4 2652614920547/3325952000000 68265871/655360000 4086198211547/3325952000000 O( 1.2372567827172938 ^n)
# 24 120/29 44046886823/51968000000 24246379/262144000 4137397614797/3325952000000 O( 1.240561646297216 ^n)
# 25 30/7 44046886823/51968000000 24246379/262144000 9419011038137/7483392000000 O( 1.2437217600526944 ^n)
# 26 40/9 2967899767667/3325952000000 540960127/6553600000 16813269830813/13303808000000 O( 1.2448297005925588 ^n)
# 27 9/2 424018823621/475136000000 16901879/204800000 4222927262307/3325952000000 O( 1.246102190944808 ^n)
# 28 32/7 221330401933/237568000000 485760679/6553600000 1821029500783/1425408000000 O( 1.247800554168467 ^n)
# 29 14/3 221330401933/237568000000 485760679/6553600000 305852760079/237568000000 O( 1.2499393220727564 ^n)
# 30 24/5 922223738707/950272000000 108715297/1638400000 1237498100007/950272000000 O( 1.2531543494790474 ^n)


import math
from worstCase import *
import sovleTau

Kai_i = 0 * math.log(sovleTau.sovleTau(4, 4), sovleTau.sovleTau(2, 2))
b_i = 1 * math.log(sovleTau.sovleTau(4, 4), sovleTau.sovleTau(2, 2))
k_i = 2
S = 0
PreDefinedTau = sovleTau.sovleTau(2, 2)
ACCURACY = Fraction(1, 100000)

for i in range(2001, 5000):
    from minS import minS

    d = i / 1000
    minS = minS(d)
    minS.minS()


    def countProcess(minS, Kai_p, b_p, k_p):
        Kai = Kai_p + (minS.getK() - k_p) * b_p

        def binarySearchToSearchBi(low, up):
            w = worstCase(minS.getS(), math.ceil(minS.getK()))
            b_mid = (low + up) / 2
            a_mid = Kai - (minS.getK() * b_mid)
            w.worstCase(w.genGraphList(), a_mid, b_mid)
            branch = w.getBranchCheck()
            if w.getWorstTime() == PreDefinedTau:
                return {"Kai": Kai, "b": b_mid, "a": a_mid, "branch": branch, "tau": w.getWorstTime(), "k": minS.getK()}
            elif (up - low) < ACCURACY:
                return {"Kai": Kai, "b": low, "a": a_mid, "branch": branch, "tau": w.getWorstTime(), "k": minS.getK()}
            elif w.getWorstTime() > PreDefinedTau:
                return binarySearchToSearchBi(b_mid, up)
            elif w.getWorstTime() < PreDefinedTau:
                return binarySearchToSearchBi(low, b_mid)

        res = binarySearchToSearchBi(ACCURACY, 1)
        del minS
        return res


    if S == 0:
        S = minS.getS()
        res = countProcess(minS, Kai_i, b_i, k_i)
        Kai_i = res["Kai"]
        b_i = res["b"]
        k_i = res["k"]

        print("S(x)", "k_i", "a_i", "b_i", "Kai_i", "Running Time")
        print(S, "&", k_i, "&", res["a"], "&", b_i, "&", Kai_i, "&", "$O\\left(", np.power(res["tau"], Kai_i),
              "^n\\right)$\\\\  \\hline")

    elif minS.getS() != S:
        S = minS.getS()
        res = countProcess(minS, Kai_i, b_i, k_i)
        Kai_i = res["Kai"]
        b_i = res["b"]
        k_i = res["k"]
        print(S, "&", k_i, "&", res["a"], "&", b_i, "&", Kai_i, "&", "$O\\left(", np.power(res["tau"], Kai_i),
              "^n\\right)$\\\\  \\hline")
