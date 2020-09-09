import matplotlib.pyplot as plt
import networkx as nx

nxG = nx.Graph()

for i in range(5):
    nxG.add_node(i)

for i in range(4):
    nxG.add_edge(0,i+1)

nxG.add_edge(1,2)
nxG.add_edge(2,3)

nx.draw(nxG,  with_labels=True, edge_color='b', node_color='g',
                node_size=1000)
plt.show()