package ar.edu.itba;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;

public class Parser {

    private static int numberOfParticles;
    static Queue<Particle> particles = new LinkedList<Particle>();
    static final double AREA_LENGTH = 200;

    public static void parseParticles() throws FileNotFoundException {
        dynamicSystemParse();
    }

    private static void dynamicSystemParse() throws FileNotFoundException {
        File dynamicFile = new File(CliParser.dynamicFile);
        Scanner sc = new Scanner(dynamicFile);
        sc.nextInt();   /* Discard first time value */
        while(sc.hasNextDouble()){
            double x = sc.nextDouble();
            double y = sc.nextDouble();
            double vx = sc.nextDouble();
            double vy = sc.nextDouble();
            double angle = Math.tan(vy/vx);
            Particle p = new Particle(x,y, angle);
            particles.add(p);
        }

    }

}
