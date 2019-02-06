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

public class Fast 
{
    private Point[] points;
    private Point[] slopeSortedPoints;
    private int pointAmt;
    private Point originPoint;
    
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
        Provides an efficient solution by taking an origin point from a sorted array
        and sorting an auxilary array based on the slope to that origin. 
        
        Subsets in the auxillary array that contain equal slopes to the origin are tracked,
        if a minimum or greater amount of slopes is found, each point is printed
        out and a line is drawn between the origin and the last point in the subset.
    */
    private void findCollinearPoints()
    {
        Arrays.sort(points);
        
        for (int i=0; i<pointAmt; i++)
        {
            originPoint = points[i];               
            originPoint.draw();
            
            slopeSortedPoints = Arrays.copyOf(points, points.length);
            
            Arrays.sort(slopeSortedPoints, i, pointAmt, originPoint.SLOPE_ORDER);
            
            // Slope to be check against
            double originSlope = originPoint.slopeTo(slopeSortedPoints[i]);
            
            int startPos = 0;
            int endPos = 0;
           
            int minPointsToCheck = 3; // This +1 for the origin.
            
            /*
                As the auxillary array is sorted from position i,
                I need only check subsets from i+1 within it;
            */
            for(int j=i+1; j<pointAmt; j++) 
            {   
                // The slope made from the origin to the point in the auxillary array.
                double sortedSlope = originPoint.slopeTo(slopeSortedPoints[j]);
                
                if(originSlope == sortedSlope) // If equal, continue finding the size of the subset.
                {
                    endPos++;
                }
                else // If the subset is fully accounted for.
                {
                    if((endPos - startPos)+1 >= minPointsToCheck) 
                    {
                        drawAndPrint(startPos, endPos);
                    }
                    
                    // Realigning the subset trackers.
                    originSlope = sortedSlope;     
                    startPos = j;
                    endPos = j;
                    
                }
                
                // Accounting for the existance of an ending subset in the auxillary array.
                if((endPos - startPos)+1 >= minPointsToCheck && (j == (pointAmt-1))) 
                {  
                    drawAndPrint(startPos, endPos);
                }
            }
        }
    }
    
    /*
        Given a start and end position, prints out the points that correspond
        to the slope of an origin point.
    */
    private void drawAndPrint(int startPos, int endPos)
    {
        originPoint.drawTo(slopeSortedPoints[endPos]);
        System.out.print(originPoint.toString());
                
        for(int i=startPos; i<=endPos; i++)
        {
            System.out.print("->" + slopeSortedPoints[i].toString());
        }

        System.out.println();    
    }

    public static void main(String[] args) 
    {
        long time = System.currentTimeMillis();
        
        String inputFile = args[0];
        Fast fasterFind = new Fast();
        
        fasterFind.rescaleCoordSystem();
        
        try 
        {
            fasterFind.readInputFile(inputFile);
        } 
        catch(FileNotFoundException e)
        {
            System.out.println(e);
            System.out.println("No file found.");
        }
        
        fasterFind.findCollinearPoints();
        
        System.out.println("Fast: " + (System.currentTimeMillis() - time));
    }  
}

