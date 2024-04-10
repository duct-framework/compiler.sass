(defproject duct/compiler.sass "0.2.1"
  :description "Integrant methods for compiling Sass into CSS"
  :url "https://github.com/duct-framework/compiler.sass"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [duct/logger "0.3.0"]
                 [integrant "0.8.1"]
                 [io.bit3/jsass "5.11.0"]
                 [dev.weavejester/medley "1.7.0"]]
  :profiles {:test {:dependencies [[org.clojure/data.json "2.5.0"]]}}
  :test-paths ["test" "target/test"])

