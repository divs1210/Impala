(ns impala.core)

;; Primitives
;; ==========
(defn GET
  "Get value of a"
  [env a]
  (-> @env :vars a))

(defn SET
  "a := val"
  [env a val]
  (swap! env update-in [:vars] assoc a val))

(defn DEL
  "Delete variable (free memory)"
  [env a]
  (swap! env update-in [:vars] dissoc a))

(defn GOTO
  "Move IP to given index"
  [env inst#]
  (swap! env assoc :IP inst#))

(defn SUB*
  "b := b - a"
  [env a b]
  (SET env b (- (or (GET env b) 0)
                (GET env a))))


;; Compound primitive
;; ==================
(defn SUBLEQ
  "Turing equivalent instruction that can be used to
  implement other 'primitives'. See lib.clj for more."
  [env a b & [c]]
  (SUB* env a b)
  (if (and (-> c nil? not)
           (neg? (GET env b)))
    (GOTO env c)
    env))


;; VM Helpers
;; ==========
(defn temp
  "Returns a randomly generated variable name."
  []
  (keyword (gensym)))

(defn mk-env
  "Returns a fresh environment with the given program ready
  to be executed."
  [prog]
  (atom {:instr-q (vec prog)
         :IP 0
         :vars {}}))


;; Interpreter
;; ===========
(defn step
  "Executes the instruction pointed at by the IP.
  Returns the updated env."
  [env]
  (let [    instr-q (:instr-q @env)
                 IP (:IP @env)
        [op & args] (instr-q IP)]
    (apply op env args)
    env))

(defn run
  "Runs the given program in a new environment.
  Returns the updated env.
  Prints state at each step to stdout if supplied
  a truthy second argument."
  [prog & [print?]]
  (let [env (mk-env prog)
        println (if print? println (fn [& args]))]
    (println "\n=== start ===")
    (while (< (-> @env :IP) (-> @env :instr-q count))
      (step env)
      (swap! env update-in [:IP] inc)
      (println (dissoc @env :instr-q)))
    (println "==== end ====")
    env))