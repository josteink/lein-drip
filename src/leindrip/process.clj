(ns leindrip.process
  (:use leindrip.platform
        leindrip.util
        [clojure.java.shell :only (sh with-sh-dir)]))

; actual worker-functions (dummy functions for now)

(def drip-line
  (str "LEIN_JAVA_CMD=" (get-leindrip-executable)))

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
  (let [file-name (get-leindrip-executable)]
    (fetch-data "https://raw.github.com/flatland/drip/develop/bin/drip" file-name)))

(defn set-drip-executable-permissions []
  (let [file-name (get-leindrip-executable)]
    (sh "chmod" "+x" file-name)))

(defn invoke-drip-self-install []
  (let [folder-name (get-leindrip-folder)
        file-name (get-leindrip-executable)]
    (with-sh-dir folder-name
      (sh file-name "upgrade"))))

(defn drip-registered-in-lein? []
  (let [file-name (get-leinrc-location)
        drip-line (get-drip-line)]
    (find-in-file file-name drip-line)))

(defn register-drip-in-lein []
  (let [file-name (get-leinrc-location)
        drip-line (get-drip-line)]
    (append-line file-name drip-line)))

