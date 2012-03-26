a = 0;
b = pi/2;

% calculate the maximum error
% note that the 5th derivative of cos is -sin
n = 1
err = (((b-a)/2)^n) / (2^(n-1))
while err > 0.5 * 10^-8
	n = n + 1
	err = (((b-a)/2)^n) / (2^(n-1))
end
