  (ns sistemasl.interprete
    (:require
      [sistemasl.tortuga :as t]))

  (defn inicializar [acciones ang-der ang-izq]
    {:acciones acciones
     :ang-der ang-der
     :ang-izq ang-izq
     :grosor 1
     :color "black"
     :pila [(t/tortuga [0 0] 90 true)]
     :camino []})

  (defn aplicar [sistema funcion pluma & args]
    (let [tortuga (peek (:pila sistema))
          t (apply funcion tortuga args)
          s (update sistema :pila #(conj (pop %) t))]
      (if pluma
        (update s :camino conj {:tipo :linea
                                :ini (:pos tortuga)
                                :fin (:pos t)
                                :color (:color s)
                                :grosor (:grosor s)
                                })
        s)))

  (defn dibujar [sistema]
    (aplicar sistema t/adelante true 1))

  (defn avanzar [sistema]
    (aplicar sistema t/adelante false 1))

  (defn girar-der [sistema]
    (aplicar sistema t/derecha true (:ang-der sistema)))

  (defn girar-izq [sistema]
    (aplicar sistema t/izquierda true (:ang-izq sistema)))

  (defn invertir [sistema]
    (aplicar sistema t/izquierda true 180))

  (defn apilar [sistema]
    (update sistema :pila conj (peek (:pila sistema))))

  (defn desapilar [sistema]
    (update sistema :pila pop))

  (defn cambiar-color [sistema color]
    (assoc sistema :color color))

  (defn cambiar-grosor [sistema grosor]
    (assoc sistema :grosor grosor))

  (defn hoja [sistema]
    (update sistema :camino conj {:tipo :hoja
                                  :pos (-> sistema :pila peek :pos)
                                  :color (:color sistema)
                                  :grosor (:grosor sistema)}))