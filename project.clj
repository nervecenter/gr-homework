(defproject gr-homework "0.5.0"
  :description "A program that parses and sorts records,
                with REST endpoints for querying."
  :dependencies [[org.clojure/clojure "1.8.0"]
								 [org.clojure/tools.namespace "0.2.11"]
								 [clj-time "0.13.0"]
								 [http-kit "2.2.0"]
                 [ring "1.6.0-RC1"]
                 [ring/ring-json "0.4.0"]
								 [compojure "1.5.2"]
                 ;;[cheshire "5.7.0"] Don't need, instead ring-json?
                 ]
  :main ^:skip-aot gr-homework.core
	:resource-paths ["res"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
