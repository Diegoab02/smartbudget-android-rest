package co.edu.unipiloto.smartbudget.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import co.edu.unipiloto.smartbudget.data.model.OptimizacionRequest
import co.edu.unipiloto.smartbudget.data.model.OptimizacionResponse
import co.edu.unipiloto.smartbudget.data.repository.SmartBudgetRepository
import kotlinx.coroutines.launch

class OptimizarViewModel(
    private val repo: SmartBudgetRepository = SmartBudgetRepository()
) : ViewModel() {

    private val _resultado = MutableLiveData<OptimizacionResponse?>(null)
    val resultado: LiveData<OptimizacionResponse?> = _resultado

    private val _cargando = MutableLiveData(false)
    val cargando: LiveData<Boolean> = _cargando

    private val _error = MutableLiveData<String?>(null)
    val error: LiveData<String?> = _error

    /** POST /optimizar */
    fun optimizar(consulta: String, presupuestoCop: Double) {
        _cargando.value = true
        _error.value = null
        viewModelScope.launch {
            val res = repo.optimizarCompra(
                OptimizacionRequest(consulta = consulta, presupuestoCop = presupuestoCop)
            )
            _cargando.value = false
            res.onSuccess { _resultado.value = it }
               .onFailure { _error.value = it.message ?: "Error al optimizar" }
        }
    }
}
