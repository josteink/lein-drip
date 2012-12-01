# lein-drip

A Leiningen plugin which downloads, bootstraps and installs [drip](https://github.com/flatland/drip) as the default JVM to use for faster leiningen startup.

## WARNING

Plugin is currently in development and nothing working has been published.

## Usage

Put `[lein-drip "0.1.0-SNAPSHOT"]` into the `:plugins` vector of your
profiles.clj (typically found in `~/.lein`) or if you are on
Leiningen 1.x do `lein plugin install lein-drip 0.1.0-SNAPSHOT`.

After that execute the following code once:

    $ lein drip

## License

Copyright © 2012 Jostein Kjønigsen

Distributed under the Eclipse Public License, the same as Clojure.
