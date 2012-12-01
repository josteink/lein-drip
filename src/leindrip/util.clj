(ns leindrip.util
  (:use leindrip.platform)
  (:import java.io.File))

; utility methods

(defn get-home []
  (System/getProperty "user.home"))

(defn path-combine [path1 path2]
  (let [fs-object (File. path1 path2)]
    (.getAbsolutePath fs-object)))

(defn get-leindrip-folder []
  (let [home (get-home)]
    (path-combine home (:leindrip-folder platform))))

(defn get-leindrip-executable []
  (path-combine (get-leindrip-folder) (:drip-executable platform)))

(defn fs-object [path]
  (File. path))

(defn path-exists? [path]
  (.exists (fs-object path)))

(defn is-directory? [path]
  (.isDirectory (fs-object path)))

(defn is-file? [path]
  (.isFile (fs-object path)))
