package co.edu.unipiloto.smartbudget.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import co.edu.unipiloto.smartbudget.R
import co.edu.unipiloto.smartbudget.data.model.Producto
import java.text.NumberFormat
import java.util.Locale

class ProductoAdapter(
    private var items: List<Producto> = emptyList(),
    private val onClick: (Producto) -> Unit = {}
) : RecyclerView.Adapter<ProductoAdapter.VH>() {

    fun submit(nuevos: List<Producto>) {
        items = nuevos
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH {
        val v = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_producto, parent, false)
        return VH(v)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: VH, position: Int) =
        holder.bind(items[position], onClick)

    class VH(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvNombre: TextView = itemView.findViewById(R.id.tvNombre)
        private val tvTienda: TextView = itemView.findViewById(R.id.tvTienda)
        private val tvPrecio: TextView = itemView.findViewById(R.id.tvPrecio)

        fun bind(p: Producto, onClick: (Producto) -> Unit) {
            tvNombre.text = p.nombre
            tvTienda.text = p.tienda
            val fmt = NumberFormat.getCurrencyInstance(Locale("es", "CO"))
            tvPrecio.text = fmt.format(p.precio)
            itemView.setOnClickListener { onClick(p) }
        }
    }
}
