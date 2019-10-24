package cpen221.mp2;

import cpen221.mp2.graph.Edge;
import cpen221.mp2.graph.Graph;
//import cpen221.mp2.graph.Node;
import cpen221.mp2.graph.Vertex;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

public class GraphTest {

    @Test
    public void testCreateGraph() {
        Vertex v1 = new Vertex(1, "A");
        Vertex v2 = new Vertex(2, "B");
        Vertex v3 = new Vertex(3, "C");
        Vertex v4 = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(v1, v2, 5);
        Edge<Vertex> e2 = new Edge<>(v2, v3, 7);
        Edge<Vertex> e3 = new Edge<>(v1, v4, 9);

        Graph<Vertex, Edge<Vertex>> g = new Graph<>();
        g.addVertex(v1);
        g.addVertex(v2);
        g.addVertex(v3);
        g.addVertex(v4);
        g.addEdge(e1);
        g.addEdge(e2);
        g.addEdge(e3);

        assertEquals(e2, g.getEdge(v2, v3));
        assertEquals(21, g.pathLength(g.shortestPath(v3, v4)));
    }

    @Test
    public void testCreateGraph1() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Vertex e = new Vertex(5, "E");
        Vertex f = new Vertex(6, "F");
        Vertex g = new Vertex(7, "G");

        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(a, c, 3);
        Edge<Vertex> e3 = new Edge<>(a, e, 7);
        Edge<Vertex> e4 = new Edge<>(b, c, 6);
        Edge<Vertex> e5 = new Edge<>(c, e, 8);
        Edge<Vertex> e6 = new Edge<>(c, d, 11);
        Edge<Vertex> e7 = new Edge<>(e, d, 2);
        Edge<Vertex> e8 = new Edge<>(b, d, 5);
        Edge<Vertex> e9 = new Edge<>(e, g, 5);
        Edge<Vertex> e10 = new Edge<>(d, g, 10);
        Edge<Vertex> e11 = new Edge<>(d, f, 2);
        Edge<Vertex> e12 = new Edge<>(g, f, 3);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addVertex(e); gr.addVertex(f); gr.addVertex(g);
        gr.addEdge(e1); gr.addEdge(e2); gr.addEdge(e3);
        gr.addEdge(e4); gr.addEdge(e5); gr.addEdge(e6);
        gr.addEdge(e7); gr.addEdge(e8); gr.addEdge(e9);
        gr.addEdge(e10); gr.addEdge(e11); gr.addEdge(e12);

        assertEquals(e2, gr.getEdge(a, c));
        List<Vertex> path = new ArrayList<>(0);
        path.add(a); path.add(b); path.add(d); path.add(f);
        /*Test shortest path generation*/
        Assert.assertEquals(path, gr.shortestPath(a, f));
        /*Test the length*/
        Assert.assertEquals(11, gr.pathLength(gr.shortestPath(a, f)));
        Assert.assertEquals(66, gr.edgeLengthSum());
    }

    /*Test that add vertex returns false if the new vertex already exists in the graph*/
    @Test
    public void addVertexTest() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(1, "A");
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a);
        Assert.assertFalse(gr.addVertex(b));
    }

    /*Test that add edge returns false if the new edge already exists in the graph*/
    @Test
    public void addEdgeTest() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(a, b, 1);
        Edge<Vertex> e2 = new Edge<>(b, a, 2);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b);
        gr.addEdge(e1);
        Assert.assertFalse(gr.addEdge(e2));
    }

    /*Test the edge boolean function*/
    @Test
    public void edgeBooleanTest() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(a, b, 1);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b);
        gr.addEdge(e1);
        Assert.assertTrue(gr.edge(e1));
        Assert.assertTrue(gr.edge(a, b));
        Assert.assertTrue(gr.edge(b, a));
    }

    /*Test the remove function*/
    @Test
    public void testRemove() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Edge<Vertex> e1 = new Edge<>(a, b, 1);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b);
        gr.addEdge(e1);
        Assert.assertTrue(gr.remove(e1));
        Set<Edge> e = new HashSet<>(0);
        Assert.assertEquals(e, gr.allEdges());
    }

    /*Test the remove function*/
    @Test
    public void testRemove1() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Edge<Vertex> e1 = new Edge<>(a, b, 1);
        Edge<Vertex> e2 = new Edge<>(b, c, 1);
        Edge<Vertex> e3 = new Edge<>(a, c, 1);

        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c);
        gr.addEdge(e1); gr.addEdge(e2); gr.addEdge(e3);

        Set<Edge> e = new HashSet<>(0);
        e.add(e1); e.add(e2); e.add(e3);
        Assert.assertEquals(e, gr.allEdges());

        Assert.assertTrue(gr.remove(b));
        e.remove(e1); e.remove(e2);
        Assert.assertEquals(e, gr.allEdges());

        Assert.assertTrue(gr.remove(e3));
        e.remove(e3);
        Assert.assertEquals(e, gr.allEdges());
    }

    @Test
    public void testAllEVertices() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c);

        Set<Vertex> v = new HashSet<>(0);
        v.add(a); v.add(b); v.add(c);

        Assert.assertEquals(v, gr.allVertices());

        gr.remove(a);
        v.remove(a);
        Assert.assertEquals(v, gr.allVertices());
    }

    @Test
    public void testUnmodifiable() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c);

