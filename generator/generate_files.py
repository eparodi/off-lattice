from numpy import random, pi, sin, cos

def generate_dynamic_file(number, length, name, speed):
    f = open(name, 'w')
    f.write('0\n')
    for x in range(0, number):
        angle = random.uniform(-pi/2, pi/2)
        f.write('{x} {y} {vx} {vy}\n'.format(
                x = random.uniform(0, length),
                y = random.uniform(0,length),
                vx = speed * cos(angle),
                vy = speed * sin(angle),
            )
        )

def generate_files(index, number, length, speed=0.03):
    generate_dynamic_file(number, length, str(index) + '-dynamic-' + str(number) + '.ari', speed)

numbers = [100,200,300,400,500,600,700,800,900,1000,2000,3000]

i = 0
for x in numbers:
    i += 1
    generate_files(i, numbers[i-1], 20)
