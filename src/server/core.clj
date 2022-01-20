(ns server.core
  (:require
   [config.core :refer [load-env]]
   [hiccup.page :as hiccup]
   [mount.core :refer [defstate] :as mount]
   [org.httpkit.server :as http]
   [reitit.ring :as ring]
   [server.middleware
    :refer [wrap-defaults api-defaults]]
   [ring.util.response :as response])
  (:gen-class))

;; Graal does not support reflection calls
(set! *warn-on-reflection* true)

(def handler
  (ring/ring-handler
   (ring/router
    [["/"
      {:get (fn [request]
              (-> (hiccup/html5
                   [:head (hiccup/include-css "screen.css")]
                   [:div.content
                    [:h2 (str "Hello " (:remote-addr request) " 🔥🔥🔥")]])
                  (response/response)
                  (response/header "content-type" "text/html")))}]])))

(defmethod response/resource-data :resource
  [^java.net.URL url]
  ;; GraalVM resource scheme
  (let [resource (.openConnection url)
        len (#'ring.util.response/connection-content-length resource)]
    (when (pos? len)
      {:content        (.getInputStream resource)
       :content-length len
       :last-modified  (#'ring.util.response/connection-last-modified resource)})))

(defstate env :start (load-env))

(defstate server
  :start (let [port (:port env)]
           (println "🔥 starting on port:" port "🔥")
           (http/run-server
            (wrap-defaults
             handler
             (assoc api-defaults :static {:resources "public"}))
            {:port port}))
  :stop (when server (server :timeout 100)))

(defn -main [& args]
  (cond
    (not (nil? args)) (mount/start)
    :else (.println System/out "needs argument to start")))
