(ns sistemasl.fractales
  (:require [clojure.string :as str])
  (:import [java.io FileNotFoundException]))

(defn aplicar-reglas
  [reglas caracter]
  (get reglas caracter (str caracter)))

(defn generar-sistema-L
  [axioma reglas iteraciones]
  (if (zero? iteraciones)
    axioma
    (let [resultado (apply str (map (partial aplicar-reglas reglas) axioma))]
      (generar-sistema-L resultado reglas (dec iteraciones)))))

(defn cargar-archivo-sistema-L!
  [filename]
  (let [archivo (try (slurp filename)
                     (catch FileNotFoundException _))
        Error "Archivo .sl no encontrado"]
    (if (some? archivo)
      (let [lineas (str/split-lines archivo)
            primera (str/split (first lineas) #" ")
            [ang-der ang-izq] (if (= 1 (count primera))
                                (let [ang (read-string (first primera))] [ang ang])
                                (map read-string primera))
            axioma (second lineas)
            reglas (into {} (map #(let [[clave & valor] (str/split % #" " 2)]
                                    [(first clave) (str/join " " valor)])
                                 (drop 2 lineas)))]
        {:ang-der ang-der
         :ang-izq ang-izq
         :axioma  axioma
         :reglas  reglas})
      (throw (FileNotFoundException. Error)))))

(defn get-input
  [entrada n salida]
  {:entrada     entrada
   :iteraciones (Integer/parseInt n)
   :salida      salida})
