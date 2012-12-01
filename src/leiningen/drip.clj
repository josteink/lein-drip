(ns leiningen.drip
  	(:import java.io.File))

; platform support

(def platforms
  {
   ; just in case we ever need to support this in the future.
   :windows nil
   :unix
   {
    :name "Unix"
    :leindrip-folder ".leindrip"
    :drip-executable "drip"
    }})

(defn get-platform []
  (let [os-name (System/getProperty "os.name")
        is-windows (not (= -1 (.indexOf os-name "Windows")))]
    ; everything not windows is unix :)
    (if is-windows
      (:windows platforms)
      (:unix platforms))))

(def platform (get-platform))

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

; actual worker-functions (dummy functions for now)

(defn leindrip-folder-exists? []
  (let [folder-name (get-leindrip-folder)]
    (and (path-exists? folder-name)
         (is-directory? folder-name))))

(defn create-leindrip-folder []
  (let [folder-name (get-leindrip-folder)]
    (.mkdirs (fs-object folder-name))))

(defn drip-executable-exists? []
  (let [file-name (get-leindrip-executable)]
    (and (path-exists? file-name)
         (is-file? file-name))))

(defn download-drip-executable []
  :placebo)

(defn invoke-drip-self-install []
  :placebo)

(defn drip-registered-in-lein? []
  ; placebo
  false)

(defn register-drip-in-lein []
  :placebo)

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
