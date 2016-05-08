(ns error-website.routes.home
  (:require [error-website.layout :as layout]
            [compojure.core :refer [defroutes GET]]
            [hiccup
             [core :as h]
             [def :as hd]]
            [error-website.hiccup :as ewh]
            [error-website.github-reader :as gr]
            [ring.util.http-response :as response]
            [clojure.java.io :as io]))

(defn home-page []
  (layout/render
   "home.html" {:docs (-> "docs/docs.md" io/resource slurp)}))


(defn errors-page []
  (ewh/into-html "errors.html"
                 (let [nodes (gr/get-error-list "clj")]
                   (gr/list-of-node-links nodes))))


(defn submit-page []
  (layout/render "submit.html"))

(defn about-page []
  (layout/render "about.html"))

(defroutes home-routes
  (GET "/" [] (home-page))
  (GET "/errors" [] (errors-page))
  (GET "/submit" [] (submit-page))
  (GET "/about" [] (about-page))
  )
