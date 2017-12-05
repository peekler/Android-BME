package hu.autsoft.shoppingkotlin

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.content_main.*

class MainActivity : AppCompatActivity() {

    private val adapter = ShoppingAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        rvShoppingItems.adapter = adapter

        btnAddItem.setOnClickListener {
            val name = etName.text.toString()
            val price = etPrice.text.toString()

            if (name.isNotBlank() && price.isNotBlank()) {
                createItem(name, price)
            }
        }
    }

    private fun createItem(name: String, price: String) {
        val newItem = ShoppingItem(name, price.toInt())

        adapter.addItem(newItem)

        etName.setText("")
        etPrice.setText("")

        rvShoppingItems.smoothScrollToPosition(adapter.itemCount)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_delete_all -> {
                adapter.removeAllItems()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

}
