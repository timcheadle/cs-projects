max_bn1 = 0;
max_bn2 = 0;
max_bd = 0;
max_xn1 = 0;
max_xn2 = 0;
max_xd = 0;

bs1 = abs(B - B_alt1);
xs1 = abs(x - x_alt1);
bs2 = abs(B - B_alt2);
xs2 = abs(x - x_alt2);

for i = 1 : n
	if bs1(i) > max_bn1
		max_bn1 = bs1(i);
	end
	if xs1(i) > max_xn1
		max_xn1 = xs1(i);
	end
	if bs2(i) > max_bn2
		max_bn2 = bs2(i);
	end
	if xs2(i) > max_xn2
		max_xn2 = xs2(i);
	end
	if abs(B(i)) > max_bd
		max_bd = abs(B(i));
	end
	if abs(x(i)) > max_xd
		max_xd = abs(x(i));
	end
end

berror1 = max_bn1 / max_bd
xerror1 = max_xn1 / max_xd
berror2 = max_bn2 / max_bd
xerror2 = max_xn2 / max_xd
