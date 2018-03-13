# off-lattice
Implementation of an off lattice automaton.

## Compilation
### Simulation

```
mvn package
```

## Execution
### Simulation

```
java -jar off-lattice-1.0-SNAPSHOT-jar-with-dependencies.jar
```
Parameters:

* -df, --dynamic_file <arg>:   Path to the file with the dynamic values.
* -h,--help: Shows the help.
* -l,--length <arg>: Length of the side.
* -n,--noise <arg>: Noise of the environment.
* -pc,--periodic_contour: Enables periodic contour conditions.
* -rc,--radius <arg>: Radius of interaction between particles.
* -s,--speed <arg>: Speed module of the particles.

### Animation

The animation can be run by executing the octave file `animation.m` that
accepts the following parameters `animation(simulation_file, speed)`.

* **simulation_file**: Path to the file with the simulation output.
* **speed**: Speed module of the particles.
* **animation_speed**: Speed of the animation.

Example:
```
animation('../out.data', 0.3, 10);
```