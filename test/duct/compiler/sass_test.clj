(ns duct.compiler.sass-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.compiler.sass :as sass]
            [integrant.core :as ig]))

(defmacro with-temp-file [f & body]
  `(try (io/delete-file ~f true)
        ~@body
        (finally (io/delete-file ~f true))))

(def config
  {:duct.compiler/sass
   {:source-paths ["test"]
    :output-path  "target/test"}})

(deftest module-test
  (testing "normal output"
    (let [expected (io/file "test/sass/test.css")
          actual   (io/file "target/test/sass/test.css")]
      (with-temp-file actual
        (ig/init config)
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "compressed output"
    (let [expected (io/file "test/sass/test.compressed.css")
          actual   (io/file "target/test/sass/test.css")]
      (with-temp-file actual
        (ig/init (assoc-in config [:duct.compiler/sass :output-style] :compressed))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual)))))))
