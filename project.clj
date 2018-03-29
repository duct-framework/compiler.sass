(defproject duct/compiler.sass "0.2.1"
  :description "Integrant methods for compiling Sass into CSS"
  :url "https://github.com/duct-framework/compiler.sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [duct/logger "0.2.1"]
                 [integrant "0.6.3"]
                 [io.bit3/jsass "5.5.6"]
                 [medley "1.0.0"]]
  :test-paths ["test" "target/test"])

