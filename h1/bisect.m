eb = (b-a)/2^(n+1);

% Check a and b signs
if f(a)*f(b) >= 0
	disp('f(a)*f(b) < 0 not satisfied')
	break
end

fa = f(a);
fb = f(b);
for k=1:n
	c = (a+b)/2;
	fc = f(c);
	if fc == 0
		break
	end
	if fc*fa < 0
		b = c;
		fb = fc;
	else	
		a = c;
		fa = fc;
	end
end

xc = (a+b)/2;  % New midpoint is our best estimate
fprintf(1, 'The solution is %12.8f +- %10.8f\n', xc, eb)

fc = f(xc);
fprintf(1, 'f(%12.8f) = %12.8f', xc, fc);

g(1) = xc - 2*(10^(-8));
g(2) = xc - 10^(-8);
g(3) = xc;
g(4) = xc + 10^(-8);
g(5) = xc + 2*(10^(-8));
for i=1:5
	v(i) = f(g(i));
end

table = [g' v']
