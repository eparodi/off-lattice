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

* ****:

### Animation

The animation can be run by executing the octave file `animation.m` that
accepts the following parameters `animation(simulation_file, speed)`.

* **simulation_file**: Path to the file with the simulation output.
* **speed**: Speed module of the particles.

Example:
```
animation('../out.data', 0.3);
```