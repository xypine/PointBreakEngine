package PBEngine;

/**
 *  Find if a path exists from top left corner of given 2D array map, considering
 *  1 as path and 0 as walls, to the position where 'X' is placed.
 *
 *  This program uses Breadth-First search, and marks current position as '0' to mark
 *  it as visited point.
 *
 *  author for the original code: Jayesh Chandrapal.
 *  original source: https://rextester.com/MLPWLV27662
 *  
 */

import com.sun.tools.javac.util.ArrayUtils;
import java.util.*;
import java.lang.*;

class Node {
    int x;
    int y;
    
    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }
}

class Rextester
{  
    public static void main(String args[])
    {
        final char[][] matrix = {
            {'1', '0', '1', '1'},
            {'1', '0', '1', 'X'},
            {'1', '0', '1', '1'},
            {'1', '1', '1', '1'}
        };
        //boolean pathExists = pathExists(matrix);
        for(Node x : getPath(matrix)){
            System.out.println("NodeX: " + x.x + " NodeY: " + x.y);
        }
        //System.out.println(pathExists ? "YES" : "NO");
    }
    
    public static boolean pathExists(char[][] matrix) {
        int N = matrix.length;
        char[][] tmp = matrix;
        List<Node> queue = new ArrayList<Node>();
        queue.add(new Node(0, 0));
        boolean pathExists = false;
        
        while(!queue.isEmpty()) {
            Node current = queue.remove(0);
            if(tmp[current.x][current.y] == 'X') {
                pathExists = true;
                break;
            }
            
            tmp[current.x][current.y] = '0'; // mark as visited
            
            List<Node> neighbors = getNeighbors(tmp, current);
            queue.addAll(neighbors);
            System.out.println(neighbors.size());
        }
        
        return pathExists;
    }
    public static List<Node> getPath(char[][] matrix) {
        int N = matrix.length;
        char[][] tmp = matrix;
        List<Node> queue = new ArrayList<>();
        queue.add(new Node(0, 0));
        boolean pathExists = false;
        List<Node> path = new ArrayList<>();
        
        while(!queue.isEmpty()) {
            
            Node current = queue.remove(0);
            if(tmp[current.x][current.y] == 'X') {
                pathExists = true;
                break;
            }
            
            tmp[current.x][current.y] = '0'; // mark as visited
            path.add(current);
            List<Node> neighbors = getNeighbors(tmp, current);
            System.out.println(neighbors.size());
            queue.addAll(neighbors);
            
        }
        
        return path;
    }
    public static List<Node> getNeighbors(char[][] matrix, Node node) {
        List<Node> neighbors = new ArrayList<Node>();
        
        if(isValidPoint(matrix, node.x - 1, node.y)) {
            neighbors.add(new Node(node.x - 1, node.y));
        }
        
        if(isValidPoint(matrix, node.x + 1, node.y)) {
            neighbors.add(new Node(node.x + 1, node.y));
        }
        
        if(isValidPoint(matrix, node.x, node.y - 1)) {
            neighbors.add(new Node(node.x, node.y - 1));
        }
        
        if(isValidPoint(matrix, node.x, node.y + 1)) {
            neighbors.add(new Node(node.x, node.y + 1));
        }
        
        return neighbors;
    }
    
    public static boolean isValidPoint(char[][] matrix, int x, int y) {
 //       System.out.println("");
 //       System.out.println(!(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && (matrix[x][y] != '0'));
        return !(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && (matrix[x][y] != '0');
    }
    public static char[][] clone2D(char[][] source){
        char[][] out = source;
        for(int i : new Range(source.length)){
            out [i] = source[i];
        }
        return out;
    }
}
