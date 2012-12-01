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

; utility methods

(defn get-home []
  (System/getProperty "user.home"))

(defn path-combine [path1 path2]
  (let [fs-object (File. path1 path2)]
    (.getAbsolutePath fs-object)))

(defn get-leindrip-folder [platform]
  (let [home (get-home)]
    (path-combine home (:leindrip-folder platform))))

(defn get-leindrip-executable [platform]
  (path-combine (get-leindrip-folder platform) (:drip-executable platform)))

(defn fs-object [path]
  (File. path))

(defn path-exists? [path]
  (.exists (fs-object path)))

(defn is-directory? [path]
  (.isDirectory (fs-object path)))

(defn is-file? [path]
  (.isFile (fs-object path)))
  
; actual worker-functions (dummy functions for now)

(defn leindrip-folder-exists? [platform]
  (let [folder-name (get-leindrip-folder platform)]
    (and (path-exists? folder-name)
         (is-directory? folder-name))))

(defn create-leindrip-folder [platform]
  (let [folder-name (get-leindrip-folder platform)]
    (.mkdirs (fs-object folder-name))))

(defn drip-executable-exists? [platform]
  (let [file-name (get-leindrip-executable platform)]
    (and (path-exists? file-name)
         (is-file? file-name))))

(defn download-drip-executable [platform]
  :placebo)

(defn invoke-drip-self-install [platform]
  :placebo)

(defn drip-jvm-registered? [platform]
  ; placebo
  false)

(defn register-drip [platform]
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
  
  (let [platform (get-platform)]
    (println (str "Detected platform-type: " (:name platform)))
    (if (not (leindrip-folder-exists? platform))
      (do
        (println "Lein-drip folder not found. Creating.")
        (create-leindrip-folder platform)))
    
    (if (not (drip-executable-exists? platform))
      (do
        (println "drip-executable not found. Downloading.")
        (download-drip-executable platform)
        (println "Invoking drip self-install.")
        (invoke-drip-self-install platform)))
    
    (if (not (drip-jvm-registered? platform))
      (do
        (println "drip not registered as Lein's JVM. Registering.")
        (register-drip platform)))))
