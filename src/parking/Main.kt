package parking
import java.util.Scanner


fun searchPark(parking: Array<Car>, color: String? = null, registration: String? = null,
    ByColor: Boolean = false, search: String = "", ByReg: Boolean = false): String {

    var found = false
    val res = ArrayList<Car>()

    for (i in parking.indices) {
        if (parking[i].position != -1) {
            if (ByColor && parking[i].color.equals(color, ignoreCase = true)){
                res.add(parking[i])
                found = true
            }

            if (ByReg && parking[i].code == registration){
                return (parking[i].position+1).toString()
            }

        }
    }

    if (!found) {
        if (ByColor) {
            return "No cars with color $color were found."
        }
        else if (ByReg) {
            return "No cars with registration number $registration were found."
        }
    }

    var resString = ""

    for (i in res.indices) {

        if (i == res.lastIndex) {
            if (search == "reg")
                resString += res[i].code
            else if (search == "spot")
                resString += (res[i].position+1).toString()
        }
        else {
            if (search == "reg")
                resString += "${res[i].code}, "
            else if (search == "spot")
                resString += "${(res[i].position+1).toString()}, "
        }
    }
    return resString
}

fun status(parking: Array<Car>, color: String = ""){
    var empty = 0
    var colorFound = false
    for (i in parking.indices){
        if (parking[i].position != -1){
            println("${parking[i].position + 1} ${parking[i].code} ${parking[i].color}")
        }
        else {
            ++empty
        }
    }
    if (empty == parking.size){
        println("Parking lot is empty.")
    }
}


fun main() {
    val input = Scanner(System.`in`)

    var parking = Array(0) {Car()}
    while(true) {
        val command = input.next()

        if (command=="exit"){
            break
        }

        if (parking.isEmpty() && command!="create"){
            println("Sorry, a parking lot has not been created.")
            input.nextLine()
            continue
        }
        else if (command == "create"){
            val int = input.nextInt()
            parking = Array(int) {Car()}
            println("Created a parking lot with $int spots.")
        }

        when (command) {
            "spot_by_color" -> {
                val color = input.next()
                println(searchPark(parking=parking,color=color, ByColor=true, search="spot"))
            }
            "reg_by_color" -> {
                val color = input.next()
                println(searchPark(parking=parking,color=color, ByColor=true, search="reg"))
            }
            "spot_by_reg" -> {
                val reg = input.next()
                println(searchPark(parking=parking,registration=reg, ByReg=true))
            }
        }

        if (command == "park") {
            val num = input.next()
            val color = input.next()
            var found = false
            for (i in parking.indices) {
                if (parking[i].position == -1) {
                    parking[i].color = color
                    parking[i].code = num
                    parking[i].position = i
                    println("$color car parked in spot ${i+1}.")
                    found = true
                    break
                }
            }

            if (!found)
                println("Sorry, the parking lot is full.")
        }

        else if (command == "status") {
            status(parking)
        }

        else if (command == "leave") {
            val num = input.nextInt()-1
            if (parking[num].position != -1) {
                parking[num] = Car()
                println("Spot ${num+1} is free.")
            } else if (parking[num].position == -1) {
                println("Something else")
            }
        }
    }
}