//        gr.allVertices().add(d);
        Set<Vertex> l = gr.allVertices();

        l.add(d);
        assertNotEquals(l,gr.allVertices());
//        assertEquals();
    }

    /*Test path length for a single point*/
    @Test
    public void testPathLength() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c);

        Assert.assertEquals(0, gr.pathLength(gr.shortestPath(a, a)));
    }

    /*Test search function*/
    @Test
    public void testSearch() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Vertex e = new Vertex(5, "E");
        Vertex f = new Vertex(6, "F");
        Vertex g = new Vertex(7, "G");

        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(a, c, 3);
        Edge<Vertex> e3 = new Edge<>(a, e, 7);
        Edge<Vertex> e4 = new Edge<>(b, c, 6);
        Edge<Vertex> e5 = new Edge<>(c, e, 8);
        Edge<Vertex> e6 = new Edge<>(c, d, 11);
        Edge<Vertex> e7 = new Edge<>(e, d, 2);
        Edge<Vertex> e8 = new Edge<>(b, d, 5);
        Edge<Vertex> e9 = new Edge<>(e, g, 5);
        Edge<Vertex> e10 = new Edge<>(d, g, 10);
        Edge<Vertex> e11 = new Edge<>(d, f, 2);
        Edge<Vertex> e12 = new Edge<>(g, f, 3);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addVertex(e); gr.addVertex(f); gr.addVertex(g);
        gr.addEdge(e1); gr.addEdge(e2); gr.addEdge(e3); gr.addEdge(e4);
        gr.addEdge(e5); gr.addEdge(e6); gr.addEdge(e7); gr.addEdge(e8);
        gr.addEdge(e9); gr.addEdge(e10); gr.addEdge(e11); gr.addEdge(e12);

        Set<Vertex> v = new HashSet<>(0);
        v.add(b); v.add(c); v.add(d); v.add(e);

        Assert.assertEquals(v, gr.search(a, 9));

        Set<Vertex> v1 = new HashSet<>(0);
        Assert.assertEquals(v1, gr.search(c, 1));
    }

    /*Test shortest path for disconnected graph*/
    @Test
    public void testShortestPath() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");

        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(c, d, 3);

        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addEdge(e1); gr.addEdge(e2);

        Assert.assertEquals(Integer.MAX_VALUE, gr.pathLength(gr.shortestPath(a, d)));
        Assert.assertEquals(0, gr.pathLength(gr.shortestPath(a, a)));

        Edge<Vertex> e3 = new Edge<>(a, c, 10);
        Edge<Vertex> e4 = new Edge<>(b, c, 1);
        gr.addEdge(e3); gr.addEdge(e4);
        Assert.assertEquals(5, gr.pathLength(gr.shortestPath(a, c)));
    }

    /*Test diameter for a well-connected graph*/
    @Test
    public void testDiameter() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Vertex e = new Vertex(5, "E");
        Vertex f = new Vertex(6, "F");
        Vertex g = new Vertex(7, "G");

        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(a, c, 3);
        Edge<Vertex> e3 = new Edge<>(a, e, 7);
        Edge<Vertex> e4 = new Edge<>(b, c, 6);
        Edge<Vertex> e5 = new Edge<>(c, e, 8);
        Edge<Vertex> e6 = new Edge<>(c, d, 11);
        Edge<Vertex> e7 = new Edge<>(e, d, 2);
        Edge<Vertex> e8 = new Edge<>(b, d, 5);
        Edge<Vertex> e9 = new Edge<>(e, g, 5);
        Edge<Vertex> e10 = new Edge<>(d, g, 10);
        Edge<Vertex> e11 = new Edge<>(d, f, 2);
        Edge<Vertex> e12 = new Edge<>(g, f, 3);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addVertex(e); gr.addVertex(f); gr.addVertex(g);
        gr.addEdge(e1); gr.addEdge(e2); gr.addEdge(e3); gr.addEdge(e4);
        gr.addEdge(e5); gr.addEdge(e6); gr.addEdge(e7); gr.addEdge(e8);
        gr.addEdge(e9); gr.addEdge(e10); gr.addEdge(e11); gr.addEdge(e12);

        Assert.assertEquals(13, gr.diameter()); //C to G
    }

    /*Test diameter for a disconnected graph*/
    @Test
    public void testDiameter1() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(c, d, 7);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addEdge(e1); gr.addEdge(e2);
        Assert.assertEquals(7, gr.diameter()); //C to D
    }


    /*Test disconnected graph for minimum spanning tree*/
    @Test(expected = IllegalArgumentException.class)
    public void testMST() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Edge<Vertex> e1 = new Edge<>(a, b, 4);
        Edge<Vertex> e2 = new Edge<>(c, d, 7);
        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addEdge(e1); gr.addEdge(e2);
        gr.minimumSpanningTree();
    }

    /*Test minimum spanning tree*/
    @Test
    public void testMST1() {
        Vertex a = new Vertex(1, "A");
        Vertex b = new Vertex(2, "B");
        Vertex c = new Vertex(3, "C");
        Vertex d = new Vertex(4, "D");
        Vertex e = new Vertex(5, "E");
        Vertex f = new Vertex(6, "F");
        Edge<Vertex> ab = new Edge<>(a, b, 1);
        Edge<Vertex> ad = new Edge<>(a, d, 4);
        Edge<Vertex> be = new Edge<>(b, e, 2);
        Edge<Vertex> de = new Edge<>(d, e, 4);
        Edge<Vertex> ae = new Edge<>(a, e, 3);
        Edge<Vertex> bd = new Edge<>(b, d, 5);
        Edge<Vertex> ce = new Edge<>(c, e, 4);
        Edge<Vertex> cf = new Edge<>(c, f, 5);
        Edge<Vertex> ef = new Edge<>(e, f, 7);


        Graph<Vertex, Edge<Vertex>> gr = new Graph<>();
        gr.addVertex(a); gr.addVertex(b); gr.addVertex(c); gr.addVertex(d);
        gr.addEdge(ab); gr.addEdge(ad); gr.addEdge(be); gr.addEdge(de);
        gr.addEdge(ae); gr.addEdge(bd); gr.addEdge(ce); gr.addEdge(cf);
        gr.addEdge(ef);

        List<Edge<Vertex>> r = gr.minimumSpanningTree();
        List<Edge<Vertex>> cA1 = new ArrayList<>(0);
        List<Edge<Vertex>> cA2 = new ArrayList<>(0);
        List<Edge<Vertex>> cA3 = new ArrayList<>(0);
        List<Edge<Vertex>> cA4 = new ArrayList<>(0);
        cA1.add(ab); cA1.add(be); cA1.add(de); cA1.add(ce); cA1.add(cf);
        cA2.add(ad); cA2.add(be); cA2.add(ce); cA2.add(de); cA2.add(cf);
        cA3.add(ab); cA3.add(be); cA3.add(ad); cA3.add(ce); cA3.add(cf);
        cA4.add(ab); cA4.add(be); cA4.add(ce); cA4.add(ad); cA4.add(cf);
        Assert.assertTrue(r.equals(cA1) || r.equals(cA2) || r.equals(cA3) || r.equals(cA4));
    }

}