(ns error-website.env
  (:require [clojure.tools.logging :as log]))

(def defaults
  {:init
   (fn []
     (log/info "\n-=[error-website started successfully]=-"))
   :stop
   (fn []
     (log/info "\n-=[error-website has shutdown successfully]=-"))
   :middleware identity})
