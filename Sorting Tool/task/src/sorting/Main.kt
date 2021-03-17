package sorting

import java.io.File
import java.util.*

//val test = true
val test = false

fun main(args: Array<String>) { // работает => не трогай!!!
    if (test) test()
    else {
        var dataType: String = ""
        var sortingType: String = ""
        var inputFile: String = ""
        var outputFile: String = ""

        var bool: Boolean = false

        val arguments = arrayOf("-dataType", "-sortingType", "line", "long", "int", "word", "natural", "byCount")

        if (args.size > 0) {
            for (i in 0..args.size - 1) {
                if (args[i].contains("-dataType")) {
                    if (i != args.size - 1
                            && args[i + 1] != "-sortingType") {
                        dataType = args[i + 1]
                        continue
                    } else if (dataType == "") {
                        println("No data type defined!")
                    dataType = "word"
//                    throw Exception("No data type defined!")
                        return
                    }
                }
                if (args[i].contains("-sortingType")) {
                    if (i != args.size - 1 &&
                            args[i + 1] != "-dataType") {
                        sortingType = args[i + 1]
                        continue
                    } else if (sortingType == "") {
                        println("No sorting type defined!")
                    sortingType = "natural"
//                    throw Exception("No sorting type defined!")
                        return
                    }
                }
                if (args[i].contains("-inputFile")) {
                    inputFile = args[i + 1]
                    bool = true
                    continue
                }
                if (args[i].contains("-outputFile")) {
                    outputFile = args[i + 1]
                    bool = true
                    continue
                }
                if (!arguments.contains(args[i]) && !bool) println("\"${args[i]}\" is not a valid parameter. It will be skipped.")
                bool = false
            }
        }
        Parser(dataType, sortingType, inputFile, outputFile)
    }
}

class Parser {
    val type: Int
    val sort: Boolean
    val lineStrategy = 1
    val wordStrategy = 2
    val longStrategy = 3
    val intStrategy = 4
    val preparedString1: String
    val preparedString2: String = "Sorted data: "

    val _X_size: Int

    var data: Array<String> = emptyArray<String>()
    var elements: Array<String> = emptyArray<String>()
    var sortedElements: Array<String> = emptyArray<String>()

    var inputFile: String = ""
    var outputFile: String = ""

    constructor(dataType: String, sortingType: String, _inputFile: String, _outputFile: String) {
        sort = if (sortingType == "byCount") false else true

        if (_outputFile != "") outputFile = _outputFile
        when (dataType) {
            "line" -> {
                type = lineStrategy
                preparedString1 = "Total lines: "
            }
            "long" -> {
                type = longStrategy
                preparedString1 = "Total numbers: "
            }
            "int" -> {
                type = intStrategy
                preparedString1 = "Total numbers: "
            }
            else -> {   // "word"
                type = wordStrategy
                preparedString1 = "Total words: "
            }
        }
        if (_inputFile != "") {
            inputFile = _inputFile
            readInputFile(inputFile)
        } else readInput()
        eliminateEmptyElements()
        _X_size = elements.size
        bubleNaturalSort()

        if (_outputFile != "") {
            outputFile = _outputFile
            outputInFile(outputFile)
        } else output()
    }

    private fun readInputFile(inputFile: String) {
        val file = File(inputFile)
        var text = file.readText()
        if (type != lineStrategy) text += " "
        stringToStringArray(text)
    }

    fun readInput(){
        val space = " " // без пробелов в конце может последнее число или word не прочитать
        val inputString: String = if
                (test) {
                        "1 -2   333 4\n" +
                        "42\n" +
                        "1                 1" + space
            } else {
                val scanner = Scanner(System.`in`)
                var input: String = scanner.nextLine()

                while (scanner.hasNext()) {
                    input += "\n" + scanner.nextLine()
                }
                if (type != lineStrategy) input += space
                input
            }
        stringToStringArray(inputString)
    }

