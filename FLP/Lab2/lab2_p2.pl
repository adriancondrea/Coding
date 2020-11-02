%b. Define a predicate to determine the greatest common divisor of all numbers from a list.

%gcd(number1, number2, result)
%gcd(A: integer, B: integer, Rez: integer)
%gcd(i, i, o)
%deterministic predicate
gcd(0, B, B) :- !.		%if a is 0, gcd = b, then cut
gcd(A, 0, A) :- !.		%if b = 0, gcd = a, then cut
gcd(A, A, A) :- !.		%if a = b, gcd = a, then cut

gcd(A, B, Rez) :-		%if a > b, compute gcd(a-b, b)
	A > B,				
	A1 is A - B,
	gcd(A1, B, Rez).

gcd(A, B, Rez) :- 		%if b > a, compute gcd(a, b-a)
	B > A,
	B1 is B - A,
	gcd(A, B1, Rez).

%gcdList(List, Result)
%gcdList(L: list, R: integer)
%gcdList(i, o)
%deterministic predicate
gcdList([], -1) :-		%if the list is empty, return -1 as the result of gcd, then do a cut
	write('Empty list! '),
	!.

gcdList([H |[]], H) :- !.		%gcd of a list with one element is that element

gcdList([H | T], R) :-			%for a list with more than one element, compute the gcd of its tail
								%in a variable G, then call gcd between the head of the list and G,
								%and store the result in R, then do a cut
	gcdList(T, G),
	gcd(H, G, R),
	!.