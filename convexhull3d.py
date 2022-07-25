import csv
from mpl_toolkits.mplot3d.art3d import Poly3DCollection
import numpy as np
import matplotlib.pyplot as plt
from mpl_toolkits.mplot3d import axes3d
from matplotlib import cm

key="parca10"
 
csvData = []
with open("data/"+key+"_coordinates.txt", 'r') as csvFile:
    csvReader = csv.reader(csvFile, delimiter=' ')
    for csvRow in csvReader:
        csvData.append(csvRow)

# Get X, Y, Z
csvData = np.array(csvData)
csvData = csvData.astype(np.float)
X, Y, Z = csvData[:,0], csvData[:,1], csvData[:,2]

x,y,z = np.loadtxt("data/"+key+"_coordinates.txt", unpack=True)

fig = plt.figure()
ax = fig.add_subplot(111, projection='3d')
ax.plot_trisurf(X, Y, Z, color='white', edgecolors='blue', alpha=0.7)
verts = [list(zip(x, y, z))]
ax.add_collection3d(Poly3DCollection(verts)).set(alpha=0.7)
ax.scatter(X, Y, Z, c='black')
#ax.scatter(0,0,0)
plt.show()


