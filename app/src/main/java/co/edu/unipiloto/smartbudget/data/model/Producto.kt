package co.edu.unipiloto.smartbudget.data.model

import com.google.gson.annotations.SerializedName

/**
 * Producto de alguna de las Top 10 tiendas colombianas comparadas por SmartBudget.
 */
data class Producto(
    @SerializedName("id")           val id: Long,
    @SerializedName("nombre")       val nombre: String,
    @SerializedName("tienda")       val tienda: String,
    @SerializedName("categoria")    val categoria: String?,
    @SerializedName("precio")       val precio: Double,
    @SerializedName("moneda")       val moneda: String = "COP",
    @SerializedName("urlImagen")    val urlImagen: String?,
    @SerializedName("urlProducto")  val urlProducto: String?,
    @SerializedName("disponible")   val disponible: Boolean = true
)
