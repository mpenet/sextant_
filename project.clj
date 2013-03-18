(defproject cc.qbits/sextant "0.1.0"
  :description "Geo location utility functions"
  :url "https://github.com/mpenet/sextant"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.4.0"]]
  :profiles {:1.4  {:dependencies [[org.clojure/clojure "1.4.0"]]}
             :1.5  {:dependencies [[org.clojure/clojure "1.5.0"]]}
             :1.6  {:dependencies [[org.clojure/clojure "1.6.0-master-SNAPSHOT"]]}
             :dev  {:dependencies [[clj-time "0.4.4"]]}
             :test  {:dependencies [[clj-time "0.4.4"]]}}
  :codox {:src-dir-uri "https://github.com/mpenet/sextant/blob/master"
          :src-linenum-anchor-prefix "L"}
  :warn-on-reflection true)
