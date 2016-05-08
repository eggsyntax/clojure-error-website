(ns error-website.github-reader
  (:require [clojure.pprint :refer [pprint]]
            [clojure.string :as s]
            [hickory.core :as h]
            [hickory.select :as hs]))


;; Base URL for github
(def site "https://github.com/")

;; Base URL for repo
(def base (str site "yogthos/clojure-error-message-catalog"))

;; Line containing content looks like:
"            <span class=\"css-truncate css-truncate-target\"><a href=\"/yogthos/clojure-error-message-catalog/blob/master/clj/let-requires-even-number-forms.md\" class=\"js-navigation-open\" id=\"08e44a41930978c7addbd6e6153dffd7-bf4613ba2a9ed6522a518ab7d45baa4ac6d0a309\" title=\"let-requires-even-number-forms.md\">let-requires-even-number-forms.md</a></span>"

(defn- only-body [coll]
  (->> coll
       (drop-while #(not (s/includes? % "<tbody>")))
       (take-while #(not (s/includes? % "</tbody>")))))

(defn get-error-list
  "Retrieve the list of errors from one of the subdirs of the github repo, as a list
  of maps, each of which contains the :href and :title keys."
  [dirname]
  (->> (str base "/tree/master/" dirname)
       slurp
       h/parse
       h/as-hickory
       (hs/select
        (hs/descendant
         (hs/class "main-content")
         (hs/class "content")
         (hs/attr :title)))
       (map :attrs)))

;; For now, just links direct to github -- later will slurp from github & embed
(defn node-to-link [node]
  [:a {:href (str site (:href node))} (:title node)])

(defn list-of-node-links [nodes]
  [:ul
   (for [node nodes]
     [:li (node-to-link node)])])
