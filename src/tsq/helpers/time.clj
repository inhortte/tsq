(ns tsq.helpers.time
  (:use [clj-time.coerce :only [from-long]]
        [clj-time.format :only [with-zone formatter unparse parse formatters]]
        [clj-time.core :only [default-time-zone]]))

(def fmt (with-zone (formatter "dd MMMM yyyy") (default-time-zone)))

(defn format-date [long]
  (unparse fmt (from-long long)))
