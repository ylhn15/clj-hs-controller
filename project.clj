(defproject hs-controller "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :main "hs-controller.core"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.1"][clj-http "3.12.3"][org.clojure/data.json "0.2.6"]]
  :plugins [[cider/cider-nrepl "0.24.0"]]
  :repl-options {:init-ns hs-controller.core})
