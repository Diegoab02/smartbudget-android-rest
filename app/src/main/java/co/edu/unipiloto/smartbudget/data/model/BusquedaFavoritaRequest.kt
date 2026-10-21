package co.edu.unipiloto.smartbudget.data.model

import com.google.gson.annotations.SerializedName

/** Payload para POST /favoritos (guardar una búsqueda para seguimiento de precio). */
data class BusquedaFavoritaRequest(
    @SerializedName("usuarioId")        val usuarioId: Long,
    @SerializedName("consulta")         val consulta: String,
    @SerializedName("presupuestoCop")   val presupuestoCop: Double
)

data class BusquedaFavoritaResponse(
    @SerializedName("id")               val id: Long,
    @SerializedName("creada")           val creada: Boolean,
    @SerializedName("mensaje")          val mensaje: String
)
