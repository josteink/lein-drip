(ns leindrip.platform)

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
    :lein-folder ".lein"
    }})

(defn get-platform []
  (let [os-name (System/getProperty "os.name")
        is-windows (not (= -1 (.indexOf os-name "Windows")))]
    ; everything not windows is unix :)
    (if is-windows
      (:windows platforms)
      (:unix platforms))))

(def platform (get-platform))