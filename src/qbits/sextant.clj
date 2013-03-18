(ns qbits.sextant
  "Ported from  http://janmatuschek.de/LatitudeLongitudeBoundingCoordinates.")

(def ^:const MIN-LAT (Math/toRadians -90.0))
(def ^:const MAX-LAT (Math/toRadians 90.0))
(def ^:const MIN-LON (Math/toRadians -180.0))
(def ^:const MAX-LON (Math/toRadians 180.0))

;; we are on planet earth but who knows, radius unit dicts the unit
;; for the distance function
(def ^:dynamic *radius* 6371.01)

(defprotocol PConversion
  (as-degrees [this]))

(defprotocol PGeolocation
  (valid-bounds? [this])
  (distance [this point])
  (bounding-box [this distance]))

;; in radians
(defrecord Point [^double latitude
                  ^double longitude]
  PGeolocation
  (valid-bounds? [this]
    (or (< latitude MIN-LAT)
        (> latitude MAX-LAT)
        (< longitude MIN-LON)
        (> longitude MAX-LON)))

  (distance [this {loc-latitude :latitude
                   loc-longitude :longitude}]
    (* *radius*
       (Math/acos (+ (* (Math/sin latitude)
                        (Math/sin loc-latitude))
                     (* (Math/cos latitude)
                        (Math/cos loc-latitude)
                        (Math/cos (- longitude loc-longitude)))))))

  (bounding-box [this distance]
    (let [rad-dist (/ distance *radius*)
          min-lat (- latitude rad-dist)
          max-lat (+ latitude rad-dist)]
      (if (and (> min-lat MIN-LAT)
               (< max-lat MAX-LAT))
        (let [delta-lon (Math/asin (/ (Math/sin rad-dist)
                                      (Math/cos latitude)))
              min-lon (- longitude delta-lon)
              min-lon (if (< min-lon MIN-LON)
                        (+ min-lon (* 2.0 Math/PI))
                        min-lon)
              max-lon (+ longitude delta-lon)
              max-lon (if (> max-lon MAX-LON)
                        (- max-lon (* 2.0 Math/PI))
                        max-lon)]
          [(Point. min-lat min-lon)
           (Point. max-lat max-lon)])
        [(Point. (Math/max min-lat MIN-LAT) MIN-LON)
         (Point. (Math/max max-lat MAX-LAT) MAX-LON)])))

  PConversion
  (as-degrees [this]
    {:latitude (Math/toDegrees latitude)
     :longitude (Math/toDegrees longitude)}))

(defn degrees->point
  [[longitude latitude]]
  (Point. (Math/toRadians longitude)
          (Math/toRadians latitude)))
