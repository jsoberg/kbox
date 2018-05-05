package ch.tutteli.kbox

import ch.tutteli.atrium.api.cc.en_UK.isEmpty
import ch.tutteli.atrium.api.cc.en_UK.toBe
import ch.tutteli.atrium.assert
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.given
import org.jetbrains.spek.api.dsl.it
import kotlin.reflect.KFunction3
import kotlin.reflect.KFunction4

class ListExtensionSpec : Spek({

    val append: (it: Int, sb: StringBuilder) -> Unit = { it, sb -> sb.append("a number: $it") }
    val separator = ", "
    val lastSeparator = " and "

    val joinToString: KFunction3<List<Int>, String, (Int, StringBuilder) -> Unit, String> = List<Int>::joinToString
    describe("fun ${joinToString.name} without lastSeparator") {
        given("empty list") {
            it("returns an empty string") {
                val result = listOf<Int>().joinToString(separator, append)
                assert(result).isEmpty()
            }
        }

        given("a list with one item") {
            it("returns a string according to the given append function") {
                val result = listOf(1).joinToString(separator, append)
                assert(result).toBe("a number: 1")
            }
        }

        given("a list with two items") {
            it("returns a string according to the given append function and uses the separator") {
                val result = listOf(1, 2).joinToString(separator, append)
                assert(result).toBe("a number: 1, a number: 2")
            }
        }
        given("a list with three items") {
            it("returns a string according to the given append function and uses the separator") {
                val result = listOf(1, 2, 3).joinToString(separator, append)
                assert(result).toBe("a number: 1, a number: 2, a number: 3")
            }
        }

    }

    describe("fun ${joinToString.name} with lastSeparator") {
        given("empty list") {
            it("returns an empty string") {
                val result = listOf<Int>().joinToString(separator, lastSeparator, append)
                assert(result).isEmpty()
            }
        }

        given("a list with one item") {
            it("returns a string according to the given append function") {
                val result = listOf(1).joinToString(separator, lastSeparator, append)
                assert(result).toBe("a number: 1")
            }
        }

        given("a list with two items") {
            it("returns a string according to the given append function and uses the lastSeparator") {
                val result = listOf(1, 2).joinToString(separator, lastSeparator, append)
                assert(result).toBe("a number: 1 and a number: 2")
            }
        }
        given("a list with three items") {
            it("returns a string according to the given append function and uses the separator as well as the lastSeparator") {
                val result = listOf(1, 2, 3).joinToString(separator, lastSeparator, append)
                assert(result).toBe("a number: 1, a number: 2 and a number: 3")
            }
        }

    }

    val appendToStringBuilder: KFunction4<List<Int>, StringBuilder, String, (Int) -> Unit, Unit> = List<Int>::appendToStringBuilder
    describe("fun ${appendToStringBuilder.name} without lastSeparator") {
        given("empty list") {
            it("does not append anything to the given StringBuilder") {
                val result = StringBuilder()
                listOf<Int>().appendToStringBuilder(result, separator) {append(it, result)}
                assert(result).isEmpty()
            }
        }

        given("a list with one item") {
            it("returns a string according to the given append function") {
                val result = StringBuilder()
                listOf(1).appendToStringBuilder(result, separator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1")
            }
        }

        given("a list with two items") {
            it("returns a string according to the given append function and uses the separator") {
                val result = StringBuilder()
                listOf(1, 2).appendToStringBuilder(result, separator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1, a number: 2")
            }
        }
        given("a list with three items") {
            it("returns a string according to the given append function and uses the separator") {
                val result = StringBuilder()
                listOf(1, 3, 2).appendToStringBuilder(result, separator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1, a number: 3, a number: 2")
            }
        }
    }

    describe("fun ${appendToStringBuilder.name} with lastSeparator") {
        given("empty list") {
            it("does not append anything to the given StringBuilder") {
                val result = StringBuilder()
                listOf<Int>().appendToStringBuilder(result, separator, lastSeparator) {append(it, result)}
                assert(result).isEmpty()
            }
        }

        given("a list with one item") {
            it("returns a string according to the given append function") {
                val result = StringBuilder()
                listOf(1).appendToStringBuilder(result, separator, lastSeparator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1")
            }
        }

        given("a list with two items") {
            it("returns a string according to the given append function and uses the lastSeparator") {
                val result = StringBuilder()
                listOf(1, 2).appendToStringBuilder(result, separator, lastSeparator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1 and a number: 2")
            }
        }
        given("a list with three items") {
            it("returns a string according to the given append function and uses the separator and lastSeparator") {
                val result = StringBuilder()
                listOf(1, 3, 2).appendToStringBuilder(result, separator, lastSeparator) {append(it, result)}
                assert(result.toString()).toBe("a number: 1, a number: 3 and a number: 2")
            }
        }
    }
}) {
    private class DummyWithList(val list: List<Int>)
}