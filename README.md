# Impala
## Simple, extensible bytecode interpreter

Impala is a simple bytecode interpreter written in Clojure.
Written to learn from and to teach making use of.


## Usage

Load everything into namespace.

```clojure
(use '[impala core lib])
```

Write a simple program in impala bytecode and execute it,
printing the whole lifecycle to `stdout`.

```clojure
(let [prog [[SET :a  5]
            [SET :b  3]
            [ADD :a :b]]]
  (run prog true))
```

We can even extend the instruction set by defining our own opcodes

* in Clojure
```clojure
(defn ADD
  "b := b + a"
  [env a b]
  (swap! env update-in [:vars b] + (-> @env :vars a)))
```

or

* __in Impala byte code__!
```clojure
;; from impala.lib
(defop ADD
  "b := b + a"
  [a b] [Z]
  (SET Z 0)
  (SUB a Z)
  (SUB Z b))
```

At the heart of this capability is the [SUBLEQ](https://en.wikipedia.org/wiki/One_instruction_set_computer#Subtract_and_branch_if_less_than_or_equal_to_zero) primitive, which is Turing Equivalent.

In this example, `Z` is a temporary register created every time `ADD` is called, and deleted once it's done executing.
An opcode may use multiple temporary registers.

### Standalone Interpreter

At the terminal, run

```bash
lein uberjar

./impala test/impala/test.imp
```


## License

Impala is licensed under [wtfpl](http://www.wtfpl.net/) and is effectively in the public domain.
