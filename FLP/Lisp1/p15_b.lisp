#||b) Write a function to return the sum of all numerical atoms of a list, at any level. 
sum(l1,l2,..,ln) = {
						0, length of list = 0
						l1 + sum(l2,l3,..ln), l1 - numerical atom
						sum(l2,l3,...,ln), l1 - atom but not numerical atom
						sum(l1) + sum(l2,l3,..,ln), otherwise
}
(atom(var)) - returns T if var is an atom
||#

(defun sum(l)
	(cond
		( (null l) 0)
		( (numberp (car l)) (+ (car l) (sum(cdr l))))
		( (atom(car l)) (sum(cdr l)))
		( t (+ (sum(car l)) (sum(cdr l))))
	)
)

(format t "sum of list (1 2 3) = ~d ~%" (sum '(1 2 3)))
(format t "sum of list (1 (a 2 (b 3 (c 4)))) = ~d" (sum '(1 (a 2) (b 3 (c 4)))))