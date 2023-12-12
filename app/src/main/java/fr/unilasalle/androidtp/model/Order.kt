package fr.unilasalle.androidtp.model

data class Order(
    val orderId: Int,
    val productList: List<Product>,
    val totalPrice: Double,
    val orderDate: String,
    val customerName: String,
    val shippingAddress: String,
    val status: OrderStatus
)

enum class OrderStatus {
    PENDING,
    SHIPPED,
    DELIVERED,
    CANCELLED
}