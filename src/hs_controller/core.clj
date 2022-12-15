(ns hs-controller.core
  (:gen-class)
  (:require
   [clj-http.client :as client]
   [clojure.data.json :as json])
  (:import
   (java.security
    MessageDigest)))

(def config (atom (json/read-str (slurp "./config.json") :key-fn keyword)))
(def url (:url @config))
(def pin (:pin @config))

(defn get-nonce
  [url]
  (->> keyword
       (json/read-str
        (:body
         (client/get url)) :key-fn)
       :meta
       :nonce))

(defn md5
  [^String s]
  (->> s
       .getBytes
       (.digest (MessageDigest/getInstance "MD5"))
       (BigInteger. 1)
       (format "%032x")))

(defn create-hs-pin
  []
  (md5 (str (get-nonce url) (md5 pin))))

(defn create-post-header
  []
  {"Host" url
   "Accept" "*/*"
   "Proxy-Connection" "keep-alive"
   "X-BACKEND-IP" "https://app.haassohn.com"
   "Accept-Language" "de-DE ;q=1.0, en-DE;q=0.9"
   "Accept-Encoding" "gzip ;q=1.0, compress;q=0.5"
   "token" "32bytes"
   "Content-Type" "application/json"
   "User-Agent" "ios"
   "Connection" "keep-alive"
   "X-HS-PIN" (create-hs-pin)})

(defn create-post-request
  [temp]
  {:headers (create-post-header)
   :body (json/write-str {:sp_temp temp})})

(defn -main
  []
  (client/post url (create-post-request 22.0)))
