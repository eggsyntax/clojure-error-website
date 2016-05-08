(ns error-website.hiccup
  (:require [clojure.string :as s]
            [error-website.layout :as layout]
            [hiccup.core :as h]
            [hiccup.util :as u]))

(defn clean-newlines [s]
  (s/replace s "\\n" "\n"))

(defn into-html
  "Embed html-ized hiccup into rendered template, replacing the first
  occurrence of a keyword therein ('!hiccup' by default)"
  ([template hiccup] (into-html template hiccup "!hiccup"))
  ([template hiccup keyword]
   (let [rendered (layout/render template)
         body (:body rendered)]
     (assoc rendered :body
            (clean-newlines
             (s/replace-first body keyword (h/html hiccup)))))))

(comment
  (into-html "<html <div <p>Hi there!</p> !hiccup </div> </html>"
             [:p "foo"])
  ;; "<html <div <p>Hi there!</p> <p>foo</p> </div> </html>"

  )
