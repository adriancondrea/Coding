/*b. For a heterogeneous list, formed from integer numbers and list of numbers, write a predicate to sort every
sublist, keeping the doubles.*/

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
minimumElement([H| T], H) :- minimumElement(T, Y), H<Y, !.


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


/*
selectionSortSublists(L: list, R: list)
selectionSortSublists(i, o)
deterministic predicate
selectionSortSublists(l1,l2,...,ln) = {
										l1 U selectionSortSublists(l2,l3,...,ln), l1 - integer
										selectionSort(l1) U selectionSortSublists(l2,l3,...ln), l1 - list
									  }
*/
selectionSortSublists([], []) :- !.
selectionSortSublists([H | T1], [R1| T2]) :- \+ number(H), selectionSort(H, R1), selectionSortSublists(T1, T2), !. %this means that H is a sublist, so sort it.
selectionSortSublists([H| T1], [H| T2]) :- selectionSortSublists(T1, T2).		%just copy the element in the new list and move to the next one



