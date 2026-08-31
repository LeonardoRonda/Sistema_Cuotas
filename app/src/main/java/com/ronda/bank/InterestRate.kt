package com.ronda.bank

import java.security.KeyStore
import java.time.LocalDate
import java.time.format.DateTimeFormatter

val fechaActual = LocalDate.now()
val formato = DateTimeFormatter.ofPattern("dd/MM/yyyy")
val fechaFormateada = fechaActual.format(formato)


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
    var opcion = readln().toIntOrNull() ?: 0
    do {
        println("=========================================")
        println(" Menu-Pagos En Cuotas ")
        println("=========================================")
        println("1. Agregar producto")
        println("2. Elegir cuotas")
        println("3. Imprimir boleta")
        println("4. Salir")
        print("Selecciona una opción: ")

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
                println("Ingresa el numero de cuotas: ")
                println("1. 6 cuotas")
                println("2. 12 cuotas")
                println("3. 24 cuotas")
                val cuotas = readln().toInt()
                val sub = calcularSubtotal(carrito)
                val i = calcularIGV(sub)
                val total = calcularTotal(sub,i)
                // se usa un when para la seleccion de cuotas
                when (cuotas){
                    6 -> {
                        val interes = total * 0.2
                        val montoTotal = total + interes
                        val cuota = montoTotal / 6
                        println("opcion elegida: 6 cuotas")
                    }
                    12 -> {
                        val interes = total * 0.2
                        val montoTotal = total + interes
                        val cuota = montoTotal / 6
                        println("opcion elegida: 12 cuotas")
                    }
                    24 -> {
                        val interes = total * 0.2
                        val montoTotal = total + interes
                        val cuota = montoTotal / 6
                        println("opcion elegida: 24 cuotas")
                    }
                    else -> {
                        println("Opcion no valida, intentelo de nuevo")
                    }
                }
            }
            3 -> {
                println("")
                println("=========================================")
                println(" CARRITO DE COMPRAS - TIENDA TECSUP ")
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
