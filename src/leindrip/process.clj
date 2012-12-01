(ns leindrip.process
  	(:use leindrip.platform leindrip.util))

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

