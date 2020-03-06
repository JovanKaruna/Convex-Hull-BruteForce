import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;
import java.util.*;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.Font;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Main{
    static ArrayList<Point> pointlist = new ArrayList<Point>();
    static ArrayList<Line> solutionlines = new ArrayList<Line>();   
    static Set<Point> solutionlist = new HashSet<Point>(); 

    //Random points from -250 to 250
    public static void Randomgenerator(int n){
        for (int i = 1; i <= n; i++){
            Random rand = new Random();
            int x = rand.nextInt(500) - 250;
            int y = rand.nextInt(500) - 250;
            Point P = new Point(x,y);
            pointlist.add(P);
        }
    }
    //Print points with (X,Y) enter output
    public static void PrintList(ArrayList<Point> List){
        for (int i = List.size()-1; i >= 0; i--){
            System.out.print(List.get(i).toString() + "\n");
        }
        System.out.print("\n");
    }

    //BruteForce to make convex hull
    public static void BruteForce(ArrayList<Point> points, Set<Point> solution){
		for(int i = 0; i < points.size(); i++){
			for(int j = 0; j < points.size(); j++){
                if(i == j){
                    continue;
                }
				boolean Line = true;
				Point p1 = points.get(i);
				Point p2 = points.get(j);
				int A = p2.GetY() - p1.GetY();
				int B = p1.GetX() - p2.GetX();
				int C = p1.GetX()*p2.GetY() - p1.GetY()*p2.GetX();
				for(int k = 0; k < points.size(); k++){
                    if(k == i || k == j){
                        continue;
                    }
					Point p3 = points.get(k);
                    int Area = A*p3.GetX() + B*p3.GetY() - C;
                    if(Area < 0){
                        Line = false;
                        break;
                    }
				}
				if(Line){ //if all the points is in the right side of the line
                    solution.add(p1);
                    solution.add(p2);
					//add solutionlines to visualisation
                    solutionlines.add(new Line(p1,p2));
                    
				}
			}
		}
		
	}

    //Show Convex Hull Graph
	private static void ShowGraph() {
        JFrame frame = new JFrame("Convex Hull Graph");
        frame.setSize(600,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        frame.setResizable(false);
		frame.add(new JPanel(){
			@Override
			public void paintComponent(Graphics g){
				int x = 600; 
				int y = 600;
				int center = 300;
                // Add image as a background
                Image image = Toolkit.getDefaultToolkit().getImage("blue.jpg");
		        g.drawImage(image, 0, 0, this);
				//Draw gray Cartesian Axis
				g.setColor(Color.GRAY);
				g.drawLine(0,center, x,center);
                g.drawLine(center,0, center,y);
                
				//Draw white points
				g.setColor(Color.WHITE);
				for (Point p : pointlist){
					g.fillOval(p.GetX() + center - 3, center - p.GetY() - 3, 6, 6);
				}
				//Draw Convex Hull Lines
				for (int i = 0; i < solutionlines.size(); i++){
					Point p1 = solutionlines.get(i).getP1();
					Point p2 = solutionlines.get(i).getP2();
                    g.drawLine(p1.GetX() + center, center - p1.GetY(), p2.GetX()
                     + center, center - p2.GetY());
                }
                //Add text
                int n = pointlist.size();
                g.setFont(new Font("TimesRoman", Font.PLAIN, 20));
                g.drawString("Convex Hull with " + n + " points", 195, 25);
                g.setFont(new Font("TimesRoman", Font.PLAIN, 15));
                g.drawString("By Jovan Karuna Cahyadi / 13518024", 360,568);
			}
        });
    }
    
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        int n;
        //Input points
        System.out.print("Input banyaknya titik : ");
        n = s.nextInt();
        Randomgenerator(n); //Random points
        s.close();
        PrintList(pointlist);
        
        //BruteForce
        System.out.println("Brute-force solution:");
        long start = System.nanoTime();
        BruteForce(pointlist, solutionlist);
        long end = System.nanoTime();
        //Time algorithm
        double t1 = (double)start;
        double t2 = (double)end;
        double time = (t2 - t1)/1000000 ;
        
        //Print solution list
        System.out.println(solutionlist);
        System.out.println("\nTime needed : "+ time + " ms");

        //Show Convex Hull Graph
        ShowGraph();
        
    }
}
