x0 = (pi/2) * (0:(n-1))/(n-1);
y0 = cos(x0);
c = newtdd(x0, y0, n);
x = 0:.01:pi/2;
y = horner(n-1, c, x, x0);
semilogy(x, abs(y - cos(x)));
