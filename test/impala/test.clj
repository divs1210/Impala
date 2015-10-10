(ns impala.test
  (:require [clojure.test :refer :all])
  (:use impala.core
        impala.lib))

(deftest e2e-test
  (testing "Environment in expected state at the end of execution."
    (let [prog [[SET :a 5]
                [SET :b 3]
                [ADD :a :b]
                [SUB :a :b]
                [MOV :b :a]]
          env (run prog)]
      (is (= (:IP   @env) 5))
      (is (= (:vars @env) {:a 3 :b 3})))))
