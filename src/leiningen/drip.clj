(ns leiningen.drip
  	(:use leindrip.process
         [leindrip.platform :only (platform)]))

; drip installation and bootstrapping process

(defn drip
  "Download, bootstrap and register drip as the default JVM for Leiningen."
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
  (println (str "Detected platform-type: " (:name platform)))
  (if (not (leindrip-folder-exists?))
    (do
      (println "Lein-drip folder not found. Creating.")
      (create-leindrip-folder)))
  
  (if (not (drip-executable-exists?))
    (do
      (println "drip-executable not found. Downloading.")
      (download-drip-executable)
      (println "Invoking drip self-install.")
      (invoke-drip-self-install)))
  
  (if (not (drip-registered-in-lein?))
    (do
      (println "drip not registered as Lein's JVM. Registering.")
      (register-drip-in-lein))))
