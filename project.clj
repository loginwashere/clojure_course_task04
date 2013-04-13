(defproject board "0.1.0-SNAPSHOT"
  :description "imageboard"
  :url "http://board-loginwashere.rhcloud.com/"
  :warn-on-reflection  true
  :source-paths       ["src"]
  :resource-paths     ["resources"]
  :main           lwh.board.handler
  :dependencies [[org.clojure/clojure "1.5.1"]
                 [http-kit "2.0.0"]
                 [compojure "1.1.5"]
                 [me.raynes/laser "1.1.1"]
                 [com.novemberain/monger "1.5.0"]
                 [lib-noir "0.4.9"]]
  :plugins [[lein-ring "0.8.2"]]
  :ring {:handler lwh.board.handler/app}
  :profiles
  {:dev {:dependencies [[ring-mock "0.1.3"]]}}
  :jvm-opts     ["-Dfile.encoding=UTF-8"])