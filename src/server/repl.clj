(ns server.repl
  (:require
   [clojure.tools.namespace.repl :refer (set-refresh-dirs)]
   [integrant.core :as ig]
   [integrant.repl :as ir]
   [integrant.repl.state :as irs]
   [server.core]))

(defmethod ig/halt-key! :server/server [_ stop-server]
  (println "Stopping server")
  (stop-server))

(defmethod ig/halt-key! :server/db [_ _])

(defn set-prep! []
  (ir/set-prep! (constantly server.core/config)))

(defn reset []
  (ir/reset-all)
  (set-refresh-dirs "src")
  (set-prep!)
  (ir/go))


(comment
  (set-prep!)
  (ir/go) ;; init the system

  (ir/halt)
  #_((:server/server irs/system))
  (reset))
