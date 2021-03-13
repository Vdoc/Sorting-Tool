package sorting

import java.util.*

//val test = true
val test = false
fun test(){
    println("word")
    Parser("word")
    println()
    println("long")
    Parser("long")
    println()
    println("int")
    Parser("int")
    println()
    println("line")
    Parser("line")
}

fun main(args: Array<String>) {
    if (test) test()
    else {
        var strategy = args.joinToString(" ").split(" ").last()
        args.forEach {
            if (it.contains("-sortIntegers")) strategy =  "int"
        }
        Parser(strategy)
    }
}

class Parser {
    val strategy: Int
    val lineStrategy = 1
    val wordStrategy = 2
    val longStrategy = 3
    val intStragegy = 4
    val preparedString1: String
    val preparedString2: String

    val _X_size: Int
    var _Y: String = ""
    var _Z: String = ""
    var _P: Int = 0

    var data: Array<String> = emptyArray<String>()
    var elements: Array<String> = emptyArray<String>()
    var sortedElements: Array<String> = emptyArray<String>()

    constructor(_strategy: String) {
        when (_strategy) {
            "line" -> {
                strategy = lineStrategy
                preparedString1 = "Total lines: "
                preparedString2 = "The longest line: \n"
            }
            "long" -> {
                strategy = longStrategy
                preparedString1 = "Total numbers: "
                preparedString2 = "The greatest number: "
            }
            "int" -> {
                strategy = intStragegy
                preparedString1 = "Total numbers: "
                preparedString2 = "Sorted data: "
            }
            else -> {   // "word"
                strategy = wordStrategy
                preparedString1 = "Total words: "
                preparedString2 = "The longest word: "
            }
        }
        readInput()
        eliminateEmptyElements()
        _X_size = elements.size
        bubleSort()
        _Y = greatestElement()
        _Z = count_Y()
        _P = 100 * _Z.toInt() / elements.size
        output()
    }

    fun readInput(){
        val space = " " // без пробелав в конце может последнее число или word не прочитать
        val inputString: String =
            if (test) {
                "1 -2   33 4\n" +
                        "42\n" +
                        "1                 1" + space
            } else {
                val scanner = Scanner(System.`in`)
                var input: String = scanner.nextLine()

                while (scanner.hasNext()) {
                    input += "\n" + scanner.nextLine()
                }
                // для строк пробел в конце не нужен
                if (strategy != lineStrategy) input += space
                input
            }
        stringToStringArray(inputString)
    }

    private fun stringToStringArray(inputString: String){
        var num: String = ""
        when (strategy) {
            lineStrategy -> {
                var str = inputString.split("\n")
                for (i in 0 until str.size) {
                    data += str[i]
                }
            }
            longStrategy,
            intStragegy -> {
                for (i in 0 until inputString.length) {
                    if (inputString[i].isDigit() || inputString[i] == '-' || inputString[i] == '_') {
                        num += inputString[i]
                    } else {
                        data += num
                        num = ""
                        continue
                    }
                }
            }
            else -> {   // wordStrategy
                for (i in 0 until inputString.length) {
                    if (inputString[i] == ' ' || inputString[i] == '\n') {
                        data += num
                        num = ""
                        continue
                    } else {
                        num += inputString[i]
                    }
                }
            }
        }
    }

    private fun eliminateEmptyElements() {
        for (i in 0 until data.size) {
            when (strategy) {
                lineStrategy -> elements += data[i]
                else -> if (data[i] != " " && data[i] != "") elements += data[i]
            }
        }
    }

    // The merge sort algorithm (O(n log n) complexity) better than bubble sort, insertion sort, and selection sort.
    // Merge sort can be used to sort even large arrays

    // bubble sort O(n2) complexity
    private fun bubleSort() {
        sortedElements = elements

        for (i in 0 until _X_size) {
            for (j in i + 1 until _X_size) {
                when (strategy) {
                    longStrategy, intStragegy ->
                        if (sortedElements[i].toInt() > sortedElements[j].toInt()) swap(i, j, sortedElements)
                    else -> {  // wordStrategy or lineStrategy
                        if (sortedElements[i].length > sortedElements[j].length) swap(i, j, sortedElements)
                        if (sortedElements[i].length == sortedElements[j].length) compareStrings(i, j, sortedElements, 0)
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

    private fun swap(a: Int, b: Int, array: Array<String>) {
        val temp = array[b]
        array[b] = array[a]
        array[a] = temp
    }

    private fun greatestElement(): String = sortedElements[_X_size - 1]

    private fun count_Y(): String {
        var counter = 0
        for (i in 0 until _X_size) {
            if (elements[i] == _Y) counter++
        }
        if (strategy == lineStrategy) _Y += "\n"
        return counter.toString()
    }

    private fun output() {
        println("${preparedString1}$_X_size.")
        if (strategy == intStragegy)
            println("${preparedString2}${printElements()}")
        else // wordStrategy or lineStrategy or longStrategy
            println("${preparedString2}$_Y ($_Z time(s), $_P%).")
    }

    private fun printElements(): String {
        var out = sortedElements[0]
        if (_X_size > 1)
            for (i in 1 until _X_size) {
                out += " " + sortedElements[i]
            }
        return out
    }
}