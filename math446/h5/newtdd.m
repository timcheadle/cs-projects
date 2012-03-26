%Program 3.1 Newton Divided Difference Interpolation Method
%Computes coefficients of interpolating polynomial
%Input: x and y are vectors containing the x and y coordinates
%       of the n data points
%Output: coefficients c of interpolating polynomial in nested form
%Use with horner.m to evaluate interpolating polynomial
function c=newtdd(x,y,n)
for j=1:n
  v(j,1)=y(j);
end
for i=2:n
  for j=1:n+1-i
    v(j,i)=(v(j+1,i-1)-v(j,i-1))/(x(j+i-1)-x(j));
  end
end 
for i=1:n
  c(i)=v(1,i);
end
