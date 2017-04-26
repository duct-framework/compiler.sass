(ns duct.compiler.sass
  (:require [integrant.core :as ig]))

(derive :duct.compiler/sass :duct/compiler)

(defmethod ig/init-key :duct.compiler/sass [_ options]
  [])
