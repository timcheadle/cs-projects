for i = n : -1 : 1
	for j = i+1 : n
		b_alt1(i) = b_alt1(i) - a(i,j)*x(j);
	end
	x_alt1(i) = b_alt1(i)/a(i,i);
end
