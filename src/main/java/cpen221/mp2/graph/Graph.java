package cpen221.mp2.graph;

import java.util.*;

/**
 * Represents a graph with vertices of type V.
 *
 * @param <V> represents a vertex type
 */
public class Graph<V extends Vertex, E extends Edge<V>> implements ImGraph<V, E>, IGraph<V, E> {

    private Set<V> allVer = new HashSet<>();
    private Set<E> allEdg = new HashSet<>();
    /**
     * Add a vertex to the graph
     *
     * @param v vertex to add
     * @return true if the vertex was added successfully and false otherwise
     */
    @Override
    public boolean addVertex(V v) {
        if( this.allVer.contains(v)){
            return false;
        }else {
            return this.allVer.add(v);
        }

    }

    /**
     * Check if a vertex is part of the graph
     *
     * @param v vertex to check in the graph
     * @return true of v is part of the graph and false otherwise
     */
    @Override
    public boolean vertex(V v) {
        return this.allVer.contains(v);
    }

    /**
     * Add an edge of the graph
     *
     * @param e the edge to add to the graph
     * @return true if the edge was successfully added and false otherwise
     */
    @Override
    public boolean addEdge(E e) {
        if( this.allEdg.contains(e)){
            return false;
        }else {
            return this.allEdg.add(e);
        }
    }

    /**
     * Check if an edge is part of the graph
     *
     * @param e the edge to check in the graph
     * @return true if e is an edge in the graoh and false otherwise
     */
    @Override
    public boolean edge(E e) {
        return this.allEdg.contains(e);
    }

    /**
     * Check if v1-v2 is an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return true of the v1-v2 edge is part of the graph and false otherwise
     */
    @Override
    public boolean edge(V v1, V v2) {
        Edge<V> Ed=new Edge<>(v1,v2);
        return this.allEdg.contains(Ed);
    }

    /**
     * Determine the length on an edge in the graph
     *
     * @param v1 the first vertex of the edge
     * @param v2 the second vertex of the edge
     * @return the length of the v1-v2 edge if this edge is part of the graph
     */
    @Override
    public int edgeLength(V v1, V v2) {
        Edge<V> toEd=new Edge<>(v1,v2);

        for(E edge: this.allEdg){
            if(edge.equals(toEd)){
                return edge.length();
            }
        }
        throw new IllegalArgumentException("Edge is not in this graph");
    }

    /**
     * Obtain the sum of the lengths of all edges in the graph
     *
     * @return the sum of the lengths of all edges in the graph
     */
    @Override
    public int edgeLengthSum() {
        int sum=0;
        for(E edge: this.allEdg){
            sum+=edge.length();
        }
        return sum;

    }

    /**
     * Remove an edge from the graph
     *
     * @param e the edge to remove
     * @return true if e was successfully removed and false otherwise
     */
    @Override
    public boolean remove(E e) {
        if (this.allEdg.contains(e)){
            this.allEdg.remove(e);
            return true;
        }else {
            return false;
        }
    }

    /**
     * Remove a vertex from the graph
     *
     * @param v the vertex to remove
     * @return true if v was successfully removed and false otherwise
     */
    @Override
    public boolean remove(V v) {
        this.allVer.remove(v);
        Map<V,E> nei=new HashMap<>();
        nei=getNeighbours(v);
        for(Map.Entry<V,E>entry:nei.entrySet()){
            this.allEdg.remove(getEdge(entry.getKey(),v));
        }
        if(!this.allVer.contains(v)){
            return true;
        }
        return false;
    }

    /**
     * Obtain a set of all vertices in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return a set of all vertices in the graph
     */
    @Override
    public Set<V> allVertices() {
        Set<V> alv=new HashSet<>(this.allVer);
        return alv;
    }

    /**
     * Obtain a set of all vertices incident on v.
     * Access to this set **should not** permit graph mutations.
     *
     * @param v the vertex of interest
     * @return all edges incident on v
     */
    @Override
    public Set<E> allEdges(V v) {
        Set<E> vIn=new HashSet<>();
        for(E edge: this.allEdg){
            if(edge.v1().equals(v)||edge.v2().equals(v)){
                vIn.add(edge);
            }
        }
        return vIn;
    }

    /**
     * Obtain a set of all edges in the graph.
     * Access to this set **should not** permit graph mutations.
     *
     * @return all edges in the graph
     */
    @Override
    public Set<E> allEdges() {
        Set<E> ale=new HashSet<>(this.allEdg);
        return ale;
    }

