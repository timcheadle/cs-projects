type matrix
type gauss
type gauss_alt1
type gauss_alt2
type backsolve
type backsolve_alt1;
type backsolve_alt2;
type table

matrix
A
b'
gauss; backsolve;
x'
A*x'
a=A;
b_alt1'
gauss_alt1; backsolve_alt1;
x_alt1'
A*x_alt1'
a=A;
b_alt2'
gauss_alt2; backsolve_alt2;
x_alt2'
A*x_alt2'
table
cond(A,inf)
