# Working with CITE/CTS in Scala


## Getting Started

You need to have [SBT](http://www.scala-sbt.org) installed.

You will need to be online, at least the first time you run this.

In a terminal, navigate to the `cite-twiddle` directory and type

> `sbt console`

From with the sbt console,

> `:load workshop.sc` (type the initial colon!)

This loads the file `CEX/brazil-large.cex` and creates:

1. `library` : an OHCO2 library of texts
1. `corpus` : the complete corpus

## API Guides

To see the documentation for the OHCO2 and CITE code libraries, follow these links:

- [CITE API (CTS and CITE URNs)](api/cite-api/index.html)
- [OHCO2 API (Respository and Corpus objects)](api/ohco2-api/index.html)

## Things to do

### Work with a CTS URN

- Create a URN-Object identifying one line of an *Iliad*

> `val u = CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1")`

> `u.namespace`
> `u.textGroup`
> `u.work`
> `u.versionOption`
> `u.exemplarOption
> `u.passageComponentOption`
> `u.dropPassage`

- Type `u.` and hit the TAB key to see other options.

### URN Algebra

- Create a URN-object identifying Caesar's *De Bello Gallico* at the work-level.

> `val caesarWorkUrn = CtsUrn("urn:cts:latinLit:phi0448.phi001:")`

- Create a URN-object identifying a Latin Edition of Caesar's *De Bello Gallico*.

> `val caesarLatUrn = CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:")`

- Create a URN-object identifying a Portuguese translation of *De Bello Gallico*.

> `val caesarPorUrn = CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:")`


- Does a citation to *De Bello Gallico* as a notional work include citations to *De Bello Gallico* in Latin?

> `caesarWorkUrn ~~ caesarLatUrn` (see if the result is `true`)

- Is a citation to *De Bello Gallico* in Portugueses also a citation to *De Bello Gallico*?

> `caesarPorUrn ~~ caesarWorkUrn`

- Is a citation to *De Bello Gallico* in Portuguese also a citation to *De Bello Gallico* in Latin?

> `caesarPorUrn ~~ caesarLatUrn`

- Do some algebra with URNs

> `CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1") > CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1.2")`
> `CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1.1") > CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1.2")`
> `CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1.1") < CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:1")`


### Corpus Algebra & Corpus Arithmetic

- Pull the first ten lines of the *Iliad* out of `corpus`, and make a new Corpus object of them

> `val iliadFirstTen = corpus >= CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1-1.10")`

- Look at your new corpus

> `printCorpus(iliadFirstTen)`

- Make another corpus with the next 10 lines

> `val iliadNextTen = corpus >= CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.11-1.20")`

- See them

> `printCorpus(iliadNextTen)`

- Do some “Corpus Arithmetic”

> `val iliadTwenty = iliadFirstTen ++ iliadNextTen`.

- See what you have

> `printCorpus(iliadTwenty)`

- Make another corpus, this time specifying *Iliad* 1.1-1.20 in a particular version

> `val iliadNewTwenty = corpus >= CtsUrn("urn:cts:greekLit:tlg0012.tlg001.perseus_grc2:1.1-1.20")`

- Confirm that the two corpora are identical

> `iliadTwenty == iliadNewTwenty`

-  Make a corpus of *De Bello Gallico* in Latin:

> val caesarLatin = corpus ~~ CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:")

- See how passages relate to one another in this corpus:

> caesarLatin.relation(CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.1"),CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.2"))

> caesarLatin.relation(CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.2"),CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.1"))

> caesarLatin.relation(CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.1.2"),CtsUrn("urn:cts:latinLit:phi0448.phi001.holmes_lat:1.1.1-1.1.3"))


## Exploring Corpora

- Make a corpus of Francisco Sotero dos Reis’ translation of *De Bello Gallico*. 

> `val dbgUrn = CtsUrn("urn:cts:latinLit:phi0448.phi001.dosreis:")
> `val dosreis = corpus ~~ dbgUrn

- See it

> `printCorpus(dosreis)`

- Get 2-grams in this translation, that occur at least 5 times, ignoring punctuation

> `dosreis.ngramHisto(2,5,true)` (Note that you don’t actually have to do the `val X =` bit every time, in `sbt console`. You can simply make a Scala statement, and `sbt` will create a new `val` for it, names `resN`, where `N` is a number. You can use that as a `val` henceforth.)

- Confirm that the result is something you can consider scholarship by grabbing the *citations* for one of those ngrams

> `val germans = dosreis.urnsForNGram("os Germanos")`

- See what you got

> `for (u <- germans) println(u)`

- Use the up-arrow key to go back in your command-history, and play around with the N-Gram values.

- Let's find some Belgae! Search the entire corpus for "belg"

> `val belgae = corpus.find("belg")`

- See what you got

> `printCorpus(belgae)`

- See just the URNs

> `for (u <- belgae.nodes) println(u.urn)`
