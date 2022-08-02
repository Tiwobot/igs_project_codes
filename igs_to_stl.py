import pyiges

key= "parca10";
# load an example impeller
iges = pyiges.read("igsdata/igsFiles/"+key+".igs")

# print an invidiual entity (boring)
print(iges[1])

# convert all lines to a vtk mesh and plot it
lines = iges.to_vtk(lines=True, bsplines=True, surfaces=False, points=True, delta=0.01, merge=True)
lines.plot(color='w', line_width=2)

# convert all surfaces to a vtk mesh and plot it
mesh = iges.to_vtk(bsplines=False, surfaces=True, merge=True, delta=0.05)
mesh.plot(color='w', smooth_shading=True)
# control resolution of the mesh by changing "delta"

# save this surface to file
#mesh.save('mesh.ply')  # as ply
#mesh.save('mesh.stl')  # as stl
#mesh.save('mesh.vtk')  # as vtk