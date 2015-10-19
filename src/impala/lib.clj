(ns impala.lib
  (:use impala.core))

(defop SUB
  "b := b - a"
  [a b] []
  (SUBLEQ a b))

(defop ADD
  "b := b + a"
  [a b] [Z]
  (SUB a Z)
  (SUB Z b))

(defop MOV
  "b := a"
  [a b] [Z]
  (SUB b b)
  (SUB a Z)
  (SUB Z b))
