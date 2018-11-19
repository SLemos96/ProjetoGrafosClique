# Clique
## The Problem
A clique of size n is K_n. The clique number of a graph is the largest clique that is a subgraph. Design an algorithm to find the clique number of a graph (and output the clique). Analyze your algorithm. Prove your algorithm works.
## How to run it
1. Write an adjacency matrix into the ```graph.txt file```. Here's an example of a graph and its adjacency matrix:

  ![Example Graph](/documentation/screenshots/examplegraph.png?raw=true)

  So, your ```graph.txt``` input file would look like this for the above graph:

  ![Example Adjacency Matrix](/documentation/screenshots/examplematrix.png?raw=true)

2. Make sure that your ```graph.txt``` file is located one file directory above where you're running your code. Otherwise, the program will not be able to find it and you will receive an error message. If you're just running it from an IDE like Eclipse, you will be able to just edit the currently existing ```graph.txt``` file with your desired graph adjacency matrix. Several input file examples can be found in the ```clique/testInputs``` folder.
3. Run the program via the Main class. The solution will be printed out to the console.
4. The program will output the largest clique size and the nodes in the largest clique as a list of nodes. For example, the program will output a clique size of 4 and nodes 5, 6, 7, 8 for the above input graph. The outputted nodes correspond to the nodes in the adjacency matrix:

  ![Example Adjacency Matrix with Key](/documentation/screenshots/examplematrixwithkey.png?raw=true)

  Remember: Do not put the column and row headings in your ```graph.txt``` file.
