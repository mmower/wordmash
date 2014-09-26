# wordmash

If you've ever wanted to create a nonsense English dictionary then Wordmash is for you. Wordmash "mashes together" words from a dictionary to form new words that are somewhat like the originals.

To create a new word, Wordmash takes two randomly selected words from the original dictionary, selects a random mid-point for each word, and
splits them to form two beginnings and two endings. It then takes the first beginning and second ending and makes a new word. Here's an example:

* Randomly choosen words "unbalance" and "guayacan"
* For "unbalance" select random mid-point, e.g. 3
* Split "unbalance" at 3 to form "unb" and "alance"
* For "guayacan" select random mid-point, e.g. 4
* Split "guayacan" at 4 to form "guay" and "acan"
* Combine "unb" with "acan" to form new word "unbacan"

## Usage

lein uberjar
java -jar target/wordmash-0.1.1-SNAPSHOT-standalone.jar <output-dictionary> <word-count>

by default it will look in /usr/share/dict/words, to use a different dictionary pass
the path in DICTFILE environment

DICTFILE=/my/special/dict/words java -jar ...

## License

Copyright © 2014 Matt Mower <m@mwrvld.com>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
