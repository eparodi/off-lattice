import numpy as np

SPEED = 0.03

with open('output.data') as f:
    numberOfParticles = None
    particle = 0
    velocity = [0,0]
    time = None
    for line in f:
        if (not numberOfParticles):
            numberOfParticles = int(line)
            continue
        if (time == None):
            time = int(line)
            continue
        numbers = line.split()
        angle = float(numbers[-1])
        velocity[0] += np.cos(angle)
        velocity[1] += np.sin(angle)
        particle += 1
        if (particle == numberOfParticles):
            particle = 0
            module = np.sqrt(velocity[0] ** 2 + velocity[1] ** 2)
            print("{} {}".format(
                    time,
                    module /(numberOfParticles),
                )
            )
            velocity = [0,0]
            numberOfParticles = None
            time = None
