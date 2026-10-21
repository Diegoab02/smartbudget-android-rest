package co.edu.unipiloto.smartbudget.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unipiloto.smartbudget.data.model.*
import co.edu.unipiloto.smartbudget.data.repository.SmartBudgetRepository
import kotlinx.coroutines.launch

class ProductosViewModel(
    private val repo: SmartBudgetRepository = SmartBudgetRepository()
) : ViewModel() {

    private val _productos = MutableLiveData<List<Producto>>(emptyList())
    val productos: LiveData<List<Producto>> = _productos

    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    /** GET /productos?q=... */
    fun buscar(consulta: String, maxPrecio: Double? = null) {
        if (consulta.isBlank()) {
            _error.value = "Escribe qué producto quieres buscar"
            return
        }
        _cargando.value = true
        _error.value = null
        viewModelScope.launch {
            val res = repo.buscarProductos(consulta, maxPrecioCop = maxPrecio)
            _cargando.value = false
            res.onSuccess { _productos.value = it }
               .onFailure { _error.value = it.message ?: "Error de red" }
        }
    }

    /** POST /favoritos */
    fun guardarFavorito(usuarioId: Long, consulta: String, presupuesto: Double) {
        viewModelScope.launch {
            val res = repo.guardarFavorito(
                BusquedaFavoritaRequest(usuarioId, consulta, presupuesto)
            )
            res.onFailure { _error.value = it.message ?: "No se pudo guardar favorito" }
        }
    }
}
