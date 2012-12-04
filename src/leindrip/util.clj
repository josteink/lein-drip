(ns leindrip.util
  (:use leindrip.platform)
  (:import (java.io File BufferedReader FileReader)))

                                        ; file-related utility methods

(defn get-home []
  (System/getProperty "user.home"))

(defn fs-object [path]
  (File. path))

(defn path-combine [path1 path2 & more-paths]
  (let [fs-object (File. path1 path2)
        combined (.getAbsolutePath fs-object)]
    (if (< 0 (count more-paths))
      (recur combined (first more-paths) (rest more-paths))
      combined)))

(defn get-leindrip-folder []
  (let [home (get-home)]
    (path-combine home (:leindrip-folder platform))))

(defn get-leindrip-executable []
  (path-combine (get-leindrip-folder) (:drip-executable platform)))

(defn get-leinrc-location []
  (path-combine (get-home) (:lein-folder platform) "leinrc"))

(defn get-drip-line []
  (str "LEIN_JAVA_CMD=" (get-leindrip-executable)))

(defn path-exists? [path]
  (.exists (fs-object path)))

(defn is-directory? [path]
  (.isDirectory (fs-object path)))

(defn is-file? [path]
  (.isFile (fs-object path)))

(defn process-file [file-name line-func line-acc]
  (with-open [rdr (BufferedReader. (FileReader. file-name))]
    (reduce line-func line-acc (line-seq rdr))))

(defn read-lines [file-name]
  (if (and (path-exists? file-name)
           (is-file? file-name))
    (process-file file-name conj [])
    []))

(defn append-line [path & lines]
  (println (str "Appending data to file at " path))
  (doseq [line lines]
    (spit path (str "\n" line) :append true)))

(defn find-in-file [file line]
  (let [lines (read-lines file)
        is-match? (fn [current] (= line current))
        has-match?
        (fn [remaining]
          (if (empty? remaining)
            false
            (if (is-match? (first remaining))
              true
              (recur (rest remaining)))))]
    (has-match? lines)))

                                        ; network-related utility methods

                                        ; binary data-fetch straight to file the java-way
(defn fetch-data [url output-file]
  (let  [con    (-> url java.net.URL. .openConnection)
         fields (reduce (fn [h v]
                          (assoc h (.getKey v) (into [] (.getValue v))))
                        {} (.getHeaderFields con))
         size   (first (fields "Content-Length"))
         in     (java.io.BufferedInputStream. (.getInputStream con))
         out    (java.io.BufferedOutputStream.
                 (java.io.FileOutputStream. output-file))
         buffer (make-array Byte/TYPE 1024)]
    (loop [g (.read in buffer)
           r 0]
      (if-not (= g -1)
        (do
          (.write out buffer 0 g)
          (recur (.read in buffer) (+ r g)))))
    (.close in)
    (.close out)
    (.disconnect con)))