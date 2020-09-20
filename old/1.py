from time import *
from handythread import *
from multiprocessing.pool import Pool
import numpy as np
import math

def f(x):
    # print(x)
    y = [1]*1000000036
    [math.exp(i) for i in y]
def g(x):
    # print(x)
    y = np.ones(10000000)
    np.exp(y)


def fornorm(f, l):
    for i in l:
        f(i)


result = {}
start_time = time.time()
fornorm(g,range(100))
end_time = time.time()
result['norm g 100'] = end_time - start_time

start_time = time.time()
fornorm(f,range(10))
end_time = time.time()
result['norm f 10'] = end_time - start_time

start_time = time.time()
foreach(g,range(100),threads=2)
end_time = time.time()
result['2 threads g 100'] = end_time - start_time

start_time =time.time()
foreach(f,range(10),threads=2)
end_time = time.time()
result['2 threads f 10'] = end_time - start_time


p = Pool(2)
start_time = time.time()
p.map(g,range(100))
end_time = time.time()
result['2 processes g 100'] = end_time - start_time

start_time = time.time()
p.map(f,range(10))
end_time = time.time()
result['2 processes f 10'] = end_time - start_time

print(result)