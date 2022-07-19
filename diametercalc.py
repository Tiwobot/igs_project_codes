import math
import sys

class Point:
	def __init__(self, x=None, y=None, z=None):
		self.x = x
		self.y = y
		self.z = z

	def __sub__(self, pointX):
		return	Point(self.x - pointX.x, self.y - pointX.y, self.z - pointX.z)

	def __add__(self, pointX):
		return	Point(self.x + pointX.x, self.y + pointX.y, self.z + pointX.z)		

	def length(self):
		return math.sqrt(self.x**2 + self.y**2 + self.z**2)

	def __str__(self):
		return str(self.x)+","+str(self.y)+","+str(self.z)

	def __hash__(self):
		return hash((self.x,self.y,self.z))

	def __eq__(self,other):
		return (self.x==other.x) and(self.y==other.y) and(self.z==other.z) 	



points = []

try:
	data = open("coordinates.dat", "r")
	num = int(data.readline())
	for line in data:
		a = list(map(float, line.split()))
		points.append(Point(a[0], a[1], a[2]))

	if num < 4:
		print ("Less than 4 points so 1D or 2D")
		sys.exit()

finally:
	data.close()	

