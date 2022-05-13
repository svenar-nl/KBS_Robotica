package nl.windesheim.ictm2f.util;// Java Program to Solve Travelling Salesman Problem
// Using Incremental Insertion Method

// Main class
public class GFG {
    // Method 1
    // Travelling Salesman Incremental Insertion Method
    static int tspdp(int c[][], int tour[], int start,
                     int n)
    {

        int mintour[] = new int[20], temp[] = new int[20],
                mincost = 999, ccost, i, j, k;

        if (start == n - 1)
        {
            return (c[tour[n - 1]][tour[n]]
                    + c[tour[n]][1]);
        }

        // Logic for implementing the minimal cost

        for (i = start + 1; i <= n; i++)
        {

            for (j = 1; j <= n; j++)

                temp[j] = tour[j];

            temp[start + 1] = tour[i];

            temp[i] = tour[start + 1];

            if ((c[tour[start]][tour[i]]
                    + (ccost = tspdp(c, temp, start + 1, n)))
                    < mincost)

            {

                mincost = c[tour[start]][tour[i]] + ccost;

                for (k = 1; k <= n; k++)

                    mintour[k] = temp[k];
            }
        }

        // Now, iterating over the path (mintour) to
        // compute its cost
        for (i = 1; i <= n; i++)

            tour[i] = mintour[i];

        // Returning the cost of min path
        return mincost;
    }

    // Method 2
    // Main driver method
    public static int[] GFG(int numCities, int[][] c){
        int r[] = new int[numCities + 1];
        // Creating an object of Scanner class to take user
        // input
        // 1. Number of cities
        // 2. Cost matrix

        // Creating matrices in the main body
        int tour[] = new int[20];

        // Declaring variables
        int i, j, cost;

        // Step 1: To read number of cities

        // Display message for asking user to
        // enter number of cities

        // Reading and storing using nextInt() of Scanner
        int n = numCities;

        // Base case
        // If the city is 1 then
        // path is not possible
        // Cost doesnot play any role
        if (n == 1) {
            // Display on the console
            System.out.println("Path is not possible!");

            // terminate
            System.exit(0);
        }

        // Case 2
        // Many cities

        // Again, reading the cost of the matrix


        // Travelling across cities using nested loops
        for (i = 1; i <= n; i++)
            tour[i] = i - 1;

        // Calling the above Method 1 to
        cost = tspdp(c, tour, 1, n);

        // Now, coming to logic to print the optimal tour

        for (i = 1; i <= n; i++){
            r[i - 1] = tour[i];
        }

        r[r.length - 1] = 0;

        return r;
    }
}