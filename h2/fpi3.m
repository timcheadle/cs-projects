clear
r3 = -1.21721705214857;
steps = 25;
x(1) = -1;
for i=1:steps
	x(i+1) = (exp(cos(x(i))) - 2)/(x(i)^2) + 1/x(i);
end
err = x - r3;
for i=1:steps
	ratio(i) = err(i+1)/err(i);
end
ratio(steps+1) = 0;
table = [x' err' ratio']
