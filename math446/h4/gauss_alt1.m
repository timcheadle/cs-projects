for  j = 1 : n-1
	for i = j+1 : n
		mult = a(i,j)/a(j,j);
		for k = j+1 : n
			a(i,k) = a(i,k) - mult*a(j,k);
		end
		b_alt1(i) = b_alt1(i) - mult*b_alt1(j);
	end
end
