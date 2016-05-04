(ns user
  (:require [mount.core :as mount]
            error-website.core))

(defn start []
  (mount/start-without #'error-website.core/repl-server))

(defn stop []
  (mount/stop-except #'error-website.core/repl-server))

(defn restart []
  (stop)
  (start))