    /**
     * Obtain all the neighbours of vertex v.
     * Access to this map **should not** permit graph mutations.
     *
     * @param v is the vertex whose neighbourhood we want.
     * @return a map containing each vertex w that neighbors v and the edge between v and w.
     */
    @Override
    public Map<V, E> getNeighbours(V v) {
        Map<V,E> nei=new HashMap<>();
        for(E e:this.allEdg){
            if(e.v1().equals(v)){
                nei.put(e.v2(),e);
            }
            else if(e.v2().equals(v)){
                nei.put(e.v1(),e);
            }
        }
        return nei;
    }

    /**
     * Compute the shortest path from source to sink
     *
     * @param source the start vertex
     * @param sink   the end vertex
     * @return the vertices, in order, on the shortest path from source to sink (both end points are part of the list)
     */
    @Override
    public List<V> shortestPath(V source, V sink) {

         if (source.equals(sink)){
             return List.of(source);
         }
         // index reference
        List<V> allVerReference= new ArrayList<>(this.allVer);
        int[] dist= new int[allVerReference.size()];
        //already selected

        List<V> selectVer= new ArrayList<>();
        List<V> visited=new ArrayList<>(this.allVer);
        List<V> previous=new ArrayList<>(this.allVer.size());
        for(V v: allVerReference){
            if(!v.equals(source)){
                dist[allVerReference.indexOf(v)]=Integer.MAX_VALUE;
            }
            previous.add(sink);

        }
        dist[allVerReference.indexOf(source)]=0;

        V vertex=source;
    visited.remove(source);
        while (visited.size()>0){
            Map<V,E> NeighborEdgeReference=getNeighbours(vertex);


            for(Map.Entry<V,E> entry: NeighborEdgeReference.entrySet()){
                if(dist[allVerReference.indexOf(vertex)]+entry.getValue().length()<dist[allVerReference.indexOf(entry.getKey())]){

                    dist[allVerReference.indexOf(entry.getKey())]=dist[allVerReference.indexOf(vertex)]+entry.getValue().length();
                    previous.set(allVerReference.indexOf((entry.getKey())),vertex);

                }


            }
            V te=vertex;
            int temp;
            int min=Integer.MAX_VALUE;
            for(V verNe: NeighborEdgeReference.keySet()){
                temp=dist[allVerReference.indexOf(verNe)];
                if(temp<min&&temp!=0&&visited.contains(verNe)){
                    min=temp;
                    vertex=verNe;
                }
            }
            if(vertex.equals(te)){
                break;
            }
            visited.remove(vertex);
        }
        if(!previous.get(allVerReference.indexOf(sink)).equals(sink)){
        selectVer.add(sink);
        V v=sink;
        while(!selectVer.contains(source)){
            V vne=previous.get(allVerReference.indexOf(v));
            selectVer.add(vne);
            v=vne;


        }
        List<V> result = new ArrayList<>(0);
//        result.add(selectVer.get(selectVer.size() - 1));
        for (int i = 1; i <= selectVer.size(); i++) {
            result.add(selectVer.get(selectVer.size() - i));
        }

        return result;
        }else {

            return new ArrayList<>();
        }

    }

    /**
     * Compute the minimum spanning tree of the graph.
     * See https://en.wikipedia.org/wiki/Minimum_spanning_tree
     *
     * @return a list of edges that forms a minimum spanning tree of the graph
     */
    @Override
    public List<E> minimumSpanningTree() {
        int min = Integer.MAX_VALUE;
        Set<E> copy = new HashSet<>();
        copy.addAll(this.allEdg);
        List<E> tree = new ArrayList<>();
        List<V> visited = new ArrayList<>();
        E first = null;
        if (this.allEdg.size() >= this.allVer.size() - 1) {
            while (visited.size() != this.allVer.size() + 2) {
                for (E e : copy) {
                    if (e.length() <= min) {
                        min = e.length();
                        first = e;
                    }
                }
                if (!visited.contains(first.v1()) || !visited.contains(first.v2())) {
                    tree.add(first);
                    if (!visited.contains(first.v1())) {
                        visited.add(first.v1());
                    }
                    if (!visited.contains(first.v2())) {
                        visited.add(first.v2());
                    }
                }
                copy.remove(first);
                min = Integer.MAX_VALUE;
            }
            return tree;
        } else {
            throw new IllegalArgumentException("some points are not connected");
        }
    }

