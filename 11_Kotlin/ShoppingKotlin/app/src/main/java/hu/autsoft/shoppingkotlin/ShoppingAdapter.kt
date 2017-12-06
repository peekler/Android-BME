package hu.autsoft.shoppingkotlin

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import kotlinx.android.synthetic.main.row_shopping_item.view.*

class ShoppingAdapter : RecyclerView.Adapter<ShoppingAdapter.ViewHolder>() {

    private val items = mutableListOf(
            ShoppingItem("milk", 200),
            ShoppingItem("tea", 100),
            ShoppingItem("cookies", 500)
    )

    override fun getItemCount() = items.size

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater
                .from(parent.context)
                .inflate(R.layout.row_shopping_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val item = items[position]
        holder.tvName.text = item.name
        holder.tvPrice.text = item.price.toString()
    }

    fun addItem(item: ShoppingItem) {
        items += item
        notifyItemInserted(items.lastIndex)
    }

    fun removeAllItems() {
        val oldSize = items.size
        items.clear()
        notifyItemRangeRemoved(0, oldSize)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val tvName = itemView.tvName
        val tvPrice = itemView.tvPrice
    }

}
