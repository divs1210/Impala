# Impala
## Pedagogical Bytecode Interpreter

Impala is a simple bytecode interpreter written in Clojure.
Written to learn from and teach making use of.


## Usage

Write a simple program in impala bytecode and execute it,
printing the whole lifecycle to `stdout`.

```clojure
(use 'impala.core
     'impala.lib)

(let [prog [[SET :a  5]
            [SET :b  3]
            [ADD :a :b]]]
  (run prog true))
```

## License

Impala is licensed under [wtfpl](http://www.wtfpl.net/) and is effectively in the public domain.
