package co.edu.unipiloto.smartbudget.data.model

import com.google.gson.annotations.SerializedName

/** Tienda colombiana soportada por el agregador (Top 10, excluye Mercado Libre). */
data class Tienda(
    @SerializedName("id")       val id: Long,
    @SerializedName("nombre")   val nombre: String,
    @SerializedName("urlBase")  val urlBase: String,
    @SerializedName("activa")   val activa: Boolean
)
