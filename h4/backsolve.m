for i = n : -1 : 1
	for j = i+1 : n
		b(i) = b(i) - a(i,j)*x(j);
	end
	x(i) = b(i)/a(i,i);
end
