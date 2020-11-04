%a. Sort a list with keeping double values in resulted list. E.g.: [4 2 6 2 3 4] --> [2 2 3 4 4 6]

/*
minimumElement(L: list, E : integer)
minimumElement(i, o)
deterministic predicate
minimumElement(l1,l2,...,ln) = {
									l1, l1 <= minimumElement(l2,l3,..ln)
									minimumElement(l2,l3,..,ln), l1 > minimumElement(l2,l3,..ln)
								}
*/
minimumElement([R], R) :- !.			%minimumElement of a list with one Element
minimumElement([], -1) :- !.			%minimumElement of an empty list
minimumElement([X, Y], X) :- X =< Y, !.
minimumElement([X, Y], Y) :- X > Y, !.
minimumElement([H| T], Y) :- minimumElement(T, Y), H >= Y, !.		
minimumElement([H| T], H) :- minimumElement(T, Y), H<Y, !.		%if head < minimumElement(sublist), we set the minimumElement to be head


/*
Eliminates the first occurencce of E in the list L, returns false if the element doesn't appear in the list
eliminateElement(L:list, E:integer (Element to be eliminated), R: list (resulting list))
eliminateElement(i,i,o)
deterministic predicate
eliminateElement(l1,l2,...ln) = {
									l1 U eliminateElement(l2,l3,...ln), l1 != E
									(l2,l3,...ln), otherwise
								}
*/
eliminateElement([E| T], E, T) :- !.
eliminateElement([H| T], E, [H |R]) :- eliminateElement(T, E, R).



/*
selectionSort(L: list, R: list)
selectionSort(i, o)
deterministic predicate
selectionSort(l1,l2,...,ln) = {
								l1, if length(l) = 1
								minimumElement(l1,...ln) U selectionSort(l1,l2,...,ln - minimumElement), otherwise
							  }
*/
selectionSort([], []) :- !.
selectionSort([R], [R]) :- !.
selectionSort(L, [M|T]) :- minimumElement(L, M), eliminateElement(L, M, R), selectionSort(R, T).