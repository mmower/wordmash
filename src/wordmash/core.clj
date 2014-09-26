(ns wordmash.core
  (:require [clojure.string :as str])
  (:gen-class :main true))

(defn read-dictionary
  "Return a sequence of dictionary words between 4 and 8 characters long and containing no upper case letters."
  [dictionary-file]
  (->> (slurp dictionary-file)
       (str/split-lines)
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

(def invalid-letter-patterns {:triple-consonant "([b-df-hj-np-tv-z]){3}"
                              :duplicate-vowels "([aiu])\1{1,}"
                              :too-many-voewls "([aeiou]){3,}"
                              :bad-ending "[iuvj]\b"
                              :q-without-u "q[^u]"})

(def invalid-word-regex (re-pattern (apply str (interpose "|" (vals invalid-letter-patterns)))))

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
  [mash-file dictionary size]
  (spit mash-file (str/join "\n" (sort (take size (viable-word-stream dictionary))))))

(defn dictionary-file
  []
  (or (System/getenv "DICTFILE")
      "/usr/share/dict/words"))

(defn -main
  [output-file & args]
  (let [dictionary (read-dictionary (dictionary-file))]
    (create-mash-dictionary output-file dictionary 2000)))
