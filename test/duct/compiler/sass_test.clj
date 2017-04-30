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
   {:source-paths ["test/sass"]
    :output-path  "target/test/output"}})

(deftest init-test
  (testing "normal output"
    (let [expected (io/file "test/sass/test.css")
          actual   (io/file "target/test/output/test.css")]
      (with-temp-file actual
        (ig/init config)
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "compressed output"
    (let [expected (io/file "test/sass/test.compressed.css")
          actual   (io/file "target/test/output/test.css")]
      (with-temp-file actual
        (ig/init (assoc-in config [:duct.compiler/sass :output-style] :compressed))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "sass input"
    (let [expected (io/file "test/sass/test.css")
          actual   (io/file "target/test/output/test3.css")]
      (with-temp-file actual
        (ig/init config)
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual))))))

  (testing "imports"
    (let [expected (io/file "test/sass/test4.css")
          actual   (io/file "target/test/output/test4.css")]
      (with-temp-file actual
        (ig/init config)
        (is (not (.exists (io/file "target/test/output/_reset.css"))))
        (is (.exists actual))
        (is (= (slurp expected) (slurp actual)))))))

(deftest resume-test
  (.mkdirs (io/file "target/test/temp"))
  (let [source (io/file "target/test/temp/test.scss")]
    (with-temp-file source
      (io/copy (io/file "test/sass/test.scss") source)
      (let [config (assoc-in config [:duct.compiler/sass :source-paths] ["target/test/temp"])
            system (ig/init config)]
        (Thread/sleep 1000)
        (io/copy (io/file "test/sass/test2.scss") source)
        (prn (slurp source))
        (ig/resume config system)
        (let [output (io/file "target/test/output/test.css")]
          (is (.exists output))
          (is (= (slurp output) (slurp (io/file "test/sass/test2.css")))))))))
