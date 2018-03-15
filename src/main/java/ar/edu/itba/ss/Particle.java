package ar.edu.itba.ss;

import java.util.HashSet;
import java.util.Set;

public class Particle implements Cloneable{

    double x;
    double y;
    double angle;
    int id;
    Set<Particle> neighbours;

    Particle(double x, double y, double angle, int id){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.id = id;
        this.neighbours = new HashSet<>();
    }

    Particle getClone() throws CloneNotSupportedException {
        return (Particle) super.clone();
    }

    public double getDistanceTo(Particle particle){
        return Math.sqrt(Math.pow(x - particle.x, 2) +
                Math.pow(y - particle.y, 2));
    }

    public double getPeriodicContourDistance(Particle particle){
        double dx = Math.abs(this.x - particle.x);
        if (dx > Parser.length / 2)
            dx = Parser.length - dx;

        double dy = Math.abs(this.y - particle.y);
        if (dy > Parser.length / 2)
            dy = Parser.length - dy;

        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        return id == particle.id;
    }

    public void addNeighbour(Particle adjacentParticle) {
        this.neighbours.add(adjacentParticle);
    }
}
