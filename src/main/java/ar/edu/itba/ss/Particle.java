package ar.edu.itba.ss;

public class Particle implements Cloneable{

    double x;
    double y;
    double angle;
    int id;

    Particle(double x, double y, double angle, int id){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.id = id;
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
        if (dx > CliParser.length / 2)
            dx = CliParser.length - dx;

        double dy = Math.abs(this.y - particle.y);
        if (dy > CliParser.length / 2)
            dy = CliParser.length - dy;

        return Math.sqrt(Math.pow(dx,2) + Math.pow(dy,2));
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Particle particle = (Particle) o;

        return id == particle.id;
    }
}