    /**
     * Compute the length of a given path
     *
     * @param path indicates the vertices on the given path
     * @return the length of path
     */

    @Override
    public int pathLength(List<V> path) {
        int sum=0;
        if(path.size()!=0) {
            for (int i = 0; i < path.size() - 1; i++) {
                int len = 0;
                Edge<V> edg = new Edge<>(path.get(i), path.get(i + 1));
                for (E edge : this.allEdg) {
                    if (edge.equals(edg)) {
                        len = edge.length();
                        break;
                    }
                }
                sum += len;
            }
            return sum;
        }else {
            return Integer.MAX_VALUE;
        }
    }

    /**
     * Obtain all vertices w that are no more than a <em>path distance</em> of range from v.
     *
     * @param v     the vertex to start the search from.
     * @param range the radius of the search.
     * @return a set of vertices that are within range of v (this set does not contain v).
     */
    @Override
    public Set<V> search(V v, int range) {
        Set<V> search=new HashSet<>();
        for(V c:this.allVer){
            if(pathLength(shortestPath(v,c))<=range&&!c.equals(v)){
                search.add(c);
            }
        }
        return search;
    }

    /**
     * Compute the diameter of the graph.
     * <ul>
     * <li>The diameter of a graph is the length of the longest shortest path in the graph.</li>
     * <li>If a graph has multiple components then we will define the diameter
     * as the diameter of the largest component.</li>
     * </ul>
     *
     * @return the diameter of the graph.
     */
    @Override
    public int diameter() {
        int max=0;
        int length;
        List<List<V>> path=new ArrayList<>();
        for(V q: allVer){
            for(V e:allVer) {
                path.add(shortestPath(q,e));
            }
        }
        for(List<V> c: path){
            if(pathLength(c)==Integer.MAX_VALUE){
                continue;
            }
            length=pathLength(c);
            if(length>=max){
                max=length;
            }
        }
        return max;
    }

    /**
     * Find the edge that connects two vertices if such an edge exists.
     * This method should not permit graph mutations.
     *
     * @param v1 one end of the edge
     * @param v2 the other end of the edge
     * @return the edge connecting v1 and v2
     */
    @Override
    public E getEdge(V v1, V v2) {
        Edge<V> ed=new Edge<>(v1,v2);
        for(E edge: this.allEdg){
            if(edge.equals(ed)){
                return edge;
            }
        }
        throw new IllegalArgumentException("Edge is not in this graph");
    }
    //// add all new code above this line ////

    /**
     * This method removes some edges at random while preserving connectivity
     * <p>
     * DO NOT CHANGE THIS METHOD
     * </p>
     * <p>
     * You will need to implement allVertices() and allEdges(V v) for this
     * method to run correctly
     *</p>
     * <p><strong>requires:</strong> this graph is connected</p>
     *
     * @param rng random number generator to select edges at random
     */
    public void pruneRandomEdges(Random rng) {
        class VEPair {
            V v;
            E e;

            public VEPair(V v, E e) {
                this.v = v;
                this.e = e;
            }
        }
        /* Visited Nodes */
        Set<V> visited = new HashSet<>();
        /* Nodes to visit and the cpen221.mp2.graph.Edge used to reach them */
        Deque<VEPair> stack = new LinkedList<VEPair>();
        /* Edges that could be removed */
        ArrayList<E> candidates = new ArrayList<>();
        /* Edges that must be kept to maintain connectivity */
        Set<E> keep = new HashSet<>();

        V start = null;
        for (V v : this.allVertices()) {
            start = v;
            break;
        }
        if (start == null) {
            // nothing to do
            return;
        }
        stack.push(new VEPair(start, null));
        while (!stack.isEmpty()) {
            VEPair pair = stack.pop();
            if (visited.add(pair.v)) {
                keep.add(pair.e);
                for (E e : this.allEdges(pair.v)) {
                    stack.push(new VEPair(e.distinctVertex(pair.v), e));
                }
            } else if (!keep.contains(pair.e)) {
                candidates.add(pair.e);
            }
        }
        // randomly trim some candidate edges
        int iterations = rng.nextInt(candidates.size());
        for (int count = 0; count < iterations; ++count) {
            int end = candidates.size() - 1;
            int index = rng.nextInt(candidates.size());
            E trim = candidates.get(index);
            candidates.set(index, candidates.get(end));
            candidates.remove(end);
            remove(trim);
        }
    }


}
