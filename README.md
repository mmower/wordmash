# wordmash

If you've ever wanted to create a nonsense English dictionary then Wordmash is for you. Wordmash "mashes together" words from a dictionary to form new words that are somewhat like the originals.

To create a new word, Wordmash takes two randomly selected words from the original dictionary, selects a random mid-point for each word, and
splits them to form two beginnings and two endings. It then takes the first beginning and second ending and makes a new word. Here's an example:

1. Randomly choosen words "unbalance" and "guayacan"
* For "unbalance" select random mid-point, e.g. 3
* Split "unbalance" at 3 to form "unb" and "alance"
* For "guayacan" select random mid-point, e.g. 4
* Split "guayacan" at 4 to form "guay" and "acan"
* Combine "unb" with "acan" to form new word "unbacan"

Wordmash includes some simple rules that try to ensure generated words seem English-like for example not allowing endings like i, u, v, or j, or allowing words with a q not followed by a u. The default rules are defined in `resources/rules.edn`.

## Usage

    lein uberjar
    cat /usr/share/dict/words | java -jar target/wordmash-0.1.3-SNAPSHOT-standalone.jar [<word-count>]

Pass in the dictionary of words you want to use as a source via stdin, the mashed dictionary will be written to stdout. By default 500
words are generated but you can pass a word-count argument to generate more or less words.

To use a custom rules files pass the path to your own edn file using the `RULESFILE` environment variable:

    RULESFILE=/my/rules.edn java -jar ...

Rules are regular expression patterns, see the resources/rules.edn file for the format. The map keys are purely for describing the rules.

## License

Copyright © 2014 Matt Mower <m@mwrvld.com>

Distributed under the Eclipse Public License either version 1.0 or (at
your option) any later version.
