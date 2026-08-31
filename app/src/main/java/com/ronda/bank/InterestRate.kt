package com.ronda.bank

import java.security.KeyStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter
data class Producto(
    val nombre: String,
    val precio: Double,
    val cantidad: Int
)
fun main() {

    println("Escribe el nombre del cliente")
    val nombreCliente = readln()
    val carrito = mutableListOf<Producto>()

    //Esta opción es para el menu usando while do y un when para el menú de opcion.
    var numeroCuotas = 0
    do {
        println("=========================================")
        println(" Menu-Pagos En Cuotas ")
        println("=========================================")
        println("1. Agregar producto")
        println("2. Elegir cuotas")
        println("3. Imprimir boleta")
        println("4. Salir")
        print("Selecciona una opcion: ")
        var opcion = readln().toIntOrNull() ?: 0
        when(opcion){
            1 -> {
                println("-----------------------------------------")
                println("            Agregar producto             ")
                println("-----------------------------------------")
                println("Ingresa el nombre del producto: ")
                val nombreP = readln()
                println("Ingresa el precio: ")
                val precioP = readln().toDouble()
                println("Ingresa la cantidad: ")
                val cantidadP = readln().toInt()
                carrito.add(Producto(nombreP, precioP, cantidadP))
            }
            2 -> {
                println("-----------------------------------------")
                println("            Elegir cuotas             ")
                println("-----------------------------------------")
                println("Ingresa el numero de cuotas (6, 12, 24): ")
                val cuotas = readln().toInt()
                val interes = calcularInteres(cuotas)
                if (interes > 0.0) {
                    numeroCuotas = cuotas
                    println("Cuotas elegidas: $cuotas")
                    println("Interes calculado: $interes")
                } else {
                    println("Numero de cuotas no valido")
                }
            }
            3 -> {
                println("")
                println("=========================================")
                println(" Resumen de la compra ")
                println("=========================================")


                println("Cliente: $nombreCliente")
                println()

                val sub = calcularSubtotal(carrito)
                val i = calcularIGV(sub)
                val total = calcularTotal(sub,i)

                mostrarDetalle( carrito)
                println(String.format("%-20s %d", "Cantidad de productos:", carrito.size))
                println(String.format("%-20s S/ %8.2f", "Subtotal:", sub))
                println(String.format("%-20s S/ %8.2f", "IGV:", i))
                println(String.format("%-20s S/ %8.2f", "TOTAL A PAGAR:", total))
                println("=========================================")
                if (numeroCuotas > 0) {
                    val interes = calcularInteres(numeroCuotas)
                    val  montoConInteres = total + (total * interes)
                    println("Monto con interes: $montoConInteres")
                    println("Numero de cuotas: $numeroCuotas")
                    mostrarCronograma(montoConInteres, numeroCuotas)
                }
            }
            4 -> {
                println("Gracias por su compra")
            }
            else -> {
                println("Opcion no valida, intentelo de nuevo")
            }
        }
    } while (opcion != 4) // Se repite hasta que el usuario marque 4
}


fun calcularSubtotal(productos: List<Producto>): Double {
    var subtotal = 0.0
    for (p in productos) {
        subtotal += p.precio * p.cantidad
    }
    return subtotal
}
fun calcularIGV(subtotal: Double): Double {
    val igv = subtotal * 0.18
    return igv
}
fun calcularTotal(subtotal: Double, igv: Double): Double {
    return subtotal + igv
}

fun mostrarDetalle(productos: List<Producto>) {
    println("--------- DETALLE DEL CARRITO ---------")
    var a = 1
    for (p in productos) {
        val importe = p.precio * p.cantidad
        println(String.format("%d. %-20s x%d S/ %8.2f", a, p.nombre, p.cantidad, importe))
        a++
    }
    println("---------------------------------------")
}

fun calcularInteres(cuotas: Int): Double {
    return when (cuotas) {
        6 -> 0.2
        12 -> 0.4
        24 -> 0.6
        else -> 0.0
    }
}
fun mostrarCronograma(montoTotal: Double, cuotas: Int){
    val montoCuota = montoTotal / cuotas
    val fechaActual = LocalDate.now() // extrae la fecha actual
    val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy") // le da formato a la fecha

    println("\n=========================================")
    println("          CRONOGRAMA DE PAGOS            ")
    println("=========================================")
    println(String.format("%-10s | %-12s | %-10s", "N° Cuota", "Fecha", "Monto"))
    println("-----------------------------------------")

    // bucle para cambiar la fecha de la cuota de manera mensual
    for (i in 1..cuotas) {
        val fechaPago = fechaActual.plusMonths(i.toLong())
        println(String.format("%-5d | %-12s | S/ %8.2f", i, fechaPago.format(formato), montoCuota))
    }
    println("=========================================\n")


}
