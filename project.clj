(defproject duct/compiler.sass "0.1.0"
  :description "Integrant methods for compiling Sass into CSS"
  :url "https://github.com/duct-framework/compiler.sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [duct/logger "0.1.1"]
                 [integrant "0.4.0"]
                 [io.bit3/jsass "5.5.0"]
                 [medley "1.0.0"]]
  :test-paths ["test" "target/test"])

