(ns qbits.sextant.core-test
  (:use clojure.test
        qbits.sextant))

(def loc-1-deg [46.5167,  6.6333]) ;;lausanne
(def loc-2-deg [46.2000 6.1500]) ;; geneva

(deftest test-distance
  (is (= 51.14385202801027
         (distance (degrees->point loc-1-deg)
                   (degrees->point loc-2-deg)))))

(deftest test-bounding-box
  (is (= [#qbits.sextant.Point{:latitude 0.7961734737416308
                               :longitude 0.09296253218512376}
          #qbits.sextant.Point{:latitude 0.8275656705832619
                               :longitude 0.13858331835995594}]
         (bounding-box (degrees->point loc-1-deg) 100))))
