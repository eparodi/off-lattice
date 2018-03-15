import os
import subprocess
import csv
import numpy as np

files = ['dynamic-40-3.1.ari','dynamic-100-5.0.ari','dynamic-400-10.0.ari','dynamic-4000-31.6.ari','dynamic-10000-50.0.ari']
noise = [0.1 * i for i in range(0,50)]
s_time = 10

with open('results.csv', 'w') as f:
    csv_writer = csv.writer(f, delimiter=';',
                            quotechar='|', quoting=csv.QUOTE_MINIMAL)
    header = [x for x in files]
    header.insert(0, 'file')

    csv_writer.writerow(header)
    for x in noise:
        values = [x]
        for y in files:
            command = 'java -jar ./target/off-lattice-1.0-SNAPSHOT-jar-with-dependencies.jar \
            -df ./generator/{file} -rc 1 -t {time} -n {noise}'.format(
                    file=y,
                    noise=x,
                    time=s_time,
                )
            print(command)
            p = subprocess.Popen(command, shell=True, stdout=subprocess.PIPE, stderr=subprocess.STDOUT)
            numberOfParticles = None
            particle = 0
            velocity = [0,0]
            time = None
            va = 0
            for line in p.stdout.readlines():
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
                    va = module/numberOfParticles;
                    velocity = [0,0]
                    numberOfParticles = None
                    time = None
            values.append(va)
            retval = p.wait()

        csv_writer.writerow(values)
