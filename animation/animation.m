function animation(simulation_file, speed)
  fileID = fopen(simulation_file,'r');  
  N = str2num(fgetl(fileID));
  parse = fgetl(fileID);
  while(parse != -1)    
    t = str2num(parse);
    for i = 1:N
      line = fgetl(fileID);
      [x(end+1) y(end+1) angle(end+1)] = sscanf (line, "%f %f %f", "C");
    end
    quiver(x, y, speed, angle);
    title('Autómata Off Lattice');
    xlabel('X position');
    ylabel('Y position');
    grid on;
    pause(1);
    parse = fgetl(fileID);
    x = [];
    y = [];
    angle = [];
  endwhile
endfunction