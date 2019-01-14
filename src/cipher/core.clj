(ns cipher.core)

(defn to-int
  [letter-char]
  (let [ascii-a (int \a)]
    (- (int letter-char) ascii-a)))

(defn to-char
  [num]
  (let [ascii-a (int \a)]
    (char (+ num ascii-a))))

(defn shift
  [letter-char num]
  (-> letter-char
    (to-int)
    (+ num)
    (mod 26)
    (to-char)))

(defn get-letters
  [letter-str]
  (let [letter (clojure.string/lower-case letter-str)]
    (apply str (filterv #(Character/isLetter %) letter))))

(defn caesar-encrypt
  [w k]
  (->> w
    (get-letters)
    (mapv #(shift % k))
    (apply str)))

(defn caesar-decrypt
  [w k]
  (->> w
    (mapv #(shift % (- k)))
    (apply str)))

(defn count-letters
  [char str]
  (->> str 
    (filterv #(= % char))
    (count)))

(def alphabet (map to-char (range 26)))

(defn count-occurrence
  [str]
  (zipmap alphabet (mapv #(count-letters % str) alphabet)))

(defn order-hashmap
  [hashmap]
  (->> hashmap
    (sort-by second >)
    (take 3)))

(defn try-key
  [x y]
  (- (to-int x) (to-int y)))

(defn try-possible-keys
  [hashmap char]
  (mapv #(try-key (first %) char) hashmap))


(defn try-decryption
  [string char]
  (let [possible-keys(-> string
                        (count-occurrence)
                        (order-hashmap)
                        (try-possible-keys char))]
    (mapv #(caesar-decrypt string %) possible-keys)))

;; Cifra de Vigenère

(defn encrypt-letter
  [x y]
  (shift x (to-int y)))

(defn decrypt-letter
  [x y]
  (shift x (- (to-int y))))

(defn vigenere-encrypt
  [string key]
  (let [repeated-keys(cycle key)
        letters(get-letters string)
        chars(mapv #(encrypt-letter %1 %2) letters repeated-keys)] 
    (apply str chars)))

(defn vigenere-operation
  [fn string key]
  (let [repeated-keys (cycle key)
        letters (get-letters string)
        chars (mapv fn letters repeated-keys)] 
    (apply str chars)))

(partial vigenere-operation +)

(def vigenere-encrypt
  (partial vigenere-operation encrypt-letter))

(defn vigenere-decrypt
  [string key]
  (vigenere-operation decrypt-letter string key))
  
