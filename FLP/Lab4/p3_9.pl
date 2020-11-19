%Generate all permutation of N (N - given) respecting the property: for every 2<=i<=n exists an 1<=j<=i, so |v(i)-v(j)|=1.

/*
candidate(N:integer, I:integer) - suggest a possile element between 1 and n and returns it through I
(i,o)- not deterministic
mathematical model:
candidate(n) = {
				1. n
				2. candidate(n-1), n > 1
				}
*/
candidate(N,N).
candidate(N,I):-
	N>1,
	N1 is N-1,
	candidate(N1,I).



/*
belongsTo(E:Integer, L: list) - returns true if E belongs to L
(i, i) - deterministic
mathematical model:
belongsTo(E, l1,l2,..,ln) = {	
								false, L - empty
								true, E = L1
								belongsTo(E, l2,l3,..,ln), else

							}
*/
belongsTo(E, [H|_]) :-
	E = H, !.
belongsTo(E, [_|T]) :-
	belongsTo(E, T).



/*
addToEnd(E:Integer, L:list, R:list) - adds element E to the end of list L and returns the result in list R
(i, i, o) - deterministic
mathematical model:
addToEnd(E, l1,l2,...,ln) = {
								[E], L - empty
								l1 + addToEnd(E, l2,...ln), else
							}
*/
addToEnd(E, [], [E]).
addToEnd(E, [H|T], [H|T1]) :-
	addToEnd(E, T, T1).


/*
permutations(N:integer, L:list) - generates a solution 
(i,o)- not deterministic
*/
permutations(N,L):-
	candidate(N,I),
	permutations_aux(N,L,1,[I]).



/*
exists_neighbour(i, i)
exists_neighbour(E: integer, L: list) - checks if there exists a neighbour (predecessor or successor) of E in L
deterministic predicate
mathematical model:
	exists_neighbour(E, l1,l2,..,ln) = {
											false, L - empty
											true, E = l1
											exists_neighbour(E, l2,l3,..,ln), else
										}
*/
exists_neighbour(E, [H|_]) :-
	abs(E-H) =:= 1, !.

exists_neighbour(E, [_|T]) :-
	exists_neighbour(E, T).


/*
permutations_aux(N:integer, R:list, L:integer, Col:list) - collects the elements of a permutation (1,2,..N) in the list Col with length L. the collecting stops when L = N. 
Result will be returned in R
%(i,o,i,i)- not deterministic
mathematical model:
permutations_aux(N, L, Col) = {
									Col, L = N
									1. candidate(N)
									2. permutations_aux(N, L+1, addToEnd(candidate(N), Col)), !belongsTo(I, Col) && exists_neighbour(I, Col)
}
*/
permutations_aux(N,Col,N,Col):-!.
permutations_aux(N,L,Lg,[H|T]):-
	candidate(N,I),
	\+ belongsTo(I,[H|T]), % (i,i)
	exists_neighbour(I, [H|T]),
	Lg1 is Lg+1, 
	addToEnd(I, [H|T], R),
	permutations_aux(N,L,Lg1,R).

%findall(X, permutations(4, X), R); true.