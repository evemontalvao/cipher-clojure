(ns cipher.core-test
  (:require [midje.sweet :refer :all]
            [cipher.core :as core]))

(facts "recebe um caractere e retorna sua posicao no alfabeto"
  (tabular 
    (core/to-int ?char) => ?result
    ?char ?result
    \a 0
    \b 1
    \c 2
    \d 3
    \z 25))

(facts "recebe um numero e retorna seu caractere"
  (tabular
    (core/to-char ?int) => ?result
    ?int ?result
    0 \a
    1 \b
    2 \c
    25 \z))

(facts "recebe um caractere e um numero, transforma o caractere em numero e soma o numero ao caractere"
  (tabular
    (core/shift ?char ?num) => ?result
    ?char ?num ?result
    \a    3    \d  
    \b    20   \v
    \z    3    \c))

(facts "recebe uma string e a retorna encriptada"
  (tabular
    (core/caesar-encrypt ?string ?num) => ?result
    ?string ?num ?result
    "abc" 1 "bcd"
    "abc" 3 "def"
    "apple" 20 "ujjfy"
    "Hello, friend!" 5 "mjqqtkwnjsi"))

(facts "recebe uma string encriptada e retorna decriptada"
  (tabular
    (core/caesar-decrypt ?string ?num) => ?result
    ?string ?num ?result
    "eve" -1 "fwf"
    "eve" +1 "dud"))

(facts "recebe uma string, encripta, desencripta e retorna o valor de entrada"
  (tabular
    (core/caesar-decrypt (core/caesar-encrypt ?string ?num) ?num) => ?result
    ?string ?num ?result
    "abc"   2    "abc"))

(facts "recebe uma string, retira os caracteres especiais e retorna em letras minusculas"
  (tabular
    (core/get-letters ?str) => ?result
    ?str ?result
    "Hello, world!" "helloworld"))

(facts "conta o numero de ocorrencias de uma letra em uma frase"
  (tabular
    (core/count-letters ?char ?str) => ?result
    ?char ?str ?result
    \a  "aadvark" 3
    \k  "aadvark" 1))

(def encr "radyjgtxhpsncpbxrvtctgpaejgedhtegdvgpbbxcvapcvjpvtrdbqxcxcviwtpeegdprwpqxaxinpcsxcitgprixktstktadebtciduphrgxeixcvapcvjpvtlxiwpctuuxrxtcipcsgdqjhixcugphigjrijgtudgbjaixiwgtpstsegdvgpbbxcvo")

(def hashmap {\a 7, \b 8, \c 16, \d 10, \e 8, \f 0, \g 16, \h 5, \i 13, \j 8, \k 2, \l 1, \m 0, \n 2, \o 1, \p 19, \q 3, \r 8, \s 6, \t 17, \u 5, \v 11, \w 4, \x 17, \y 1, \z 0})

(def orderedHashmap '([\p 19] [\t 17] [\x 17]))

(facts "recebe uma string ecriptada e retorna o numero de ocorrencias de cada letra"
  (tabular
    (core/count-occurrence ?str) => ?result
    ?str ?result
    encr hashmap))

(facts "recebe um hashmap e retorna as 3 maiores ocorrencias"
  (tabular
    (core/order-hashmap ?hashmap) => ?result
    ?hashmap ?result
    hashmap  orderedHashmap))

(facts "recebe dois numeros e retorna a diferença entre eles"
  (tabular
    (core/try-key ?x ?y) => ?result
    ?x ?y ?result
    \p \e 11))

(facts "recebe uma lista ordenada e retorna as 3 possiveis chaves"
  (tabular
    (core/try-possible-keys ?orderedHashmap ?char) => ?result
    ?orderedHashmap ?char ?result
    orderedHashmap  \e [11, 15, 19]))
(def encr2 "ahixblmaxmabgzpbmayxtmaxklmatmixkvaxlbgmaxlhnetgwlbgzlmaxmngxpbmahnmmaxphkwltgwgxoxklmhiltmtee")

#_(facts "recebe uma string ecriptada e retorna 3 possiveis resultados",
  (tabular
    (core/try-decryption ?str ?char) => ?result
    ?str ?char ?result
    encr \e []
    encr2 \e []))

(facts "recebe duas letras e usa a segunda para ecriptar a primeira"
  (tabular
    (core/encrypt-letter ?x ?y) => ?result
    ?x ?y ?result
    \w  \c \y
    \u  \i \c))

(facts "recebe duas letras e usa a segunda para ecriptar a primeira"
  (tabular
    (core/decrypt-letter ?x ?y) => ?result
    ?x ?y ?result
    \y  \c \w
    \c  \i \u))

(facts "recebe uma string e uma chave e repete a chave"
  (tabular
    (core/vigenere-encrypt ?str ?key) => ?result
    ?str ?key ?result
    "welcometoclojurebridge" "cipher" "ymajsdgbdjpflcglfiklvl"
    "Welcome to ClojureBridge!" "cipher" "ymajsdgbdjpflcglfiklvl"))

(facts "recebe uma string encriptada e desencripta"
  (tabular
    (core/vigenere-decrypt ?key ?str) => ?result
    ?key ?str ?result
    "ymajsdgbdjpflcglfiklvl" "cipher" "welcometoclojurebridge"))


