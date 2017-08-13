import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Scanner;

public class Solution {

    public static boolean validCell(final int x, final int y, final String[] grid, HashSet<Point> visited){
        if(    0 < x && x < grid.length
            && 0 < y && y < grid[0].length()
            && (grid[x].charAt(y) == '-'
                || grid[x].charAt(y) == '.')
            && !visited.contains(new Point(x,y))
                )
            return true;
        else
            return false;
    }
    public static void addNow(Point parent, Point p, LinkedList<Point> ll, HashMap<Point, Point> hm){
        ll.add(p);
        hm.put(p, parent);
    }
    public static LinkedList<Point> findNeighbors(Point point, String[] grid, HashMap<Point, Point> hm, HashSet<Point> visited) {
        LinkedList<Point> ll = new LinkedList<>();
        int x = (int) point.getX();
        int y = (int) point.getY();
        if(validCell(x-1, y, grid, visited))
            addNow(point, new Point(x-1, y), ll, hm);
        if(validCell(x, y-1, grid, visited))
            addNow(point, new Point(x, y-1), ll, hm);
        if(validCell(x, y+1, grid, visited))
            addNow(point, new Point(x, y+1), ll, hm);
        if(validCell(x+1, y, grid, visited))
            addNow(point, new Point(x+1, y), ll, hm);
        return ll;
    }
    public static LinkedList<Point> DFS(Point source, Point dest, String[] grid, HashMap<Point, Point> pathMap) {
        HashSet<Point> visited = new HashSet();
        LinkedList<Point> path = new LinkedList<Point>();
        LinkedList<Point> queue = new LinkedList<Point>();
        queue.add(source);

        Point prevPoint = null;
        while(!queue.isEmpty()) {
            Point currentPoint = queue.remove();
            if(grid[(int) currentPoint.getX()].charAt((int) currentPoint.getY()) == '.'){//currentPoint.equals(dest)) {
                path.add(currentPoint);
                break;
            }
            if(!visited.contains(currentPoint)) {
                visited.add(currentPoint);
                path.add(currentPoint);
                queue.addAll(findNeighbors(currentPoint, grid, pathMap, visited));
            }
            prevPoint = currentPoint;

        }
        return path;

    }

    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);

        int pacman_r = in.nextInt();
        int pacman_c = in.nextInt();

        int food_r = in.nextInt();
        int food_c = in.nextInt();

        int r = in.nextInt();
        int c = in.nextInt();

        String grid[] = new String[r];

        for(int i = 0; i < r; i++) {
            grid[i] = in.next();
        }

        HashMap<Point, Point> hm = new HashMap<>();
        Point dest = new Point(food_r, food_c);
        Point source = new Point(pacman_r, pacman_c);
        LinkedList<Point> ll = DFS(source, dest, grid, hm);
        System.out.println(ll.size());
        while(!ll.isEmpty()) {
            Point point = ll.remove();
            System.out.println((int) point.getX() + " " + (int) point.getY());
        }
        System.out.println(hm.size());
        Point currPoint = dest;
        while(!source.equals(currPoint) && !hm.isEmpty() && currPoint != null) {
            System.out.println((int) currPoint.getX() + " " + (int) currPoint.getY());
            currPoint = hm.remove(currPoint);
        }

    }
}
