(ns impala.main
  (:gen-class)
  (:use [impala core lib]
        [clojure.string :only [split]]))

(def this-ns *ns*)

(defn str->instruction [s]
  (binding [*ns* this-ns]
    (eval (read-string (str "[" s "]")))))

(defn str->prog [s]
  (->> (split s #"\n")
       (map str->instruction)))

(defn run-prog-string [s]
  (run (str->prog s) true))

(defn -main [& [filename]]
  (run-prog-string (slurp filename)))
