package ar.edu.itba.ss;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;

public class OffLattice
{
    public static void main( String[] args ) {

        CliParser.parseOptions(args);
        try{
            Parser.dynamicSystemParse();
            offLattice();
        }catch(Exception e){
            e.printStackTrace();
            System.out.println("There was an error while executing the program, please try again");
            exit(1);
        }

    }

    private static void offLattice() throws CloneNotSupportedException {

        ArrayList<List<Particle>> cells = createCells();

        System.out.println(Parser.particles.size()); /* Print N */
        System.out.println(0);
        for (Particle p : Parser.particles){
            System.out.println(p.x + "\t" + p.y + "\t" + p.angle);
            assignCell(cells, p);
        }

        for (int i = 0; i < CliParser.time; i++){
            ArrayList<List<Particle>> oldValues = cloneCells(cells);
            List<Particle> particles = new LinkedList<Particle>();
            for (List<Particle> cell : cells) {
                for (Particle p : cell) {
                    double angle = getNewAngle(oldValues, p);
                    changePosition(p);
                    p.angle = angle;
                    particles.add(p);
                }
            }
            cells = createCells();
            System.out.println(Parser.numberOfParticles);
            System.out.println(i + 1);
            for (Particle p: particles){
                System.out.println(p.x + "\t" + p.y + "\t" + p.angle);
                assignCell(cells, p);
            }
        }
    }

    private static ArrayList<List<Particle>> cloneCells(ArrayList<List<Particle>> cells) throws CloneNotSupportedException {
        ArrayList<List<Particle>> copy = new ArrayList<List<Particle>>(cells.size());

        for (List<Particle> list : cells){
            List<Particle> copyList = new LinkedList<Particle>();
            for (Particle p: list){
                Particle copyParticle = p.getClone();
                copyList.add(copyParticle);
            }
            copy.add(copyList);
        }

        for (int i = 0; i < cells.size() ; i++){
            copy.add(new LinkedList<Particle>());
        }

        return copy;
    }

    private static void assignCell(ArrayList<List<Particle>> cells, Particle p){
        double cellSize = CliParser.length / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);
        if (cellY == CliParser.length)
            cellY = 0;
        if (cellX == CliParser.length)
            cellX = 0;
        int cellNumber = (int) (cellY * Parser.matrixSize + cellX);
        List <Particle> cellParticles = cells.get(cellNumber);
        cellParticles.add(p);
    }

    private static double getNewAngle(ArrayList<List<Particle>> cells, Particle p){
        double angle = 0;
        double cellSize = CliParser.length / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);

        List<Particle> particles = new LinkedList<Particle>();

        particles.addAll(checkAdjacent(p, cellX, cellY, cells));
        particles.addAll(checkAdjacent(p, cellX, cellY + 1, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY + 1, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY - 1, cells));

        double sin = 0;
        double cos = 0;
        for (Particle particle : particles){
            sin += Math.sin(particle.angle);
            cos += Math.cos(particle.angle);
        }

        if (particles.size() != 0){
            sin = sin / particles.size();
            cos = cos / particles.size();
            angle = Math.atan2(cos, sin);
            double noise =  CliParser.noise * (Math.random() - 1.0 / 2.0);
            angle += noise;
        }
        if (angle > Math.PI){
            angle -= Math.PI;
        }else if (angle < -Math.PI){
            angle += Math.PI;
        }
        return angle;
    }

    private static void changePosition(Particle p){
        p.x += Math.cos(p.angle) * CliParser.speed;
        p.y += Math.sin(p.angle) * CliParser.speed;
        if (p.x >= CliParser.length){
            p.x -= CliParser.length;
        }
        if (p.y >= CliParser.length){
            p.y -= CliParser.length;
        }
        if (p.x < 0){
            p.x += CliParser.length;
        }
        if (p.y < 0){
            p.y += CliParser.length;
        }
    }

    private static List<Particle> checkAdjacent(Particle particle, double cellX, double cellY, ArrayList<List<Particle>> cells){
        List<Particle> particles = new LinkedList<Particle>();
        if (cellX >= Parser.matrixSize){
            cellX = 0;
        }

        if (cellY >= Parser.matrixSize){
            cellY = 0;
        }

        if (cellX == -1){
            cellX = Parser.matrixSize - 1;
        }

        if (cellY == -1){
            cellY = Parser.matrixSize - 1;
        }

        int adjCellNumber = (int) (cellY * Parser.matrixSize + cellX);

        List<Particle> cellParticles = cells.get(adjCellNumber);

        for (Particle adjacentParticle : cellParticles){

            double distance = particle.getPeriodicContourDistance(adjacentParticle);

            if (distance < CliParser.interactionRadius){
                particles.add(adjacentParticle);
            }
        }
        return particles;
    }

    private static ArrayList<List<Particle>> createCells(){
        ArrayList<List<Particle>> cells = new ArrayList<List<Particle>>(Parser.matrixSize * Parser.matrixSize);

        for (int i = 0; i < Parser.matrixSize * Parser.matrixSize ; i++){
            cells.add(new LinkedList<Particle>());
        }
        return cells;
    }

}
