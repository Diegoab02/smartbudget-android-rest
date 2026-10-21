package co.edu.unipiloto.smartbudget.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import co.edu.unipiloto.smartbudget.databinding.ActivityMainBinding
import co.edu.unipiloto.smartbudget.ui.adapter.ProductoAdapter
import co.edu.unipiloto.smartbudget.ui.viewmodel.ProductosViewModel

/**
 * Pantalla principal:
 *  - Busca productos (GET /productos?q=...).
 *  - Permite guardar la búsqueda como favorita (POST /favoritos).
 *  - Botón que lleva a OptimizarActivity (POST /optimizar).
 */
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private val vm: ProductosViewModel by viewModels()
    private val adapter = ProductoAdapter { producto ->
        Toast.makeText(this, "${producto.nombre} en ${producto.tienda}", Toast.LENGTH_SHORT).show()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.rvProductos.layoutManager = LinearLayoutManager(this)
        binding.rvProductos.adapter = adapter

        binding.btnBuscar.setOnClickListener {
            val q = binding.etConsulta.text.toString().trim()
            vm.buscar(q)
        }

        binding.btnFavorito.setOnClickListener {
            val q = binding.etConsulta.text.toString().trim()
            val presupuesto = binding.etPresupuesto.text.toString().toDoubleOrNull() ?: 0.0
            vm.guardarFavorito(usuarioId = 1L, consulta = q, presupuesto = presupuesto)
            Toast.makeText(this, "Favorito enviado", Toast.LENGTH_SHORT).show()
        }

        binding.btnOptimizar.setOnClickListener {
            val q = binding.etConsulta.text.toString().trim()
            val presupuesto = binding.etPresupuesto.text.toString()
            startActivity(
                Intent(this, OptimizarActivity::class.java)
                    .putExtra(OptimizarActivity.EXTRA_CONSULTA, q)
                    .putExtra(OptimizarActivity.EXTRA_PRESUPUESTO, presupuesto)
            )
        }

        vm.productos.observe(this) { adapter.submit(it) }
        vm.cargando.observe(this) {
            binding.progress.visibility = if (it) View.VISIBLE else View.GONE
        }
        vm.error.observe(this) { msg ->
            msg?.let { Toast.makeText(this, it, Toast.LENGTH_LONG).show() }
        }
    }
}
