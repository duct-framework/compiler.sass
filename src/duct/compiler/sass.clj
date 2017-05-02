(ns duct.compiler.sass
  (:import [io.bit3.jsass Options OutputStyle]
           [io.bit3.jsass.context FileContext])
  (:require [clojure.java.io :as io]
            [clojure.string :as str]
            [duct.logger :as log]
            [integrant.core :as ig]
            [medley.core :as m]))

(def ^:private compiler (io.bit3.jsass.Compiler.))

(def ^:private re-ext
  (re-pattern (str "\\.([^\\" java.io.File/separator "]*?)$")))

(defn- file-ext [f]
  (second (re-find re-ext (str f))))

(defn- find-files [dir exts]
  (filter (comp (set exts) file-ext) (file-seq (io/file dir))))

(defn- relative-path [dir file]
  (str (.relativize (.toPath (io/file dir)) (.toPath (io/file file)))))

(defn- css-output-file [input-file source-path output-path]
  (-> (relative-path source-path input-file)
      (str/replace re-ext ".css")
      (as-> f (io/file output-path f))))

(def ^:private re-partial
  (re-pattern (str "_[^\\" java.io.File/separator "]*?$")))

(defn- partial-file? [f]
  (re-find re-partial (str f)))

(defn- file-mapping-1 [source-path output-path]
  (->> (find-files source-path ["scss" "sass"])
       (remove partial-file?)
       (map (juxt identity #(css-output-file % source-path output-path)))
       (into {})))

(defn- file-mapping [{:keys [source-paths output-path]}]
  (into {} (map #(file-mapping-1 % output-path)) source-paths))

(defn- timestamp-map [files]
  (into {} (map (juxt identity #(.lastModified %)) files)))

(def ^:private output-styles
  {:compact    OutputStyle/COMPACT
   :compressed OutputStyle/COMPRESSED
   :expanded   OutputStyle/EXPANDED
   :nested     OutputStyle/NESTED})

(defn- make-options [opts]
  (doto (Options.)
    (.setOutputStyle (output-styles (:output-style opts :nested)))
    (.setIndent (:indent opts "  "))))

(defn- compile-sass [in out {:keys [logger] :as opts}]
  (log/log logger :info ::compiling {:in (str in) :out (str out)})
  (let [context (FileContext. (.toURI in) (.toURI out) (make-options opts))
        result  (.compile compiler context)]
    (.mkdirs (.getParentFile out))
    (spit out (.getCss result))))

(defn- file-modified? [file->timestamp file]
  (if-let [timestamp (file->timestamp file)]
    (< timestamp (.lastModified file))
    true))

(defn- compile-results [in->out]
  {:output     (map (comp str val) in->out)
   :timestamps (timestamp-map (keys in->out))})

(derive :duct.compiler/sass :duct/compiler)

(defmethod ig/init-key :duct.compiler/sass [_ opts]
  (let [in->out (file-mapping opts)]
    (doseq [[in out] in->out]
      (compile-sass in out opts))
    (compile-results in->out)))

(defmethod ig/resume-key :duct.compiler/sass [key opts old-opts {:keys [timestamps]}]
  (if (= (dissoc opts :logger) (dissoc old-opts :logger))
    (let [in->out (file-mapping opts)]
      (doseq [[in out] in->out]
        (when (file-modified? timestamps in)
          (compile-sass in out opts)))
      (compile-results in->out))
    (ig/init-key key opts)))
