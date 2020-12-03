#||
d) Write a function to test the equality of two sets, without using the difference of two sets.
belongs(list, element)
belongs(l1,l2,..,ln, e) = {
							false, l - empty
							true, l1 = e
							belongs(l2,l3,..,ln, e), otherwise
}

all_elements_belong(set1, set2)
all_elements_belong(s1,s2,..sn, q1,q2,..,qm) = {
													true, s - empty set
													all_elements_belong(s2,s3,..,sn, q1,q2,..,qn), belongs(q, s1)
													false, otherwise

}
||#

(defun belongs(list element)
	(cond
		( (null list) nil)
		( (eq element (car list)) t)
		( (belongs (cdr list) element))
		))

(defun all_elements_belong(set1 set2)
	(cond
		( (null set1) t)
		( (belongs set2 (car set1)) (all_elements_belong (cdr set1) set2))
		( nil)
		))

(defun equal_sets(set1 set2)
	(cond
		( (and (all_elements_belong set1 set2) (all_elements_belong set2 set1)))
		))

(format t "testing belongs: ~%")
(format t "1 belongs to (1 2 3): ~a ~%" (belongs '(1 2 3) 1))
(format t "2 belongs to (1 2 3): ~a ~%" (belongs '(1 2 3) 2))
(format t "3 belongs to (1 2 3): ~a ~%" (belongs '(1 2 3) 3))
(format t "99 belongs to (1 2 3): ~a ~%" (belongs '(1 2 3) 99))

(format t "~%testing set equality: ~%")
(format t "(1 2 3) = (3 2 1): ~a ~%" (equal_sets '(1 2 3) '(3 2 1)))
(format t "(1 2 3 4) = (3 2 4 1): ~a ~%" (equal_sets '(1 2 3 4) '(3 2 4 1)))
(format t "(1 2 3) = (1 2): ~a ~%" (equal_sets '(1 2 3) '(1 2)))
(format t "(1 2) = (1 2 3): ~a ~%" (equal_sets '(1 2) '(1 2 3)))
(format t "() = (): ~a ~%" (equal_sets '() '()))
(format t "() = (1 2 3) ~a ~%" (equal_sets '() '(1 2 3)))
(format t "(1 2 3) = () ~a ~%" (equal_sets '(1 2 3) '()))
(format t "(a b) = (b a): ~a ~%" (equal_sets '(a b) '(b a)))