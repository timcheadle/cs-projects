function y=cos1(x)
	n = 9;

	% base points
	xb = (pi/2) * (0:(n-1))/(n-1);
	yb = cos(xb);

	% find the interpolating polynomial
	c = newtdd(xb, yb, n);

	% for each element in x, move to cosine's
	% fundamental domain and evaluate
	for i=1:size(x,2)
		xm = mod(x(i), 2*pi);

		% keep track of the right sign
		s = 1;

		if xm > pi
			xm = xm - pi;
			s = -1;
		end
		if xm > pi/2
		xm = pi - xm;
		s = s * -1;
		end
		
		y(i) = s * horner(n-1, c, xm, xb);
	end
