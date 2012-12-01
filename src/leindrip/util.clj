(ns leindrip.util
  (:use leindrip.platform)
  (:import java.io.File))

; file-related utility methods

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

(defn append-line [path & lines]
  (doseq [line lines]
    (spit path (str "\n" line) :append true)))

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