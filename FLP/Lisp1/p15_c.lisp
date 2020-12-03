#||c)Write a function to return the set of all sublists of a given linear list.
Ex.For list ((1 2 3)((4 5)6))=> ((1 2 3) (4 5) ((4 5) 6))
sublists(l1,l2,..,ln) = {
				[], l - empty list
				sublists(l2,l3,..ln) - l1 - not a list
				sublists(l1) (+) l1 (+) sublists(l2,l3,..ln)
}
||#

(defun insert_last (item list)
  (append list (list item)))

(defun add_to_beginning(item list)
	(cond
		( (null item) list)
		( (cons item list))
	))

(defun sublists(l)
	(cond
		( (null l) nil)
		( (listp (car l)) (append (insert_last (car l) (sublists (car l))) (sublists (cdr l))))
		( (sublists(cdr l)))
	))

(format t "((1 2 3)((4 5)6))=> ~a ~%" (sublists '((1 2 3)((4 5)6))))
;;;(format t "~a ~%" (sublists '(1 (2 3 (4 5 (6 7 (8)))))))
;;;(format t "~a ~%" (sublists '(1 (2 3 (2 3 (2 3 (2 3)))))))
