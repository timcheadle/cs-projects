clear
r2 = 0.91127861104362;
steps = 15;
x(1) = 0.5;
for i=1:steps
	x(i+1) = (exp(cos(x(i))) + x(i) - 2)^(1/3);
end
err = x - r2;
for i=1:steps
	ratio(i) = err(i+1)/err(i);
end
ratio(steps+1) = 0;
table = [x' err' ratio']
