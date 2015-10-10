(ns impala.lib
  (:use impala.core))

(defn SUB
  "b := b - a"
  [env a b]
  (SUBLEQ env a b))

(defn ADD
  "b := b + a"
  [env a b]
  (let [Z (temp)]
    (SET env Z 0)
    (SUB env a Z)
    (SUB env Z b)
    (DEL env Z)))

(defn MOV
  "b := a"
  [env a b]
  (let [Z (temp)]
    (SUB env b b)
    (SUB env a Z)
    (SUB env Z b)
    (DEL env Z)))
