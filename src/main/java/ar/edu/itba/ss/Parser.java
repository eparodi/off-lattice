package ar.edu.itba.ss;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Parser {

    static int numberOfParticles = 0;
    static Queue<Particle> particles = new LinkedList<Particle>();
    static int matrixSize;
    static Double speed;

    static void dynamicSystemParse() throws FileNotFoundException {
        matrixSize = (int) Math.floor(CliParser.length/CliParser.interactionRadius);
        File dynamicFile = new File(CliParser.dynamicFile);
        Scanner sc = new Scanner(dynamicFile);
        sc.nextInt();   /* Discard first time value */
        int id = 1;
        while(sc.hasNextDouble()){
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            double vx = sc.nextDouble();
            double vy = sc.nextDouble();
            if (speed == null){
                speed = Math.sqrt(Math.pow(vx,2) + Math.pow(vy,2));
            }
            double angle = Math.tan(vy/vx);
            Particle p = new Particle(x,y, angle, id++);
            particles.add(p);
            numberOfParticles += 1;
        }

    }

}
