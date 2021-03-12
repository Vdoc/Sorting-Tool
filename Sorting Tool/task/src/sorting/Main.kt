package sorting

import java.util.*

//val test = true
val test = false

fun main(args: Array<String>) {
    val arg2 = if (args.size == 2) {
         args[1]
//    } else "long"
//    } else "line"
    } else "word"

    Parser(arg2)
}

class Parser {
    val arr: Array<Any>
    var _X: String = ""
    var _Y: String = ""
    var _Z: String = ""
    var _P: Int = 0
    val data: Array<String>
    var elements: Array<String> = emptyArray<String>()
    var sortedElements: Array<String> = emptyArray<String>()

    constructor(arg2: String) {
        arr = when (arg2) {
            "long" -> arrayOf(1, "numbers", "greatest number: ")
            "line" -> arrayOf(2, "lines", "longest line: \n")
            else -> arrayOf(3, "words", "longest word: ")
        }
        data = readInput()
        _X = countX()
        _Y = findY()
        _Z = countZ()
        _P = 100 / elements.size
        output(_X, _Y, _Z, _P)
    }

    fun readInput(): Array<String> {
        val space = " " // без пробелав в конце может последнее число или word не прочитать

        val inputString: String =
            if (test) {
                "1 -2   333 4\n" +
                "42\n" +
                "1                 1" + space
            } else {
                val scanner = Scanner(System.`in`)
                var input: String = scanner.nextLine()

                while (scanner.hasNext()) {
                    input += "\n" + scanner.nextLine()
                }
                if (arr[0] == 1 || arr[0] == 3) input += space
                input
            }
        return stringToStringArray(inputString)
    }

    private fun stringToStringArray(inputString: String): Array<String> {
        var strings: Array<String> = emptyArray<String>()
        var num: String = ""

        when (arr[0]) {
            1 -> {
                for (i in 0 until inputString.length) {
                    if (inputString[i].isDigit() || inputString[i] == '-' || inputString[i] == '_') {
                        num += inputString[i]
                    } else {
                        strings += num
                        num = ""
                        continue
                    }
                }
            }
            2 -> {
                var str = inputString.split("\n")
                for (i in 0 until str.size) {
                    strings += str[i]
                }
            }
            else -> {
                for (i in 0 until inputString.length) {
                    if (inputString[i] == ' ' || inputString[i] == '\n') {
                        strings += num
                        num = ""
                        continue
                    } else {
                        num += inputString[i]
                    }
                }
            }
        }
        return strings
    }

    private fun countX(): String {
        for (i in 0 until data.size) {
            when (arr[0]) {
                1 -> if (data[i] != " " && data[i] != "") elements += data[i]
                2 -> elements += data[i]
                else -> if (data[i] != " " && data[i] != "") elements += data[i]
            }
        }
        return elements.size.toString()
    }

    private fun findY(): String {
        bubleSort(elements)
        return greatestElement()
    }

    // O(n2) complexity
    fun bubleSort(elements: Array<String>) {
        sortedElements = elements

        for (i in 0 until sortedElements.size) {
            for (j in i + 1 until sortedElements.size) {
                when (arr[0]) {
                    1 -> if (sortedElements[i].toInt() > sortedElements[j].toInt()) swap(i, j, sortedElements)
                    else -> {
                        if (sortedElements[i].length > sortedElements[j].length) {
                            swap(i, j, sortedElements)
                        }
                        if (sortedElements[i].length == sortedElements[j].length) {
                            compareStrings(i, j, sortedElements, 0)
                        }
                    }
                }
            }
        }
    }

    private fun compareStrings(i: Int, j: Int, sortedElements: Array<String>, chr: Int) {
        if (sortedElements[i][chr] > sortedElements[j][chr])
            swap(i, j, sortedElements)
        if (sortedElements[i][chr] == sortedElements[j][chr] && chr < sortedElements[i].length - 1)
            compareStrings(i, j, sortedElements, chr + 1)
    }

    fun swap(a: Int, b: Int, array: Array<String>) {
        val temp = array[b]
        array[b] = array[a]
        array[a] = temp
    }

    fun greatestElement(): String = when(arr[0]) {
        1 -> sortedElements[sortedElements.size - 1]
        2 -> sortedElements[sortedElements.size - 1]
        else -> sortedElements[sortedElements.size - 1]
    }

    private fun countZ(): String {
        var counter = 0
        for (i in 0 until elements.size) {
            if (elements[i] == _Y) counter++
        }
        if (arr[0] == 2) _Y += "\n"
        return counter.toString()
    }

    private fun output(_X: String, _Y: String, _Z: String, _P: Int) {
        println("Total ${arr[1]}: $_X.\n" +
                "The ${arr[2]}$_Y ($_Z time(s), $_P%).")
    }
}