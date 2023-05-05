import com.example.onlineshopapp.models.CartItem
import com.example.onlineshopapp.models.Product
import java.util.Date

private val Any.id: Any
    get() {
        return 1
    }

object Cart {
    private val cartItems = mutableListOf<CartItem>()

    fun addProduct(id: Product, userId: Int, date: Date, product_id: String, quantity: Int) {
        // Generate a unique id for the new item
        val newItemId = cartItems.size + 1

        // Create a new cart item and add it to the list
        val date = System.currentTimeMillis()
        val cartItem = CartItem(id,userId, date, product_id, quantity)
        cartItems.add(cartItem)
    }

    fun getCartItems(): List<CartItem> {
        return cartItems
    }

    fun addProduct(id: Product, userId: Int) {


    }
}


    private fun CartItem(id: Product, userId: Int) {

    }

    // Other functions to get and manipulate the cart items go here

private fun <E> MutableList<E>.add(element: Unit) {

}
