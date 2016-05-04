(ns error-website.env
  (:require [selmer.parser :as parser]
            [clojure.tools.logging :as log]
            [error-website.dev-middleware :refer [wrap-dev]]))

(def defaults
  {:init
   (fn []
     (parser/cache-off!)
     (log/info "\n-=[error-website started successfully using the development profile]=-"))
   :stop
   (fn []
     (log/info "\n-=[error-website has shutdown successfully]=-"))
   :middleware wrap-dev})
