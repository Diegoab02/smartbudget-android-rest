package co.edu.unipiloto.smartbudget.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unipiloto.smartbudget.databinding.ActivityOptimizarBinding
import co.edu.unipiloto.smartbudget.ui.adapter.ProductoAdapter
import co.edu.unipiloto.smartbudget.ui.viewmodel.OptimizarViewModel
import java.text.NumberFormat
import java.util.Locale

/**
 * POST /optimizar: envía {consulta, presupuestoCop} y pinta el combo óptimo.
 */
class OptimizarActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_CONSULTA = "extra_consulta"
        const val EXTRA_PRESUPUESTO = "extra_presupuesto"
    }

    private lateinit var binding: ActivityOptimizarBinding
    private val vm: OptimizarViewModel by viewModels()
    private val adapter = ProductoAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityOptimizarBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvResultado.layoutManager = LinearLayoutManager(this)
        binding.rvResultado.adapter = adapter

        val consulta = intent.getStringExtra(EXTRA_CONSULTA).orEmpty()
        val presupuesto = intent.getStringExtra(EXTRA_PRESUPUESTO)?.toDoubleOrNull() ?: 0.0
        binding.tvConsulta.text = "Consulta: $consulta"

        if (consulta.isNotBlank() && presupuesto > 0) {
            vm.optimizar(consulta, presupuesto)
        } else {
            Toast.makeText(this, "Faltan consulta o presupuesto", Toast.LENGTH_LONG).show()
        }

        vm.resultado.observe(this) { r ->
            if (r != null) {
                val fmt = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
                binding.tvResumen.text =
                    "Total: ${fmt.format(r.totalCop)}  |  Ahorro: ${fmt.format(r.ahorroCop)}"
                adapter.submit(r.productos)
            }
        }
        vm.cargando.observe(this) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.error.observe(this) { it?.let { msg ->
            Toast.makeText(this, msg, Toast.LENGTH_LONG).show()
        }}
    }
}
