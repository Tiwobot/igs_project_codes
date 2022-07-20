import sys
import numpy

from hull import Point


def get_circumsphere(S):

    U = S[1:] - S[0]
    B = numpy.sqrt(numpy.sum(U ** 2, axis=1))
    U /= B[:, None]
    B /= 2
    C = numpy.dot(numpy.linalg.solve(numpy.inner(U, U), B), U)
    r2 = numpy.sum(C ** 2)
    C += S[0]
    return C, r2


def get_bounding_ball(S, epsilon=1e-7, rng=numpy.random.default_rng()):

    def circle_contains(D, p):
        c, r2 = D
        return numpy.sum((p - c) ** 2) <= r2

    def get_boundary(R):
        if len(R) == 0:
            return numpy.zeros(S.shape[1]), 0.0

        if len(R) <= S.shape[1] + 1:
            return get_circumsphere(S[R])

        c, r2 = get_circumsphere(S[R[: S.shape[1] + 1]])
        if numpy.all(numpy.fabs(numpy.sum((S[R] - c) ** 2, axis=1) - r2) < epsilon):
            return c, r2

    class Node(object):
        def __init__(self, P, R):
            self.P = P
            self.R = R
            self.D = None
            self.pivot = None
            self.left = None
            self.right = None

    def traverse(node):
        stack = [node]
        while len(stack) > 0:
            node = stack.pop()

            if len(node.P) == 0 or len(node.R) >= S.shape[1] + 1:
                node.D = get_boundary(node.R)
            elif node.left is None:
                node.pivot = rng.choice(node.P)
                node.left = Node(list(set(node.P) - set([node.pivot])), node.R)
                stack.extend((node, node.left))
            elif node.right is None:
                if circle_contains(node.left.D, S[node.pivot]):
                    node.D = node.left.D
                else:
                    node.right = Node(node.left.P, node.R + [node.pivot])
                    stack.extend((node, node.right))
            else:
                node.D = node.right.D
                node.left, node.right = None, None

    S = S.astype(float, copy=False)
    root = Node(range(S.shape[0]), [])
    traverse(root)
    return root.D


a = []
coordinatesinput = open('coordinates.txt', 'r')
Lines = coordinatesinput.readlines()
for line in Lines:
    a = float(line.split(" ")[0]), float(
        line.split(" ")[1]), float(line.split(" ")[2])

S = numpy.random.randn(5, 3)
C, r2 = get_bounding_ball(S)
print(a)
