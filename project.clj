(defproject gr-homework "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
								 [org.clojure/tools.namespace "0.2.11"]
								 [clj-time "0.13.0"]
								 [http-kit "2.2.0"]
								 [ring/ring-core "1.6.0-RC1"]
								 [compojure "1.5.2"]]
  :main ^:skip-aot gr-homework.core
	:resource-paths ["resources"]
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
