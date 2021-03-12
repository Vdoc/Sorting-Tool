package sorting

import java.util.*

//val test = true
val test = false

fun main() {
    val space = " " // без пробелав в конце может последнее число не прочитать

    val input: String =
    if (test) {
        "1 -2   33 4\n" +
        "42\n" +
        "1                 1" + space
    } else readInput() + space
    var ints: Array<Int> = stringToIntArray(input)

    val _X: Int = nomberOfIntegers(ints)
    val sortedInts: Array<Int> = bubleSort(ints)
    val _Y: Int = greatestNumber(sortedInts)
    val _Z = howMany(_Y, ints)

    println("Total numbers: $_X.\n" +
            "The greatest number: $_Y ($_Z time(s)).")
}

fun howMany(_Y: Int, ints: Array<Int>): Any {
    var counter = 0
    for (i in 0 until ints.size) {
        if (ints[i] == _Y) counter++
    }
    return counter
}

fun greatestNumber(sortedInts: Array<Int>): Int {
    return sortedInts[sortedInts.size - 1]
}

// O(n2) complexity
fun bubleSort(ints: Array<Int>): Array<Int> {
    val sortedInts: Array<Int> = ints

    for (i in 0 until ints.size) {
        for (j in i + 1 until ints.size) {
            if (ints[i] > ints[j]) swap(i, j, sortedInts)
        }
    }
    return sortedInts
}

fun nomberOfIntegers(ints: Array<Int>): Int {
    return ints.size
}

fun stringToIntArray(input: String): Array<Int> {
    var ints: Array<Int> = emptyArray<Int>()
    var strings: Array<String> = emptyArray<String>()
    var num: String = ""
    for (i in 0 until input.length) {
        if (input[i].isDigit() || input[i] == '-') {
            num += input[i]
        } else {
            strings += num
            num = ""
            continue
        }
    }

    for (i in 0 until strings.size) {
        if (strings[i] != " " && strings[i] != "") {
            ints += strings[i].toInt()
        }
    }
    return ints
}

fun swap(a: Int, b: Int, array: Array<Int>) {
    val temp = array[b]
    array[b] = array[a]
    array[a] = temp
}

fun readInput(): String {
    val scanner = Scanner(System.`in`)
    var input: String = ""

    while (scanner.hasNext()) {
        input += " " + scanner.nextLine()
    }
    return input
}
