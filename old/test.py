# from scipy.optimize import fsolve
#
a = 4
b = 4
#
#
# def func1(i):
#     x = i[0]
#     if b > 0 and a >= b:
#         return [(x ** a) - (x ** (a - b)) - 1]
#     elif a > 0 and b >= a:
#         return [(x ** b) - (x ** (b - a)) - 1]
#     elif b <= a and a < 0:
#         return [1 - (x ** (- b) - (x ** (-a)))]
#     elif a < 0 and b > 0:
#         return [(x ** b) - (x ** (b - a)) - 1]
#     elif a <= b and b < 0:
#         return [1 - (x ** (- b) - (x ** (-a)))]
#
#
# r1 = fsolve(func1, [1])[0]
# print(r1)

# from sympy import *
#
# def function(a, b):
#     x = Symbol('x', real=True, nonnegative=True)
#     if b > 0 and a >= b:
#         return [(x ** a) - (x ** (a - b)) - 1]
#     elif a > 0 and b >= a:
#         return [(x ** b) - (x ** (b - a)) - 1]
#     elif b <= a and a < 0:
#         return [1 - (x ** (- b) - (x ** (-a)))]
#     elif a < 0 and b > 0:
#         return [(x ** b) - (x ** (b - a)) - 1]
#     elif a <= b and b < 0:
#         return [1 - (x ** (- b) - (x ** (-a)))]
#
#
# def sovleTau(a, b):
#     x = Symbol('x', real=True, nonnegative=True)
#     f = function(a, b)
#     r = solve(f, x, rational=True,dict=True)
#     if len(r)!=0:
#         return r[0][x]
#     else:
#         return []
#
# print(sovleTau(a,b))