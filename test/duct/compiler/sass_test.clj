(ns duct.compiler.sass-test
  (:require [clojure.java.io :as io]
            [clojure.test :refer :all]
            [duct.compiler.sass :as sass]
            [integrant.core :as ig]))

(def config
  {:duct.compiler/sass
   {:source-paths ["test"]
    :output-path  "target/test"}})

(deftest module-test
  (let [expected (io/file "test/sass/test.css")
        actual   (io/file "target/test/sass/test.css")]
    (try
      (io/delete-file actual true)
      (ig/init config)
      (is (.exists actual))
      (is (= (slurp expected) (slurp actual)))
      (finally
        (io/delete-file actual true)))))
