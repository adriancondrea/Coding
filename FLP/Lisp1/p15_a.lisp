#||
a) Write a function to insert an element E on the n-th position of a linear list.
insert
insert_at_position(e, pos, l1,l2,..,ln) = {
											l1,l2,..ln, pos < 0
											e + l1,l2,..,ln, pos = 0
											nil, list is empty
											l1 + insert_at_position(e, pos - 1, l2,..,ln), otherwise			
										}
||#

(defun insert_at_position(element position list)
	(cond
		( (< position 0) list)
		( (= position 0) (cons element list))
		( (null list) nil)
		( (cons (car list) (insert_at_position element (- position 1) (cdr list))))
	))
(format t "initial list: ~a ~%" '(1 2 3))
(format t "insert at position 0: ~a ~%" (insert_at_position 99 0 '(1 2 3)))
(format t "insert at position -1 (invalid, lists stays unchanged): ~a ~%" (insert_at_position 99 -1 '(1 2 3)))
(format t "insert at position 2: ~a ~%" (insert_at_position 99 2 '(1 2 3)))
(format t "insert at last position: ~a ~%" (insert_at_position 99 3 '(1 2 3)))
(format t "insert at position > length (invalid, lists stays unchanged): ~a ~%" (insert_at_position 99 400 '(1 2 3)))