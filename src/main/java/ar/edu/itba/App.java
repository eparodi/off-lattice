package ar.edu.itba;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static java.lang.System.exit;

public class App
{
    public static void main( String[] args ) {

        CliParser.parseOptions(args);
        try{
            Parser.dynamicSystemParse();
            offLattice();
        }catch(Exception e){
            System.out.println("There was an error while executing the program, please try again");
            exit(1);
        }

    }

    private static void offLattice() throws CloneNotSupportedException {
        ArrayList<List<Particle>> cells = new ArrayList<List<Particle>>(Parser.matrixSize * Parser.matrixSize);

        for (int i = 0; i < Parser.matrixSize * Parser.matrixSize ; i++){
            cells.add(new LinkedList<Particle>());
        }

        for (Particle p : Parser.particles){
            assignCell(cells, p);
        }

        for (int i = 0; i < 100; i++){
            ArrayList<List<Particle>> oldValues = cloneCells(cells);
            for (int j = 0 ; j < cells.size(); j++){
                List<Particle> cell = cells.get(j);
                for (Particle p: cell){
                    double angle = getNewAngle(oldValues, p);
                    changePosition(p);
                    p.angle = angle;
                    assignCell(cells, p);
                }
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
        double cellSize = Parser.AREA_LENGTH / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);
        int cellNumber = (int) (cellY * Parser.matrixSize + cellX);
        List <Particle> cellParticles = cells.get(cellNumber);
        cellParticles.add(p);
    }

    private static double getNewAngle(ArrayList<List<Particle>> cells, Particle p){
        double angle = 0;
        double cellSize = Parser.AREA_LENGTH / Parser.matrixSize;
        double cellX = Math.floor(p.x / cellSize);
        double cellY = Math.floor(p.y / cellSize);

        List<Particle> particles = new LinkedList<Particle>();

        particles.addAll(checkAdjacent(p, cellX, cellY, cells));
        particles.addAll(checkAdjacent(p, cellX, cellY + 1, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY + 1, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY, cells));
        particles.addAll(checkAdjacent(p, cellX + 1, cellY - 1, cells));
        for (Particle particle : particles){
            angle += particle.angle;
        }

        angle = angle / particles.size();
        double noise =  CliParser.noise * (Math.random() - 1.0 / 2.0);
        angle += noise;
        return angle;
    }

    private static void changePosition(Particle p){
        p.x += Math.cos(p.angle) * Parser.speed;
        p.y += Math.sin(p.angle) * Parser.speed;
        if (p.x > Parser.AREA_LENGTH){
            if (CliParser.periodicContour){
                p.x -= Parser.AREA_LENGTH;
            }else{
                p.x -= p.x - Parser.AREA_LENGTH;
            }
        }
        if (p.y > Parser.AREA_LENGTH){
            if (CliParser.periodicContour){
                p.y -= Parser.AREA_LENGTH;
            }else{
                p.y -= p.y - Parser.AREA_LENGTH;
            }
        }
        if (p.x < 0){
            if (CliParser.periodicContour){
                p.x += Parser.AREA_LENGTH;
            }else{
                p.x = - p.x;
            }
        }
        if (p.y < 0){
            if (CliParser.periodicContour){
                p.y += Parser.AREA_LENGTH;
            }else{
                p.y += - p.y;
            }
        }
    }

    private static List<Particle> checkAdjacent(Particle particle, double cellX, double cellY, ArrayList<List<Particle>> cells){
        List<Particle> particles = new LinkedList<Particle>();
        if (CliParser.periodicContour) {
            return particles;
        }else {
            if (cellX >= Parser.matrixSize || cellX < 0 || cellY >= Parser.matrixSize || cellY < 0) {
                return particles;
            }
        }

        int adjCellNumber = (int) (cellY * Parser.matrixSize + cellX);

        List<Particle> cellParticles = cells.get(adjCellNumber);

        for (Particle adjacentParticle : cellParticles){

            double distance;

            distance = particle.getDistanceTo(adjacentParticle);

            if (distance < CliParser.interactionRadius){
                particles.add(adjacentParticle);
            }
        }
        return particles;
    }


}
