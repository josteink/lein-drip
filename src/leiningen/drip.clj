(ns leiningen.drip
  (:use leindrip.process
        [leindrip.platform :only (platform)]
        [clojure.string :only (lower-case)]))

                                        ; drip installation and bootstrapping process

(defn drip
  "Download, bootstrap and register drip as the default JVM for Leiningen."
  [project & args]

                                        ; must evaluate num of arguments, and never attempt get (first args) on
                                        ; a empty args-list, or no parts of the program will execute. lightly wtf.
  (let [p1v (if (= 0 (count args)) "" (first args))
        do-force (= "force" (lower-case p1v))]

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
        :done))))
