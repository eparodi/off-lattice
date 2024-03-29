package ar.edu.itba.ss;

import net.jafama.FastMath;

import java.util.*;

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
                    getNeighbours(p, oldValues, cells);
                }
            }

            Map<Particle, Double> newAngle = new HashMap<>();

            for (List<Particle> cell : cells) {
                for (Particle p : cell) {
                    double angle = getNewAngle(p);
                    changePosition(p);
                    newAngle.put(p, angle);
                    particles.add(p);
                }
            }

            cells = createCells();
            System.out.println(Parser.numberOfParticles);
            System.out.println(i + 1);
            for (Particle p: particles){
                p.angle = newAngle.get(p);
                System.out.println(p.x + "\t" + p.y + "\t" + p.angle);
                p.neighbours = new HashSet<>();
                assignCell(cells, p);
            }
        }
    }

    private static void getNeighbours(Particle p, List<List<Particle>> oldCells, List<List<Particle>> cells) {

        double cellSize = Parser.length / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);

        checkAdjacent(p, cellX, cellY, oldCells, cells);
        checkAdjacent(p, cellX, cellY + 1, oldCells, cells);
        checkAdjacent(p, cellX + 1, cellY + 1, oldCells, cells);
        checkAdjacent(p, cellX + 1, cellY, oldCells, cells);
        checkAdjacent(p, cellX + 1, cellY - 1, oldCells,  cells);

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

        return copy;
    }

    private static void assignCell(ArrayList<List<Particle>> cells, Particle p){
        double cellSize = Parser.length / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);
        if (cellY == Parser.length)
            cellY = 0;
        if (cellX == Parser.length)
            cellX = 0;
        int cellNumber = (int) (cellY * Parser.matrixSize + cellX);
        List <Particle> cellParticles = cells.get(cellNumber);
        cellParticles.add(p);
    }

    private static double getNewAngle(Particle p){
        double angle = 0;
        Set<Particle> neighbours = p.neighbours;

        double sin = 0;
        double cos = 0;
        for (Particle neighbour : neighbours){
            sin += FastMath.sin(neighbour.angle);
            cos += FastMath.cos(neighbour.angle);
        }

        sin = sin / neighbours.size();
        cos = cos / neighbours.size();
        angle = FastMath.atan2(sin, cos);
        double noise =  CliParser.noise * (Math.random() - 1.0 / 2.0);
        angle += noise;

        if (angle > Math.PI){
            angle -= 2*Math.PI;
        }else if (angle < -Math.PI){
            angle += 2*Math.PI;
        }
        return angle;
    }

    private static void changePosition(Particle p){
        p.x += Math.cos(p.angle) * CliParser.speed;
        p.y += Math.sin(p.angle) * CliParser.speed;
        if (p.x >= Parser.length){
            p.x -= Parser.length;
        }
        if (p.y >= Parser.length){
            p.y -= Parser.length;
        }
        if (p.x < 0){
            p.x += Parser.length;
        }
        if (p.y < 0){
            p.y += Parser.length;
        }
    }

    private static void checkAdjacent(Particle particle, double cellX, double cellY, List<List<Particle>> oldCells, List<List<Particle>> cells){

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

        List<Particle> cellParticles = oldCells.get(adjCellNumber);

        for (Particle adjacentParticle : cellParticles){

            if (adjacentParticle.id != particle.id) {

                double distance = particle.getPeriodicContourDistance(adjacentParticle);

                if (distance < CliParser.interactionRadius) {
                    particle.addNeighbour(adjacentParticle);
                    boolean found = false;
                    for (List<Particle> newParticles : cells){
                        for(Particle newParticle : newParticles){
                            if (newParticle.id == adjacentParticle.id){
                                newParticle.addNeighbour(particle);
                                found = true;
                                break;
                            }
                        }
                        if (found){
                            break;
                        }
                    }
                }
            }else{
                particle.addNeighbour(particle);
            }
        }
    }

    private static ArrayList<List<Particle>> createCells(){
        ArrayList<List<Particle>> cells = new ArrayList<List<Particle>>(Parser.matrixSize * Parser.matrixSize);

        for (int i = 0; i < Parser.matrixSize * Parser.matrixSize ; i++){
            cells.add(new LinkedList<Particle>());
        }
        return cells;
    }

}
