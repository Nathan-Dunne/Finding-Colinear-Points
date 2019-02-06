/******************************************************************************
* Data Structures Assignment 2
*
* Name: Nathan Dunne
* ID: K00211819
* Course: Games Design & Development
*
******************************************************************************/

import java.util.Comparator;

public class Point implements Comparable<Point>
{
    public final Comparator<Point> SLOPE_ORDER = new SlopeOrder();;      
    
    /*
        Decides the order for comparison.
    */
    private class SlopeOrder implements Comparator<Point> 
    {
        public int compare(Point a, Point b) 
        {
            int difference = 0;
            
            if (slopeTo(a) > slopeTo(b))
            {
                difference = 1;
            }
            else if (slopeTo(a) < slopeTo(b)) 
            {
                difference = -1;
            }
            
            return difference;
        }
    }
    
    private final int x;                             // x coordinate
    private final int y;                              // y coordinate

    public Point(int x, int y)
    {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    public void draw()
    {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    public void drawTo(Point that)
    {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    public double slopeTo(Point that)
    {
        double slope;
        
        if((this.x == that.x) && (this.y == that.y))
        {
            slope = Double.NEGATIVE_INFINITY;
        }
        else if(this.x == that.x)
        {
            slope = Double.POSITIVE_INFINITY;
        }
        else if(this.y == that.y)
        {
            slope = 0;
        }
        else
        {
            slope = (double)(that.y - this.y) / (that.x - this.x);
        }
        
        return slope;
    }
    
    public int compareTo(Point that)
    {
        int comparison;
        
        if (this.y < that.y)
        {
            comparison = -1;
        }
        else if (this.y > that.y)
        {
            comparison = 1;
        }
        else if (this.x < that.x)
        {
            comparison = -1;
        }
        else if (this.x > that.x)
        {
            comparison = 1;
        }
        else
        {
            comparison = 0;
        }
        
        return comparison;
    }

    public String toString()
    {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    public static void main(String[] args)
    {
    }
}
