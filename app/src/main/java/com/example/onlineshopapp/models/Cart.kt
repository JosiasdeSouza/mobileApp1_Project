import com.example.onlineshopapp.models.CartItem
import com.example.onlineshopapp.models.User
import java.io.Serializable

data class Cart(val user: User, val items: MutableList<CartItem>) : Serializable
