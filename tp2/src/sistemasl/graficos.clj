  (ns sistemasl.graficos
    (:require [clojure.string :as str]
              [sistemasl.interprete :as interprete]))

  (def margen 50)

  (def comandos
    {\F interprete/dibujar
     \G interprete/dibujar
     \f interprete/avanzar
     \g interprete/avanzar
     \+ interprete/girar-der
     \- interprete/girar-izq
     \| interprete/invertir
     \[ interprete/apilar
     \] interprete/desapilar
     \a (fn [s] (interprete/cambiar-color s "blue"))
     \b (fn [s] (interprete/cambiar-color s "red"))
     \c (fn [s] (interprete/cambiar-color s "green"))
     \d (fn [s] (interprete/cambiar-color s "magenta"))
     \e (fn [s] (interprete/cambiar-color s "yellow"))
     \1 (fn [s] (interprete/cambiar-grosor s 1))
     \2 (fn [s] (interprete/cambiar-grosor s 2))
     \L (fn [s] (interprete/hoja s))})

(defn interpretar [{:keys [acciones] :as sistema}]
  (if (empty? acciones)
    sistema
    (let [c (first acciones)
          f (comandos c)
          nuevo-sist (if f (f sistema) sistema)]
      (recur (assoc nuevo-sist :acciones (rest acciones))))))

(defn calcular-limites [camino]
  (let [puntos (mapcat (fn [segmento]
                         (case (:tipo segmento)
                           :linea [(:ini segmento) (:fin segmento)]
                             :hoja [(:pos segmento)]))
                         camino)
          xs (map first puntos)
          ys (map second puntos)]
      {:min-x (apply min xs)
       :max-x (apply max xs)
       :min-y (apply min ys)
       :max-y (apply max ys)}))

  (defn linea->svg-path [{:keys [ini fin color grosor]}]
    (let [[x1 y1] ini
          [x2 y2] fin]
      (str "<path d=\"M " x1 " " y1 " L " x2 " " y2 "\" "
           "stroke=\"" color "\" stroke-width=\"" grosor "\" fill=\"none\"/>")))

  (defn hoja->svg-circle [{:keys [pos color grosor]}]
    (let [[x y] pos]
      (str "<circle r=\"" 1 "\" cx=\"" x "\" cy=\"" y "\" fill=\"" color "\" stroke=\"" color "\" stroke-width=\"" grosor "\" />")))

  (defn generar-svg [camino]
      (let [{:keys [min-x max-x min-y max-y]} (calcular-limites camino)
            ancho (+ (- max-x min-x) (* 2 margen))
            alto (+ (- max-y min-y) (* 2 margen))
            viewbox (str (- min-x margen) " " (- min-y margen) " " ancho " " alto)
            path-data (->> camino
                           (map (fn [segmento]
                                  (case (:tipo segmento)
                                    :linea (linea->svg-path segmento)
                                    :hoja (hoja->svg-circle segmento))))
                           (str/join "\n"))]
        (str "<svg viewBox=\"" viewbox "\" xmlns=\"http://www.w3.org/2000/svg\">\n"
             path-data
             "\n</svg>\n")))