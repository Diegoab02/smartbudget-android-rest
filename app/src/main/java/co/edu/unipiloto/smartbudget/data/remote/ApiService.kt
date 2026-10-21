package co.edu.unipiloto.smartbudget.data.remote

import co.edu.unipiloto.smartbudget.data.model.*
import retrofit2.Response
import retrofit2.http.*

/**
 * Contrato REST que consume el frontend Android de SmartBudget.
 *
 * Endpoints base esperados en el backend (ver RetrofitClient.BASE_URL):
 *   GET  /tiendas                    -> listado de tiendas colombianas soportadas
 *   GET  /productos?q={q}            -> búsqueda de un producto en todas las tiendas
 *   GET  /productos/{id}             -> detalle de producto
 *   POST /optimizar                  -> calcula mejor combinación bajo presupuesto
 *   POST /favoritos                  -> guarda una búsqueda favorita del usuario
 */
interface ApiService {

    // ---------- GET ----------
    @GET("tiendas")
    suspend fun listarTiendas(): Response<List<Tienda>>

    @GET("productos")
    suspend fun buscarProductos(
        @Query("q") consulta: String,
        @Query("tienda") tienda: String? = null,
        @Query("maxPrecio") maxPrecioCop: Double? = null
    ): Response<List<Producto>>

    @GET("productos/{id}")
    suspend fun detalleProducto(
        @Path("id") id: Long
    ): Response<Producto>

    // ---------- POST ----------
    @POST("optimizar")
    suspend fun optimizarCompra(
        @Body body: OptimizacionRequest
    ): Response<OptimizacionResponse>

    @POST("favoritos")
    suspend fun guardarFavorito(
        @Body body: BusquedaFavoritaRequest
    ): Response<BusquedaFavoritaResponse>
}
