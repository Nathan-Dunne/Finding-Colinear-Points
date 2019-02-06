/******************************************************************************
* Data Structures Assignment 2
*
* Name: Nathan Dunne
* ID: K00211819
* Course: Games Design & Development
*
******************************************************************************/

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class Brute
{
    private Point[] points;
    private int pointAmt;
    
    /*
        Reads in the amount of points in a file from the first number present,
        then reads x and y from each next pair of numbers into new Points.
    */
    private void readInputFile(String fileName) throws FileNotFoundException
    {
        File inputFile = new File(fileName);
        Scanner input = new Scanner(inputFile);
        
        pointAmt = input.nextInt();
        points = new Point[pointAmt];
        
        for (int i=0; i<pointAmt; i++)
        {
            int x = input.nextInt();
            int y = input.nextInt();
            points[i] = new Point(x, y);
            
            points[i].draw();
        }
    }
    
    /*
        Rescales the coordinate system as outlined in requirements.
    */
    private void rescaleCoordSystem()
    {
        StdDraw.setXscale(0, 32768);
        StdDraw.setYscale(0, 32768);
    }
    
    /*
        Provides a brute force solution by taking every single set of 4 points
        and checking if they are collinear to eachother.
    */
    private void findCollinearPoints()
    {
        Arrays.sort(points);
        
        if (pointAmt >= 4)
        {
            for (int i=0; i<pointAmt; i++)
            {
                for (int j=i+1; j<pointAmt; j++)
                {
                    for (int k=j+1; k<pointAmt; k++)
                    {
                        for (int l=k+1; l< pointAmt; l++)
                        {
                            if (isCollinear(points[i], points[j], points[k], points[l])) 
                            {
                                printPoints(points[i], points[j], points[k], points[l]);
                                
                                points[i].drawTo(points[l]);
                            }
                        }
                    }
                }
            }
        }
        else
        {
            System.out.println("Less than 4 points in file.");
        }
    }
    
    private void printPoints(Point a, Point b, Point c, Point d)
    {
        System.out.println(a.toString() + "->" + b.toString() + "->" + c.toString() + "->" + d.toString());
    }
    
    private boolean isCollinear(Point a, Point b, Point c, Point d)
    {
        return (a.slopeTo(b) == b.slopeTo(c)) && (b.slopeTo(c) == a.slopeTo(d));
    }
    
    public static void main(String[] args)
    {
        long time = System.currentTimeMillis();
        
        String inputFile = args[0];
        Brute bruteForce = new Brute();
        
        bruteForce.rescaleCoordSystem();
        
        try 
        {
            bruteForce.readInputFile(inputFile);
        } 
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            System.out.println("No file found.");
        }
        
        bruteForce.findCollinearPoints();
        
        //System.out.println("Brute: " + (System.currentTimeMillis() - time));
    }
}