    private fun stringToStringArray(inputString: String){
        var num: String = ""
        var skippedString: String = ""
        when (type) {
            lineStrategy -> {
                var str = inputString.split("\n")
                for (i in 0 until str.size) {
                    data += str[i]
                }
            }
            longStrategy,
            intStrategy -> {
                for (i in 0 until inputString.length) {
                    if (inputString[i].isDigit() || inputString[i] == '-' || inputString[i] == '_') {
                        num += inputString[i]
                        if (skippedString.length > 0) {
                            println("\"$skippedString\" is not a long. It will be skipped.")
                            skippedString = ""
                        }
                    } else {
                        if (inputString[i] != ' ' && inputString[i] != '\n')
                            skippedString += inputString[i]
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
            when (type) {
                lineStrategy -> elements += data[i]
                else -> if (data[i] != " " && data[i] != "") elements += data[i]
            }
        }
    }

    // The merge sort algorithm (O(n log n) complexity) better than bubble sort, insertion sort, and selection sort.
    // Merge sort can be used to sort even large arrays
    // TODO Merge sort

    // bubble sort O(n2) complexity
    private fun bubleNaturalSort() {
        sortedElements = elements

        for (i in 0 until _X_size) {
            for (j in i + 1 until _X_size) {
                when (type) {
                    wordStrategy, lineStrategy -> {
                        compareStringsNatural(i, j)
                    }
                    else -> {   // longStrategy, intStragegy
                        if (sortedElements[i].toInt() > sortedElements[j].toInt()) swap(i, j, sortedElements)
                    }
                }
            }
        }
    }

    private fun compareStringsNatural(i: Int, j: Int) {
        var descendSort = false

        var chrI = 0
        var chrJ = 0
        if (sortedElements[i].first() == '-')
            chrI++
        if (sortedElements[j].first() == '-')
            chrJ++
        if (chrI > chrJ)
            return
        if (chrI < chrJ) {
            swap(i, j, sortedElements)
            return
        }
        if (chrI == 1 && chrJ == 1)
            descendSort = true

        val iLength = sortedElements[i].length
        val jLength = sortedElements[j].length
        val minLength = Math.min(iLength, jLength)
        val ch = chrI
        for (k in ch until minLength) {
            if (sortedElements[i][k] == sortedElements[j][k]) continue
            if (sortedElements[i][ch] < sortedElements[j][ch] && descendSort) {
                swap(i, j, sortedElements)
                return
            }
            if (sortedElements[i][k] < sortedElements[j][k]) return
            if (sortedElements[i][k] > sortedElements[j][k] && !descendSort) {
                swap(i, j, sortedElements)
                return
            }
            if (sortedElements[i][k] > sortedElements[j][k])
                return
        }

        // если в одной из строк частей больше и при этом
        // все, кроме "лишних", совпадают с другой строкой, то эта строка "больше"
        if (compare(sortedElements[i].length, sortedElements[j].length)) swap(i, j, sortedElements)
    }

    fun compare(a: Int, b: Int): Boolean = if (a > b) true else false

    private fun swap(a: Int, b: Int, array: Array<String>) {
        val temp = array[b]
        array[b] = array[a]
        array[a] = temp
    }

    private fun count(string: String): String {
        var counter = 0
        for (i in 0 until _X_size) {
            if (sortedElements[i] == string) counter++
        }
        return counter.toString()
    }

    private fun output() {
        println("${preparedString1}$_X_size.")
        if (sort) {
            print(preparedString2)
            println(printElements())
        } else println(printStatistics())
    }

    private fun outputInFile(outputFile: String) {
        val file = File(outputFile)
        var out = "${preparedString1}$_X_size."

        if (sort) {
            out += preparedString2
            out += printElements()
        } else out += printStatistics()
        file.writeText(out)
    }

    private fun printElements(): String {
        var out = ""
        if (type == lineStrategy) out += "\n"
        out += sortedElements[0]
        if (_X_size > 1)
            for (i in 1 until _X_size) {
                out += when (type) {
                    lineStrategy -> "\n" + sortedElements[i]
                    else -> " " + sortedElements[i]
                }
            }
        return out
    }

    private fun printStatistics(): String {
        val list: MutableList<List<String>> = mutableListOf<List<String>>()
        var out = ""

        var c = 0
        var p = 0
        for (i in 0 until _X_size) {
            c = count(sortedElements[i]).toInt()
            p = 100 * c / _X_size
            list.add(listOf(p.toString(), c.toString(), sortedElements[i], "${sortedElements[i]}: $c time(s), $p%\n"))
        }

        val list2 = sortByCount(list)

        for (i in 0 until list2.size) {
            out += list2[i][3]
        }

        return out
    }

    private fun sortByCount(list: MutableList<List<String>>): MutableList<List<String>> {
        var min = 0
        val list3: MutableList<List<String>> = mutableListOf<List<String>>()
        var size: Int = list.size
        while (size != 0) {
            for (i in 1 until size) {
                if (list[i][0].toInt() < list[min][0].toInt()) {
                    min = i
                }
            }
            list3 += list[min]
            list.removeAt(min)
            min = 0
            size--
        }

        return eliminateDuplicateElements(list3)
    }

    private fun eliminateDuplicateElements(list3: MutableList<List<String>>): MutableList<List<String>> {
        val list4: MutableList<List<String>> = mutableListOf<List<String>>()
        var size = list3.size
        var a: String = ""
        var c: Int = 0
        while (size != 0) {
            a = list3[0][2]
            c = list3[0][1].toInt()
            while (c != 1) {
                if (list3[1][2] == a) {
                    list3.removeAt(1)
                    size--
                    c--
                }
            }
            list4 += list3[0]
            list3.removeAt(0)
            size--
        }
        return list4
    }
}

fun test(){

    println("long natural\n============")
    Parser("long", "natural", "", "")
    println("============\n")

    println("int natural\n============")
    Parser("int", "natural", "", "")
    println("============\n")

    println("word natural\n============")
    Parser("word", "natural", "", "")
    println("============\n")

    println("line natural\n============")
    Parser("line", "natural", "", "")
    println("============\n")



    println("long byCount\n============")
    Parser("long", "byCount", "", "")
    println("============\n")

    println("int byCount\n============")
    Parser("int", "byCount", "", "")
    println("============\n")

    println("word byCount\n============")
    Parser("word", "byCount", "", "")
    println("============\n")

    println("line byCount\n============")
    Parser("line", "byCount", "", "")
    println("============\n")
}