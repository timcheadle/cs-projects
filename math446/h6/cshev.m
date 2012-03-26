a = 0;
b = pi/2;

% get n chebyshev nodes from a to b
x0(n:-1:1) = ((b-a)/2) * cos((2*(0:n-1)*pi)/(2*n)) + ((b+a)/2);
y0 = cos(x0);

% perform newton's divided differences
c = newtdd(x0, y0, n);
x = a : 0.01 : b;
y = horner(n-1, c, x, x0);

% calculate the actual function
yc = cos(x);
