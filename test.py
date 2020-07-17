import matplotlib.pyplot as plt
import networkx as nx

G = nx.Graph()
G.add_edges_from([(1, 2), (1, 3), (2, 4), (2, 5), (3, 6), (4, 8), (5, 8), (3, 7)])
nx.draw(G, with_labels=True, edge_color='b', node_color='g', node_size=1000)
plt.show()
