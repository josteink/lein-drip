(ns leiningen.drip
	(:import java.io.File))

(defn get-home []
  (System/getProperty "user.home"))

(defn path-combine [path1 path2]
  (let [fs-object (File. path1 path2)]
    (.getAbsolutePath fs-object)))

(defn get-leindrip-folder []
  (let [home (get-home)]
    (path-combine home ".leindrip")))

(defn path-exists? [path]
  (let [fs-object (file. path)]
    (.exists fs-object)))

(defn drip
  "Download, bootstrap and register drip as the default JVM for leiningen."
  [project & args]
  ; TODO: should be able to detect self-installs and print that out
  ; what needs to be done is the following:
  ; 1. check if drip is installed
  ;    1.1. determine user-local drip folder as drip-folder
  ;          OUR rules! (defn drip-golder)
  ; yes -> report installed
  ; no -> install
  ;      1. download to drip-folder
  ;      2. execute to self-bootstrap
  ;      3. register in ~/.lein/leinrc
  (println "TODO: Implement me!")
  (println (str "Make me check if the following folder exists: " (get-leindrip-folder))))

