import java.util.*


fun main(){
    println("Hola mundo")
    // INMUTABLES (NO se reasignan "=")
    val inmutable: String = "Byron";
    // inmutable = "Vicente";

    // Mutables (Re asignar)
    var mutable: String = "Marcelo";
    mutable = "Byron";

    // val > var
    // Duck Typing
    var ejemploVariable = "Byron Ortiz"
    val edadEjemplo: Int = 12
    ejemploVariable.trim()
    // ejemploVariable = edadEjemplo;



    // Variable primitiva

    val nombreProfesor: String = "Byron Ortiz"
    val sueldo: Double = 1.2
    val estadoCivil: Char = 'C'
    val mayorEdad: Boolean = true


    // Clases Java
    val fechaNacimiento: Date = Date()


    // SWITCH

    val estadoCivilWhen = "C"
    when (estadoCivilWhen){
        ("C") -> {
            println("Casado")
        }
        "S" -> {
            println("Soltero")
        }
        else -> {
            println("No sabemos")
        }
    }
    val coqueteo = if (estadoCivilWhen == "S") "Si" else "No"

    calcularSueldo(10.00)
    calcularSueldo(10.00,15.00)
    calcularSueldo(20.00,12.00,20.00)

    // Parametros nombrados
    calcularSueldo(sueldo = 10.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00)
    calcularSueldo(sueldo = 10.00, tasa = 15.00, bonoEspecial = 20.00)

    calcularSueldo(sueldo = 10.00,bonoEspecial = 20.00)
    calcularSueldo(10.00,bonoEspecial = 20.00)

    calcularSueldo(bonoEspecial = 20.00, sueldo = 10.00, tasa = 14.00)

    val sumarUno = Suma(1,1)
    val sumarDos = Suma(null,1)
    val sumaTres = Suma(1,null)
}

fun impirmirNombre(nombre: String): Unit{
    // "Nombre: " +variable + "bienvenido";
    println("Nombre : ${nombre}")
}


fun calcularSueldo(
    sueldo: Double, // requerido
    tasa: Double =12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null
): Double{
    // Int -> ? (nullable)
    //string -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa ) + bonoEspecial
    }
}


///////// clases abtraptas con  lenguaje java
abstract class NumerosJava{
    protected val numeroUno: Int
    private val numeroDos: Int

    constructor(
        uno: Int,
        dos: Int
    ){// bloque de codigo del constructor
        this.numeroUno= uno
        this.numeroDos= dos
        println("Inicializado")
    }
}

// Clase abstracta para constructor primario en lenguaje kotli
abstract class Numeros ( // constructor primario

    // propiedad de la clase protectes numero.numerdos
    protected val numeroUno: Int,

    // propiedad de la clase protectes numero.numerdos
    protected val numeroDos: Int,
){
    //var cedula: string = " " (public es por defecto)
    // private valorcalculado: int =0 (private)

    init{ //bloque codigo constructor primario
        this.numeroUno; this.numeroDos; //this es opcional
        numeroUno; numeroDos; // sin el this, es lo mismo
        println("Inicializando")
    }
}

class Suma ( //constructor primario suma
    uno: Int,
    dos: Int
): Numeros(uno, dos) {
    init { // bloque constructor
        this.numeroUno; numeroUno;
        this.numeroDos; numeroDos;
    }

    constructor( // Segundo constructor
        uno: Int?,
        dos: Int
    ) : this( // Llamada constructor primario
        if (uno == null) 0 else uno,
        dos
    ) {// si necesitamos bloque de codigo lo usamos
        numeroUno;
    }

    constructor( // tercer constructor
        uno: Int,
        dos: Int?
    ) : this(
        uno,
        if (dos == null) 0 else uno
    )
    // Si no lo necesitamos al bloque de codigo "{}" lo omitimos

    constructor(//  cuarto constructor
        uno: Int?, // parametros
        dos: Int? // parametros
    ) : this(  // llamada constructor primario
        if (uno == null) 0 else uno,
        if (dos == null) 0 else uno
    )
    // public por defecto, o usar private o protected
    public fun sumar(): Int {
        val total = numeroUno + numeroDos
        agregarHistorial(total)
        return total
    }
    companion object { // Atributos y Metodos "Compartidos"
        // entre las instancias
        val pi = 3.14
        fun elevarAlCuadrado(num: Int): Int {
            return num * num
        }
        val historialSumas = arrayListOf<Int>()
        fun agregarHistorial(valorNuevaSuma:Int){
            historialSumas.add(valorNuevaSuma)
        }
    }

}
// void -> Unit
fun imprimirNombre(nombre: String): Unit{
    // "Nombre: " + variable + " bienvenido";
    println("Nombre : ${nombre}")
}
fun calcularSueldo(
    sueldo: Double, // Requerido
    tasa: Double = 12.00, // Opcional (defecto)
    bonoEspecial: Double? = null, // Opcion null -> nullable
): Double{
    // Int -> Int? (nullable)
    // String -> String? (nullable)
    // Date -> Date? (nullable)
    if(bonoEspecial == null){
        return sueldo * (100/tasa)
    }else{
        bonoEspecial.dec()
        return sueldo * (100/tasa ) + bonoEspecial
    }
}


