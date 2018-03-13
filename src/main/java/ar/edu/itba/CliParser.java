package ar.edu.itba;

import org.apache.commons.cli.*;

public class CliParser {

    public static String dynamicFile;
    static double interactionRadius;
    static boolean periodicContour = false;
    static double noise;
    static double speed;

    private static Options createOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Shows this screen.");
        options.addOption("rc", "radius", true, "Radius of interaction between particles.");
        options.addOption("df", "dynamic_file", true, "Path to the file with the dynamic values.");
        options.addOption("pc", "periodic_contour", false, "Enables periodic contour conditions.");
        options.addOption("n", "noise", true, "Noise of the environment.");
        options.addOption("s", "speed", true, "Noise of the environment.");
        return options;
    }

    public static void parseOptions(String[] args){
        Options options = createOptions();
        CommandLineParser parser = new BasicParser();

        try{
            CommandLine cmd = parser.parse(options, args);
            if(cmd.hasOption("h")){
                help(options);
            }


            if (!cmd.hasOption("df")){
                System.out.println("You must specify a dynamic file!");
                System.exit(1);
            }

            dynamicFile = cmd.getOptionValue("df");

            if (cmd.hasOption("rc")){
                interactionRadius = Double.parseDouble(cmd.getOptionValue("rc"));
            }
            if (cmd.hasOption("pc")){
                periodicContour = true;
            }

            noise = Double.parseDouble(cmd.getOptionValue("n"));
            speed = Double.parseDouble(cmd.getOptionValue("s"));
        }catch (Exception e){
            System.out.println("Command not recognized.");
            help(options);
        }
    }

    private static void help(Options options){
        HelpFormatter helpFormatter = new HelpFormatter();
        helpFormatter.printHelp("Main", options);
        System.exit(0);
    }
}
