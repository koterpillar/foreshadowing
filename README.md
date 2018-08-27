# Foreshadowing

Learn something about your values before they are computed!

```scala
import com.koterpillar.foreshadowing._

val isVowel = Set('a', 'e', 'i', 'o', 'u')

val Eventually(res) = for {
  len <- Divination[String, Int](_.length)
  vowels <- Divination[String, Int](_.count(isVowel))
} yield s"This string has $len characters. Of those, $vowels are vowels."

// res: String = This string has 55 characters. Of those, 12 are vowels.

res.length
// 55

res.count(isVowel)
// 12
```

# Limitations

Divination power is not very strong yet. Asking for the impossible will lead
one onto the infinite road through time ~and space~ `@tailrec` fixed the space
bit.

Scala cannot divine up enough types, somewhat breaking the immersion.

# Motivation

This is indirectly inspired by a colleauge mentioning that `for`
comprehensions are sequencing the computation. Of course they do... normally.

Also see:

* [Tardis monad transformer](http://hackage.haskell.org/package/tardis),
  illustrating cases where there is _more than one_ sequence of operations.
* [XKCD diagram](https://xkcd.com/688/) that this code _should_ be able to
  generate.
* [GEB](https://en.wikipedia.org/wiki/G%C3%B6del,_Escher,_Bach), just because.