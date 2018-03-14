import numpy as np

SPEED = 0.3

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
        numbers = line.strip()
        angle = float(numbers[-1])
        velocity[0] += np.cos(angle) * SPEED
        velocity[1] += np.sin(angle) * SPEED
        particle += 1
        if (particle == numberOfParticles):
            particle = 0
            module = np.sqrt(velocity[0] ** 2 + velocity[1] ** 2)
            print("{} {}".format(
                    time,
                    module /(numberOfParticles * SPEED),
                )
            )
            velocity = [0,0]
            numberOfParticles = None
            time = None
