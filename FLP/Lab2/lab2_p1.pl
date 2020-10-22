%a. Insert an element on the position n in a list.

%length(List, Length)
%length(A: list, L: integer)
%length(i, o)
myLen([], 0) :- !.		%length of an empty list is 0
myLen([_|T], L) :-
	myLen(T, L1),		%compute the length of the tail
	L is L1 + 1.		%L = length of the tail + 1
	

%insertElement(L: list, P: integer, V: Element, R: list)
%insertElement(List, Position, Value, ResultList)
%insertElement(i, i, i, o)

%invalid position, leave the list unaffected
insertElement(L, P, _, L) :-
	P < 1,
	write('Invalid Position!'),
	!.

insertElement([], 1, V, [V]) :- !.				%insert at last position

insertElement([], _, _, []) :- !. 				%when the list is empty, return an empty list, then cut 
insertElement(L, 1, V, [V | L]) :- !. 			%when Position = 1, add the value and then the rest of the list, then cut
insertElement([H | T], P, V, [H | Rez]) :-		%when Position > 1, 
	P2 is P - 1,
	insertElement(T, P2, V, Rez).