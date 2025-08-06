  (ns sistemasl.tortuga
    (:require
      [clojure.math :refer [to-radians cos sin]]))

  (defn tortuga [pos ang plu]
    ; pos = vector de 2 elementos [x y]
    ; ang = angulo de 0 a 360
    ; plu = booleano para pluma arriba/abajo
    {:pos   pos
     :ang   ang
     :pluma plu})

  (defn clamp [x x- x+]
    (cond
      (< x x-) x-
      (> x x+) x+
      :else x))

  (defn adelante [estado n]
    (let [a (to-radians(:ang estado))]
      (update-in estado [:pos]
              (fn [[x y]]
                [(+ x (* n 10 (cos a)))
                 (- y (* n 10 (sin a)))]))))

  (defn normalizar-ang [x]
    (mod x 360))

  (defn derecha [estado alpha]
    (update estado :ang #(normalizar-ang (- % alpha))))

  (defn izquierda [estado alpha]
    (update estado :ang #(normalizar-ang (+ % alpha))))