% Tim Cheadle
% Math 446 Homework 3

format long;
steps=20;

for j=1:41
  x(1) = -2 + (j-1)*.1;
  for i=1:steps
    f = 0.54*x(i)^5 - 0.81*x(i)^4 - 0.09*x(i)^3 + 0.43*x(i)^2 - 0.17*x(i) + 0.02;
    fp = 2.7*x(i)^4 - 3.24*x(i)^3 - 0.27*x(i)^2 + 0.86*x(i) - 0.17;
    x(i+1) = x(i) - f/fp;
  end
  start(j) = x(1);
  root(j) = x(i+1);
  clear x;
end

a = [start' root']
