% Tim Cheadle
% Math 446 Homework 3

format long;
steps=20;

% iterate using newton's method
for i=1:steps
  f = 0.54*x(i).^5 - 0.81*x(i).^4 - 0.09*x(i).^3 + 0.43*x(i).^2 - 0.17*x(i) + 0.02;
  fp = 2.7*x(i).^4 - 3.24*x(i).^3 - 0.27*x(i).^2 + 0.86*x(i) - 0.17;
  x(i+1) = x(i) - f/fp;
end
t = x(steps+1);
e = x - t;
for i=1:steps-1
  r(i+1) = (x(i+1) - t) / (x(i) - t);
end
r(1) = 0;
r(steps+1) = 0;

% print out convergence table
a = [x' e' r']

clear x e r a;
