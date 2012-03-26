x1 = -0.52169174;
x2 = 0.91127861;
x3 = -1.21721705;

be1 = x1.^3 - x1 + 2 - exp(cos(x1));
be2 = x2.^3 - x2 + 2 - exp(cos(x2));
be3 = x3.^3 - x3 + 2 - exp(cos(x3));

k1 = 3*x1.^2 + exp(cos(x1))*sin(x1);
k2 = (-exp(cos(x2))*sin(x2) + 1) / (3*(exp(cos(x2)) + x2 - 2)^(2/3));
k3 = (x3*sin(x3)*exp(cos(x3)) + 2*exp(cos(x3)) + x3 - 4) / x3.^3;
