(ns leiningen.drip
  	(:use leindrip.process
         [leindrip.platform :only (platform)]
         [clojure.string :only (lower-case)]))

; drip installation and bootstrapping process

(defn drip
  "Download, bootstrap and register drip as the default JVM for Leiningen."
  [project & args]
  
  (def do-force (= "force" (lower-case (first args))))
  
  (println (str "Detected platform-type: " (:name platform)))
  (if (or do-force (not (leindrip-folder-exists?)))
    (do
      (println "Lein-drip folder not found. Creating.")
      (create-leindrip-folder))
    (do
      (println "Lein-drip folder found.")))
  
  
  (if (or do-force (not (drip-executable-exists?)))
    (do
      (println "drip-executable not found. Downloading.")
      (download-drip-executable)
      (println "Settings permissions.")
      (set-drip-executable-permissions)
      (println "Invoking drip self-install.")
      (invoke-drip-self-install))
    (do
      (println "drip-executable found.")))
  
  (if (or do-force (not (drip-registered-in-lein?)))
    (do
      (println "drip not registered as Lein's JVM. Registering.")
      (register-drip-in-lein)
      :done)
    (do
      (println "drip already registered as Lein's JVM.")
      :done)))

