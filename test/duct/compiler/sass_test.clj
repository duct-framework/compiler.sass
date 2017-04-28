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
  (let [f (io/file "target/test/sass/test.css")]
    (try
      (io/delete-file f true)
      (ig/init config)
      (is (.exists f))
      (is (= (slurp f) "body {\n  font: 100% Helvetica, sans-serif;\n  color: #333; }\n"))
      (finally
        (io/delete-file f true)))))
