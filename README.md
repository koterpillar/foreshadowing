# Foreshadowing

Learn something about your values before they are computed!

```scala
import com.koterpillar.foreshadowing._

val Eventually(res) = for {
  l <- Divination[String, Int](_.length)
} yield s"This string has $l characters."

// res: String = This string has 30 characters.

res.length
// 30
```
