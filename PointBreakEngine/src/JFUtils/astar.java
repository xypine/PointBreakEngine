package JFUtils;

/**
 *  Find if a path exists from top left corner of given 2D array map, considering
 *  1 as path and 0 as walls, to the position where 'X' is placed.
 *
 *  This program uses Breadth-First search, and marks current position as '0' to mark
 *  it as visited point.
 *
 *  author for the original code: Jayesh Chandrapal.
 *  original code: https://rextester.com/MLPWLV27662
 *  
 */

import JFUtils.astarNode;
import JFUtils.dVector;
import java.util.*;
import java.lang.*;
import static java.lang.Math.pow;
import static java.lang.Math.sqrt;



public class astar
{  
    public static LinkedList<dVector> pathToVector(List<astarNode> path){
        LinkedList<dVector> vectors = new LinkedList<>();
        for(astarNode step: path){
            try {
                vectors.add(new dVector(step.x, step.y));
            } catch (Exception e) {
                vectors.add(new dVector(0, 0));
            }
        }
        return vectors;
    }
    public static void main(String[] Args)
    {
        System.out.println("Performing pathfinding test...");
        final char[][] matrix = {
            {'1', '0', '0', '1'},
            {'1', '1', '0', 'X'},
            {'0', '1', '0', '1'},
            {'1', '1', '1', '1'}
        };
        //boolean pathExists = pathExists(matrix);
        char[][] result = new char[matrix.length][matrix[0].length];
        for(char[] lane : result){
            for(char i : lane){
                i = ' ';
            }
        }
        for(dVector x : pathToVector(getPath(matrix, 0, 0))){
            System.out.println("NodeX: " + x.x + " NodeY: " + x.y);
            result[(int)x.x][(int)x.y] = '-';
        }
        for(char[] lane : result){
            for(char i : lane){
                System.out.print(i + " ");
            }
            System.out.println("");
        }
        //System.out.println(pathExists ? "YES" : "NO");
    }
    
    public static boolean pathExists(char[][] matrix, int fromX, int fromY) {
        int N = matrix.length;
        char[][] tmp = matrix;
        List<astarNode> queue = new ArrayList<astarNode>();
        queue.add(new astarNode(fromX, fromY, 0));
        boolean pathExists = false;
        List<astarNode> path = new ArrayList<>();
        dVector start = new dVector(fromX, fromY);
        dVector goal = new dVector(0, 0);
        int yp = 0, xp = 0;
        for(char[] lane : tmp){
            for(char i : lane){
                if(i == 'X'){
                    goal = new dVector(xp, yp);
                }
                yp++;
            }xp++;yp=0;
        }
        while(!queue.isEmpty()) {
            astarNode current = queue.remove(0);
            if(tmp[current.x][current.y] == 'X') {
                pathExists = true;
                break;
            }
            
            tmp[current.x][current.y] = '0'; // mark as visited
            
            List<astarNode> neighbors = getNeighbors(tmp, current, start, goal);
            queue.addAll(neighbors);
        }
        
        return pathExists;
    }
    public static List<astarNode> getPath(char[][] matrix, int fromX, int fromY) {
        Comparator<astarNode> comp = astarNode.DESCENDING_COMPARATOR;
        int N = matrix.length;
        char[][] tmp = matrix;
        PriorityQueue<astarNode> queue = new PriorityQueue<>();
        
        boolean pathExists = false;
        List<astarNode> path = new ArrayList<>();
        dVector start = new dVector(fromX, fromY);
        dVector goal = new dVector(5, 5);
        int yp = 0, xp = 0;
        for(char[] lane : tmp){
            for(char i : lane){
                if(i == 'X'){
                    goal = new dVector(xp, yp);
                }
                yp++;
            }xp++;yp=0;
        }
        queue.add(new astarNode(fromX, fromY, score(start, goal, start)));
        //System.out.println("Goal: "+goal.represent());
        while(!queue.isEmpty()) {
            
            astarNode current = queue.remove();
            if(tmp[current.x][current.y] == 'X' || (current.x == goal.x && current.y == goal.y)) {
                pathExists = true;
                path = new ArrayList<>();
                astarNode origin = current;
                path.add(origin);
                while(origin != null){
                    origin = origin.cameFrom;
                    path.add(origin);
                }
                break;
            }
            
            tmp[current.x][current.y] = '0'; // mark as visited
            path.add(current);
            List<astarNode> neighbors = getNeighbors(tmp, current, start, goal);
            for(astarNode neighbour : neighbors){
                if(!queue.contains(neighbour)){
                    try{
                        if(neighbour.cameFrom.value < current.value){
                            neighbour.cameFrom = current;
                        }
                        
                    }catch(Exception e){
                        neighbour.cameFrom = current;
                    }
                    queue.add(neighbour);
                }
            }
        }
        
        return path;
    }
    public static int score(dVector location, dVector goal, dVector start){
        double distanceTo = getDistance(location, goal);
        double distanceFrom = getDistance(location, start);
        return (int) distanceTo;
    }
    public static double getDistance(dVector one, dVector two){
        double ry = (double) pow(one.y - two.y, 2.0);
        double rx = (double) pow(one.x - two.x, 2.0);
        double finish = (double) sqrt(rx + ry);
        return finish;
    }
    public static List<astarNode> getNeighbors(char[][] matrix, astarNode node, dVector start, dVector end) {
        List<astarNode> neighbors = new ArrayList<astarNode>();
        
        if(isValidPoint(matrix, node.x - 1, node.y)) {
            neighbors.add(new astarNode(node.x - 1, node.y, score(new dVector(node.x - 1, node.y), end, start)));
        }
        
        if(isValidPoint(matrix, node.x + 1, node.y)) {
            neighbors.add(new astarNode(node.x + 1, node.y, score(new dVector(node.x + 1, node.y), end, start)));
        }
        
        if(isValidPoint(matrix, node.x, node.y - 1)) {
            neighbors.add(new astarNode(node.x, node.y - 1, score(new dVector(node.x, node.y-1), end, start)));
        }
        
        if(isValidPoint(matrix, node.x, node.y + 1)) {
            neighbors.add(new astarNode(node.x, node.y + 1, score(new dVector(node.x, node.y+1), end, start)));
        }
        
        return neighbors;
    }
    
    public static boolean isValidPoint(char[][] matrix, int x, int y) {
 //       System.out.println("");
 //       System.out.println(!(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && (matrix[x][y] != '0'));
        boolean result;
        try{
            result = !(x < 0 || x >= matrix.length || y < 0 || y >= matrix.length) && (matrix[x][y] != '0');
        }
        catch(Exception e){
            result = false;
        }
        return result;
    }
    public static char[][] clone2D(char[][] source){
        char[][] out = source;
        for(int i : new Range(source.length)){
            out [i] = source[i];
        }
        return out;
    }
}
