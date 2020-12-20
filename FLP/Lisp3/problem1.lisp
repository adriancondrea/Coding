#||
Solve the following problems using MAP functions.
1. Write a function to check if an atom is member of a list (the list is non-liniar)
||#

#||
checkMember(l1,l2,..,ln, value) = { (T), l1 = value
									U i = 1, n checkMember(l1, value), l1 - list
									empty list, otherwise
									}
The result of this function is a list containing T for each element = value or if element is a list that contains value.
We use mapcan instead of mapcar, because we want to concatenate the result in one single list, insead of having a non - linear list
If we were to use mapcar instead of mapcan, every time it doesn't find the element it would add nil to the list, and add (T) when it finds it.
ex: 1 is member of (2 3 1): (NIL NIL (T))
Instead, mapcan concatenates the lists
We only have to check the first element of the list to know if the element appears in it or not, thus we use car
||#
(defun checkMember (lst value)
   (car (mapcan #'(lambda (element)
                       (cond
                        	((atom element) (and (equal value element) (list T)))
                        	((listp element) (and  (checkMember element value) (list T)))
                        )
                  )
   		lst)
   )
)


(format t "1 is member of (2 3 1): ~a~%" (checkMember '(2 3 1) 1))
(format t "1 is member of (2 3 (1 2): ~a~%" (checkMember '(2 3 '(1 2)) 1))
(format t "1 is member of (2 3 (4 (1 9))): ~a~%" (checkMember '(2 3 '(4 '(1 9))) 1))
(format t "1 is member of (2 3 (4 (a 9))): ~a~%" (checkMember '(2 3 '(4 '(a 9))) 1))
(format t "1 is member of (a b (c (a d))): ~a~%" (checkMember '(a b '(c '(a d))) 1))
(format t "1 is member of (((1))): ~a~%" (checkMember '(((1))) 1))
(format t "2 is member of ((1 2) (3 4)): ~a~%" (checkMember '((1 2) (3 4)) 2))
