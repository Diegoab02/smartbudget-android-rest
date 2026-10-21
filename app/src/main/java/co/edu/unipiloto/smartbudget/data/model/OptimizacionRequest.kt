package co.edu.unipiloto.smartbudget.data.model

import com.google.gson.annotations.SerializedName

/**
 * Payload para POST /optimizar.
 * El usuario envía una consulta de producto + presupuesto máximo en COP,
 * el backend devuelve la mejor combinación posible dentro de ese presupuesto.
 */
data class OptimizacionRequest(
    @SerializedName("consulta")         val consulta: String,
    @SerializedName("presupuestoCop")   val presupuestoCop: Double,
    @SerializedName("tiendas")          val tiendas: List<String>? = null,
    @SerializedName("incluirAgotados")  val incluirAgotados: Boolean = false
)

data class OptimizacionResponse(
    @SerializedName("consulta")         val consulta: String,
    @SerializedName("presupuestoCop")   val presupuestoCop: Double,
    @SerializedName("totalCop")         val totalCop: Double,
    @SerializedName("ahorroCop")        val ahorroCop: Double,
    @SerializedName("productos")        val productos: List<Producto>
)
