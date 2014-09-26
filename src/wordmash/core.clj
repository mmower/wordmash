(ns wordmash.core
  (:require [clojure.string :as str]
            [clojure.edn :as edn])
  (:gen-class :main true))

(defn rules-file
  "Return the path to the default rules file or an alternate file if the RULESFILE environment var is set."
  []
  (or (System/getenv "RULESFILE")
      "resources/rules.edn"))

(defn read-dictionary
  "Return a sequence of dictionary words, parsed from STDIN, between 4 and 8 characters long and containing no upper case letters."
  []
  (->> (line-seq (java.io.BufferedReader. *in*))
       (filter #(> 9 (count %) 3))
       (filter #(= % (str/lower-case %)))))

(defn random-word
  "Return a randomly choosen word from the dictionary sequence."
  [dictionary]
  (rand-nth dictionary))

(defn split-point
  "Return a random split point for word w between the second and penultimate characters."
  [w]
  (inc (rand-int (dec (count w)))))

(defn split-word
  "Splits the word w into two chunks around a randomly choosen split point."
  [w]
  (let [split-point (split-point w)]
    [(subs w 0 split-point) (subs w split-point (count w))]))

(defn word-mash
  "Mashes two randomly choosen words together around a random split point to form a new word from the beginning of the first and end of the second."
  [dictionary]
  (let [[b1 _] (split-word (random-word dictionary))
        [_ e2] (split-word (random-word dictionary))]
    (str/join [b1 e2])))

(defn rule-set
  "Return a sequence of regular expressions that represent invalid patterns for viable words."
  []
  (vals (edn/read-string (slurp (rules-file)))))

(def invalid-word-regex (re-pattern (apply str (interpose "|" (rule-set)))))

(defn viable-word?
  "Predicate that ensures the word w contains none of the invalid letter patterns."
  [w]
  (not (re-find invalid-word-regex w)))

(defn word-stream
  "Return a lazy sequence of mashed words."
  [dictionary]
  (cons (word-mash dictionary) (lazy-seq (word-stream dictionary))))

(defn viable-word-stream
  "Return a lazy sequence of viable mashed words."
  [dictionary]
  (filter viable-word? (word-stream dictionary)))

(defn create-mash-dictionary
  "Take size viable words from the word-mash word stream."
  [dictionary size]
  (str/join "\n" (sort (take size (viable-word-stream dictionary)))))

(defn -main
  [& args]
  (let [word-count (if-let [arg (first args)]
                     (Integer/parseInt arg)
                     500)]
      (println (create-mash-dictionary (read-dictionary) word-count))))
