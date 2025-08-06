  (ns sistemasl.core
    (:require [sistemasl.fractales :as fractales]
              [sistemasl.interprete :as interprete]
              [sistemasl.graficos :as svg]
              [clojure.string :as str]))

  (defn -main [entrada n salida]
    (let [input (fractales/get-input entrada n salida)
          sistema (fractales/cargar-archivo-sistema-L! (input :entrada))]
      (if sistema
        (let [acciones (str/join "" (fractales/generar-sistema-L
                                      (:axioma sistema)
                                      (:reglas sistema)
                                      (:iteraciones input)))
              sistema-final (svg/interpretar
                              (interprete/inicializar acciones (:ang-der sistema) (:ang-izq sistema)))
              svg-content (svg/generar-svg (:camino sistema-final))]
          (spit (:salida input) svg-content)))))
