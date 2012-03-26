%Program 0.1 Horner's Method
%Evaluates polynomial from nested form
%Input: degree d of polynomial,
%       array of coefficients c of polynomial in nested form,
%       x-coordinate x at which to evaluate, and
%       array of d base points b
%Output: value of polynomial at x
function y=horner(d,c,x,b) 
if nargin<4, b=zeros(d,1); end
y=c(d+1); 
for i=d:-1:1
  y = y.*(x-b(i))+c(i);
end

