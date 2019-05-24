import numpy as np
array = np.zeros((5, 5))
point0 = (0, 0)
array[:]
array[point0] = 5
point = (0, 0)
dir = (0, 0)
xp = [0, 1, 1, 1, 0, -1, -1, -1]
yp = [1, 1, 0, -1, -1, -1, 0, 1]
power = 5
for x in range(8):
    power = 5
    point = point0 + (-1, -1)
    for y in range(5):
        try:
            #array[point + (xp[x], yp[x])] = power
            point = point + (xp[x], yp[x])
        except Exception as e:
            print("over!")
        print(str(point))
print(array)
