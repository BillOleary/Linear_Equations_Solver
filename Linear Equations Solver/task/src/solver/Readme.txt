The code solves a n x n Linear Equation using Gauss/Jordan Elimination method.
Description

FROM STAGE 3 OF THE HYPERSKILL CODING CHALLENGE

In this stage, the problem became much harder. You should generalize the solution for any amount of variables. The important part is to understand that in most cases if the number of equations equals the number of variables there is only one solution. We will consider special cases in the next stage.

Such multiple linear equations connected together are called system of linear equations.

One of the most popular ways to solve them is Gauss-Jordan Elimination. You can see here and here detailed explanations of how to do it with 3 variables.

The general system of linear equations looks like in the picture below. The variables are named x1,x2,...,xnx_1, x_2, ..., x_nx1​,x2​,...,xn​. The coefficients are named ai1,ai2,...aina_{i1}, a_{i2}, ... a_{in}ai1​,ai2​,...ain​ for the iii-th row. And the constants are named b1,b2,...bnb_1, b_2, ... b_nb1​,b2​,...bn​.

{a11x1+a12x2+...+a1nxn=b1a21x1+a22x2+...+a2nxn=b2...an1x1+an2x2+...+annxn=bn\begin{cases} \begin{aligned} a_{11}x_1 & + a_{12}x_2+... +a_{1n}x_n = b_1 \\ a_{21}x_1 & + a_{22}x_2 +... + a_{2n}x_n = b_2 \\ &... \\ a_{n1}x_1 & + a_{n2}x_2 +... + a_{nn}x_n = b_n \\ \end{aligned} \end{cases}⎩⎪⎪⎪⎪⎪⎨⎪⎪⎪⎪⎪⎧​a11​x1​a21​x1​an1​x1​​+a12​x2​+...+a1n​xn​=b1​+a22​x2​+...+a2n​xn​=b2​...+an2​x2​+...+ann​xn​=bn​​​

Like said in the video, firstly, the algorithm should null the first column of coefficients except for the first coefficient - it should be equal to 1. Notice letters c and d instead of a and b. It means that through some calculations these coefficients became other coefficients and thus we cannot use letters a and b since it would mean the initial coefficients.

{x1+c12x2+...+c1nxn=d10+c22x2+...+c2nxn=d20+...0+cn2x2+...+cnnxn=dn\begin{cases} \begin{aligned} x_1 & + c_{12}x_2+... +c_{1n}x_n = d_1 \\ 0 & + c_{22}x_2 +... + c_{2n}x_n = d_2 \\ 0 & +... \\ 0 & + c_{n2}x_2 +... + c_{nn}x_n = d_n \\ \end{aligned} \end{cases}⎩⎪⎪⎪⎪⎪⎨⎪⎪⎪⎪⎪⎧​x1​000​+c12​x2​+...+c1n​xn​=d1​+c22​x2​+...+c2n​xn​=d2​+...+cn2​x2​+...+cnn​xn​=dn​​​

After that, you need to null the second column all the way from the third row. The second row should contain the coefficient equal to 1.

{x1 + c12x2+...+c1nxn=d10 + x2+...+e2nxn=f20 + 0+...0 + 0+...+ennxn=fn\begin{cases} \begin{alignedat}{2} x_1 & \>+\> & c_{12}x_2 & +... +c_{1n}x_n = d_1 \\ 0 & \>+\> & x_2 & +... + e_{2n}x_n = f_2 \\ 0 & \>+\> & 0 & + ... \\ 0 & \>+\> & 0 & +... + e_{nn}x_n = f_n \\ \end{alignedat} \end{cases}⎩⎪⎪⎪⎪⎪⎨⎪⎪⎪⎪⎪⎧​x1​000​++++​c12​x2​x2​00​+...+c1n​xn​=d1​+...+e2n​xn​=f2​+...+...+enn​xn​=fn​​​

The same goes for the rest of the columns. In the end, you should get something like that:

{x1 + c12x2+... + c1nxn=d10 + x2+... + e2nxn=f2...0 + 0+...+0 +       xn=gn\begin{cases} \begin{alignedat}{4} x_1 & \>+\> & c_{12}x_2 & + ... & \>+\> & c_{1n}x_n & = d_1 \\ 0 & \>+\> & x_2 & +... & \>+\>& e_{2n}x_n & = f_2 \\ & ... \\ 0 & \>+\> & 0 & +... + 0 & \>+\> & \>\>\>\>\>\> x_n & = g_n \\ \end{alignedat} \end{cases}⎩⎪⎪⎪⎪⎪⎨⎪⎪⎪⎪⎪⎧​x1​00​++...+​c12​x2​x2​0​+...+...+...+0​+++​c1n​xn​e2n​xn​xn​​=d1​=f2​=gn​​​

The second part of the algorithm is to iterate rows from the last to the first and null the top part of the linear system. In the end, there should be only diagonal elements of the linear system.

{x1 + 0+... +   0=k10 + x2+0+... +   0=f2...0 + 0+...+0 + xn=gn\begin{cases} \begin{alignedat}{4} x_1 & \>+\> & 0 & + ... & \>+\> & \>\>0 & = k_1 \\ 0 & \>+\> & x_2 & + 0+ ... & \>+\>& \>\>0 & = f_2 \\ & ... \\ 0 & \>+\> & 0 & +... + 0 & \>+\> & x_n & = g_n \\ \end{alignedat} \end{cases}⎩⎪⎪⎪⎪⎪⎨⎪⎪⎪⎪⎪⎧​x1​00​++...+​0x2​0​+...+0+...+...+0​+++​00xn​​=k1​=f2​=gn​​​

And the right part of the system is the solution to this system.

In this stage, you need to write a program that reads coefficients from a file, solves the system of linear equations and writes the answer to another file. You should pass paths to files using command-line arguments. Write to the file only answers separated by \n. Output all the steps only to the console, not in the file.

The first line of the file should contain the number N - a number of variables being also a number of equations. Every other N lines contain N+1 numbers - N coefficients of the current row and a constant as the last number in this line. The program also should output all rows manipulation it is doing during solving the system of linear equations.

Try to create various classes like Matrix, Row, LinearEquation. With these, the code would be more readable and easier to program.
Example

Suppose you have a file named in.txt. It contains the following:

3
1 1 2 9
2 4 -3 1
3 6 -5 0

Which corresponds to the following equation:

{1∗x1+1∗x2+2∗x3=92∗x1+4∗x2−3∗x3=13∗x1+6∗x2−5∗x3=0\begin{cases} 1*x_1 + 1*x_2 + 2*x_3 = 9 \\ 2*x_1 +4*x_2 - 3*x_3 = 1 \\ 3* x_1 + 6*x_2 - 5*x_3 = 0 \\ \end{cases}⎩⎪⎪⎨⎪⎪⎧​1∗x1​+1∗x2​+2∗x3​=92∗x1​+4∗x2​−3∗x3​=13∗x1​+6∗x2​−5∗x3​=0​

Below is how your program might work. The lines which start with > represent the user input. Everything that is output to the console is not checked by tests, use this to debug your program. Tests only check the output file.

> java Solver -in in.txt -out out.txt
Start solving the equation.
-2 * R1 + R2 -> R2
-3 * R1 + R3 -> R3
0.5 * R2 -> R2
-3 * R2 + R3 -> R3
-2 * R3 -> R3
3.5 * R3 + R2 -> R2
-2 * R3 + R1 -> R1
-1 * R2 + R1 -> R1
The solution is: (1.0, 2.0, 3.0)
Saved to file out.txt

And the file out.txt should look like this (each answer should be on separate line without any excess symbols).

1.0
2.0
3.0
