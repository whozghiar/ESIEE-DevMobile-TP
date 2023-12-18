package fr.unilasalle.androidtp.Activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import fr.unilasalle.androidtp.adapters.OrderAdapter
import fr.unilasalle.androidtp.database.AppDatabase
import fr.unilasalle.androidtp.database.daos.CartDao
import fr.unilasalle.androidtp.database.daos.CartItemDao
import fr.unilasalle.androidtp.database.daos.OrderDao
import fr.unilasalle.androidtp.database.daos.ProductDao
import fr.unilasalle.androidtp.databinding.ActivityBuyHistoryBinding
import fr.unilasalle.androidtp.databinding.ActivityDetailProductBinding
import fr.unilasalle.androidtp.fragments.BannerFragment
import fr.unilasalle.androidtp.model.Cart
import fr.unilasalle.androidtp.repositories.OrderRepository
import fr.unilasalle.androidtp.repositories.ProductRepository
import fr.unilasalle.androidtp.repositories.ShoppingCartRepository
import fr.unilasalle.androidtp.viewmodels.OrderListViewModel
import fr.unilasalle.androidtp.viewmodels.ProductDetailViewModel
import fr.unilasalle.androidtp.viewmodelsfactories.CategoryViewModelFactory
import fr.unilasalle.androidtp.viewmodelsfactories.OrderListViewModelFactory
import fr.unilasalle.androidtp.viewmodelsfactories.ProductDetailViewModelFactory

class BuyHistoryActivity: AppCompatActivity() {
    private lateinit var binding: ActivityBuyHistoryBinding

    private lateinit var orderDao: OrderDao
    private lateinit var cartDao: CartDao
    private lateinit var cartItemDao: CartItemDao

    private lateinit var shoppingCartRepository: ShoppingCartRepository
    private lateinit var orderRepository: OrderRepository


    private lateinit var orderListViewModelFactory: OrderListViewModelFactory
    private lateinit var orderListViewModel: OrderListViewModel

    private lateinit var orderRecyclerView: RecyclerView

    private lateinit var orderAdapter: OrderAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityBuyHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Bannière en haut de l'écran
        if (savedInstanceState == null) {
            supportFragmentManager.beginTransaction()
                .replace(binding.bannerContainer.id, BannerFragment())
                .commit()
        }

        cartDao = AppDatabase.getDatabase(this).getCartDao()
        cartItemDao = AppDatabase.getDatabase(this).getCartItemDao()
        orderDao = AppDatabase.getDatabase(this).getOrderDao()

        shoppingCartRepository = ShoppingCartRepository(cartItemDao, cartDao)
        orderRepository = OrderRepository(orderDao,cartDao)

        orderListViewModelFactory = OrderListViewModelFactory(orderRepository,shoppingCartRepository)
        orderListViewModel = ViewModelProvider(this, orderListViewModelFactory).get(
            OrderListViewModel::class.java)

        orderAdapter = OrderAdapter()
        orderRecyclerView = binding.idCarts

        orderRecyclerView.adapter = orderAdapter
        orderRecyclerView.layoutManager = LinearLayoutManager(this@BuyHistoryActivity)

        orderListViewModel.ordersWithCart.observe(this) {
            orderAdapter.orders = it
        }



    }

}