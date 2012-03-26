for i = 1 : n
	sum = 0;
	for j = 1 : n
		a(i,j) = ((i-1)/(n-1))^(j-1);
		sum = sum + a(i,j);
	end
	b(i) = sum;
	b_alt1(i) = sum + ((-1)^i)*delta;
	b_alt2(i) = sum + delta;
end

A = a;
B = b;
B_alt1 = b_alt1;
B_alt2 = b_alt2;
