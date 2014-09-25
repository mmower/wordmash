# wordmash

Mash together dictionary words to form new English like words.

## Usage

lein uberjar
java -jar target/wordmash-0.1.0-SNAPSHOT-standalone.jar mashed.txt

by default it will look in /usr/share/dict/words, to use a different dictionary pass
the path in DICTFILE environment

DICTFILE=my-dict/words java -jar ...

## License

Copyright © 2014 Matt Mower <m@mwrvld.com>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
