clear
r1 = -0.52169174760555;
steps = 20;
x(1) = 0.5;
for i=1:steps
	x(i+1) = x(i)^3 - exp(cos(x(i))) + 2;
end
err = x - r1;
for i=1:steps
	ratio(i) = err(i+1)/err(i);
end
ratio(steps+1) = 0;
table = [x' err' ratio']
