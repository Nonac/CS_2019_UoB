from fractions import Fraction
import numpy as np

UPPERBOARD = 3
LOWERBOARD = 0
ACCURACY = Fraction(1, 100000)


def calculationOfTau(x, a, b):
    r = np.power(x, -a) + np.power(x, -b) - 1
    return r


def binarySearchToSearchTau(low, up, a, b, subtraction):
    mid = (low + up) / 2
    r = calculationOfTau(mid, a, b)
    if r == 0:
        return mid
    elif up - low <= ACCURACY:
        if subtraction:
            return low
        else:
            return up
    elif subtraction and r < 0:
        return binarySearchToSearchTau(low, mid, a, b, subtraction)
    elif subtraction and r > 0:
        return binarySearchToSearchTau(mid, up, a, b, subtraction)
    elif (not subtraction) and r < 0:
        return binarySearchToSearchTau(mid, up, a, b, subtraction)
    elif (not subtraction) and r > 0:
        return binarySearchToSearchTau(low, mid, a, b, subtraction)


def sovleTau(a, b):
    up = UPPERBOARD
    low = LOWERBOARD
    if (a < 0 and b > 0) or (a > 0 and b < 0):
        return None
    elif a < 0 and b < 0:
        subtraction = False
    else:
        subtraction = True

    return binarySearchToSearchTau(low, up, a, b, subtraction)

