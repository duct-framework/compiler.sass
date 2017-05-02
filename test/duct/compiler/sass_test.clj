(ns duct.compiler.sass-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.compiler.sass :as sass]
            [integrant.core :as ig]))

(defmacro with-temp-files [fs & body]
  `(let [fs# ~fs]
     (try (doseq [f# fs#] (io/delete-file f# true))
          ~@body
          (finally (doseq [f# fs#] (io/delete-file f# true))))))

(def config
  {:duct.compiler/sass
   {:source-paths ["test/sass"]
    :output-path  "target/test/output"}})

(deftest module-test
  (testing "normal output"
    (let [expected (io/file "test/sass/test.css")
          actual   (io/file "target/test/output/test.css")]
      (with-temp-files [actual]
        (ig/init config)
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "compressed output"
    (let [expected (io/file "test/sass/test.compressed.css")
          actual   (io/file "target/test/output/test.css")]
      (with-temp-files [actual]
        (ig/init (assoc-in config [:duct.compiler/sass :output-style] :compressed))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "different indent"
    (let [expected (io/file "test/sass/test.indent.css")
          actual   (io/file "target/test/output/test.css")]
      (with-temp-files [actual]
        (ig/init (assoc-in config [:duct.compiler/sass :indent] "    "))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "sass input"
    (let [expected (io/file "test/sass/test.css")
          actual   (io/file "target/test/output/test3.css")]
      (with-temp-files [actual]
        (ig/init config)
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "imports"
    (let [expected (io/file "test/sass/test4.css")
          actual   (io/file "target/test/output/test4.css")]
      (with-temp-files [actual]
        (ig/init config)
        (is (not (.exists (io/file "target/test/output/_reset.css"))))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual)))))))
