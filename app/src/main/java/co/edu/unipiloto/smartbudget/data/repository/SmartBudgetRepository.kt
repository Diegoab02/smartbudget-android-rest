package co.edu.unipiloto.smartbudget.data.repository

import co.edu.unipiloto.smartbudget.data.model.*
import co.edu.unipiloto.smartbudget.data.remote.ApiService
import co.edu.unipiloto.smartbudget.data.remote.RetrofitClient

/**
 * Capa de dominio entre la ApiService (Retrofit) y los ViewModels.
 * Envuelve cada respuesta en [Result] para manejar errores sin excepciones no controladas.
 */
class SmartBudgetRepository(
    private val api: ApiService = RetrofitClient.api
) {

    suspend fun listarTiendas(): Result<List<Tienda>> = safeCall {
        api.listarTiendas()
    }

    suspend fun buscarProductos(
        consulta: String,
        tienda: String? = null,
        maxPrecioCop: Double? = null
    ): Result<List<Producto>> = safeCall {
        api.buscarProductos(consulta, tienda, maxPrecioCop)
    }

    suspend fun detalleProducto(id: Long): Result<Producto> = safeCall {
        api.detalleProducto(id)
    }

    suspend fun optimizarCompra(
        req: OptimizacionRequest
    ): Result<OptimizacionResponse> = safeCall {
        api.optimizarCompra(req)
    }

    suspend fun guardarFavorito(
        req: BusquedaFavoritaRequest
    ): Result<BusquedaFavoritaResponse> = safeCall {
        api.guardarFavorito(req)
    }

    // ---------- helper ----------
    private inline fun <T> safeCall(block: () -> retrofit2.Response<T>): Result<T> = try {
        val response = block()
        if (response.isSuccessful) {
            response.body()
                ?.let { Result.success(it) }
                ?: Result.failure(IllegalStateException("Respuesta vacía"))
        } else {
            Result.failure(RuntimeException("HTTP ${response.code()} ${response.message()}"))
        }
    } catch (e: Exception) {
        Result.failure(e)
    }
}
