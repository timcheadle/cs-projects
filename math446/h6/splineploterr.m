function maxerr=splineploterr(x,y)
%Plots spline from data points
%Input: x,y vectors of data points
%Output: none
n=length(x);
% calculate coefficients of a clamped spline
% with end slopes closely matching cosine
coeff=splinecoeff(x, y, 0, -1);
cla;hold on;    %clear figure window and turn hold on
for i=1:n-1
    x0=linspace(x(i),x(i+1),100);  
    dx=x0-x(i);
    y0=coeff(i,3)*dx; %evaluate using horner's method
    y0=(y0+coeff(i,2)).*dx;
    y0=(y0+coeff(i,1)).*dx+y(i);
    yc=cos(x0);
    semilogy(x0,abs(y0-yc))
    m(i) = max(abs(y0-yc));
end
hold off
maxerr = max(m);
