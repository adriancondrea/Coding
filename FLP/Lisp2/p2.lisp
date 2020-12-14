#||
Write recursive Lisp functions for the following problems (optionally, you may use MAP functions):
 A binary tree is memorised in the following two ways:
(node no-subtrees list-subtree-1 list-subtree-2 ...) (1)
(node (list-subtree-1) (list-subtree-2) ...) (2)
As an example, the tree
A 
/\
B C
  /\
  D E
is represented as follows:
(A 2 B 0 C 2 D 0 E 0) (1)
(A (B) (C (D) (E))) (2)
2. Return the list of nodes on the k-th level of a tree of type (1).
||#


#||
the tree

      a
    /   \
    b    d
   /\    /\
  c  f  e  h
 /  /
i  g

is represented as
(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)
||#


#||traverse_left_subtree(l1,l2,..,lk, number_of_nodes, number_of_edges) = {
		empty list, l - empty
		empty list, number_of_nodes = number_of_edges + 1
		l1 U l2 U traverse_left_subtree(l3,l4,..,lk, number_of_nodes + 1, number_of_edges + l2), otherwise
	}
||#
(defun traverse_left_subtree(tree number_of_nodes number_of_edges) 
	(cond
		((null tree) ())
		((= number_of_nodes (+ 1 number_of_edges)) ())
		(t (cons (car tree) (cons (cadr tree) (traverse_left_subtree (cddr tree) (+ 1 number_of_nodes) (+ (cadr tree) number_of_edges)))))
	)
)

;;;left_subtree(l1,l2,..,ln) = traverse_left_subtree(l3,l4,..,ln,0,0)
(defun left_subtree(tree)
	(traverse_left_subtree (cddr tree) 0 0)
)
(format t "left subtree of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (left_subtree '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)))
(format t "left subtree of (b 2 c 1 i 0 f 1 g 0) = ~a ~%" (left_subtree ' (b 2 c 1 i 0 f 1 g 0)))
(format t "left subtree of (c 1 i 0) = ~a ~%" (left_subtree ' (c 1 i 0)))
(format t "left subtree of (e 0) = ~a ~%" (left_subtree ' (e 0)))




#||
traverse_right_subtree(l1,l2,..,lk, number_of_nodes, number_of_edges) = {
	empty list, l - empty
	l1,l2,..,lk, number_of_nodes = number_of_edges + 1
	traverse_right_subtree(l3,l4,..,lk, number_of_nodes + 1, number_of_edges + l2), otherwise
}
||#
(defun traverse_right_subtree(tree number_of_nodes number_of_edges)
	(cond
		((null tree) ())
		((= number_of_nodes (+ 1 number_of_edges)) tree)
		(t (traverse_right_subtree (cddr tree) (+ 1 number_of_nodes) (+ (cadr tree) number_of_edges)))
	)
)

;;;right_subtree(l1,l2,..,ln) = traverse_right_subtree(l3,l4,..,ln, 0, 0)
(defun right_subtree(tree)
	(traverse_right_subtree (cddr tree) 0 0)
)
(format t "~%right subtree of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%~%" (right_subtree '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0)))

#||
atlevel(l1,l2,..,ln, level) = {
	empty list, level < 1
	empty list, l - empty
	(l1), level = 1
	atlevel(left_subtree(l1,l2,..,ln), level - 1)  U atlevel(right_subtree(l1,l2,..,ln), level -1), otherwise
}
||#
(defun atlevel(tree level)
	(cond
		((null tree) ())
		((< level 1) ())
		((eq level 1) (list (car tree)))
		(t (append (atlevel (left_subtree tree) (- level 1)) (atlevel (right_subtree tree) (- level 1))))
	)
)

(format t "level 1 of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (atlevel '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 1))
(format t "level 2 of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (atlevel '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 2))
(format t "level 3 of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (atlevel '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 3))
(format t "level 4 of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (atlevel '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 4))
(format t "level 0 of (a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) = ~a ~%" (atlevel '(a 2 b 2 c 1 i 0 f 1 g 0 d 2 e 0 h 0) 0))