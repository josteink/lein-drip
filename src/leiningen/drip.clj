(ns leiningen.drip
	(:import java.io.File))

; utility methods

(defn get-home []
  (System/getProperty "user.home"))

(defn path-combine [path1 path2]
  (let [fs-object (File. path1 path2)]
    (.getAbsolutePath fs-object)))

(defn get-leindrip-folder []
  (let [home (get-home)]
    (path-combine home ".leindrip")))

(defn get-leindrip-executable []
    (path-combine (get-leindrip-folder) "drip"))

(defn path-exists? [path]
  (let [fs-object (File. path)]
    (.exists fs-object)))

; actual worker-functions (dummy functions for now)

(defn create-leindrip-folder []
  :placebo)

(defn download-drip-executable []
  :placebo)

(defn invoke-drip-self-install []
  :placebo)

(defn drip-jvm-registered? []
  ; placebo
  false)

(defn register-drip []
  :placebo)

; drip installation and bootstrapping process

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
  
  (if (not (path-exists? (get-leindrip-folder)))
    (do
      (println "Lein-drip folder not found. Creating.")
      (create-leindrip-folder)))
  
  (if (not (path-exists? (get-leindrip-executable)))
    (do
      (println "drip-executable not found. Downloading.")
      (download-drip-executable)
      (println "Invoking drip self-install.")
      (invoke-drip-self-install)))
  
  (if (not (drip-jvm-registered?))
    (do
      (println "drip not registered as Lein's JVM. Registering.")
      (register-drip))))
