package ar.edu.itba.ss;

import org.apache.commons.cli.*;

public class CliParser {

    public static String dynamicFile;
    static double interactionRadius = 1;
    static double noise = 0.1;
    static double speed = 0.3;
    static int time = 100;

    private static Options createOptions(){
        Options options = new Options();
        options.addOption("h", "help", false, "Shows this screen.");
        options.addOption("rc", "radius", true, "Radius of interaction between particles.");
        options.addOption("df", "dynamic_file", true, "Path to the file with the dynamic values.");
        options.addOption("n", "noise", true, "Noise of the environment.");
        options.addOption("s", "speed", true, "Speed module of the particles.");
        options.addOption("t", "time", true, "Total time of the simulation.");
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

            if (cmd.hasOption("n")) {
                noise = Double.parseDouble(cmd.getOptionValue("n"));
            }

            if (cmd.hasOption("s")) {
                speed = Double.parseDouble(cmd.getOptionValue("s"));
            }

            if (cmd.hasOption("t")) {
                time = Integer.parseInt(cmd.getOptionValue("t"));
            }
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
