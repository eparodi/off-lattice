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
    static double length;

    static void dynamicSystemParse() throws FileNotFoundException {
        File dynamicFile = new File(CliParser.dynamicFile);
        Scanner sc = new Scanner(dynamicFile);
        length = sc.nextDouble();   /* Discard first time value */
        matrixSize = (int) Math.floor(Parser.length/CliParser.interactionRadius);
        int id = 1;
        while(sc.hasNextDouble()){
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            double angle = sc.nextDouble();
            Particle p = new Particle(x,y, angle, id++);
            particles.add(p);
            numberOfParticles += 1;
        }

    }

}
