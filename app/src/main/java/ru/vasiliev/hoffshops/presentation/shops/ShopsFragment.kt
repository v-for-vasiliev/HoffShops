package ru.vasiliev.hoffshops.presentation.shops

import android.annotation.SuppressLint
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView
import androidx.annotation.LayoutRes
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import moxy.MvpAppCompatFragment
import moxy.ktx.moxyPresenter
import ru.vasiliev.hoffshops.App
import ru.vasiliev.hoffshops.R
import ru.vasiliev.hoffshops.di.ShopsScope
import ru.vasiliev.hoffshops.domain.entity.ShopEntity
import ru.vasiliev.hoffshops.presentation.ShopsScreen
import javax.inject.Inject
import javax.inject.Provider

@ShopsScope
class ShopsFragment : MvpAppCompatFragment(), ShopsView {

    private var content: RecyclerView? = null
    private var progress: ProgressBar? = null
    private var errorText: TextView? = null
    private val adapter = ShopsAdapter()

    @Inject
    lateinit var shopsProvider: Provider<ShopsPresenter>

    private val presenter: ShopsPresenter by moxyPresenter { shopsProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        App.appComponent.getShopsComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View = inflater.inflate(R.layout.fragment_shops, container, false)

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        content = view.findViewById(R.id.content)
        content?.let { recycler ->
            val layoutManager = LinearLayoutManager(requireContext())
            val dividerItemDecoration = DividerItemDecoration(
                recycler.context,
                layoutManager.orientation
            )
            recycler.layoutManager = layoutManager
            recycler.addItemDecoration(dividerItemDecoration)
            recycler.adapter = adapter
        }
        progress = view.findViewById(R.id.progress)
        errorText = view.findViewById(R.id.error)

        arguments?.let { it ->
            it.getString("city")?.let { city ->
                presenter.loadShopsByCity(city)
            }
        }
    }

    override fun showError(error: Throwable) {
        errorText?.text = error.message
        toggleLayers(contentVisible = false, loaderVisible = false, errorVisible = true)
    }

    private fun toggleLayers(
        contentVisible: Boolean,
        loaderVisible: Boolean,
        errorVisible: Boolean
    ) {
        content?.visibility = if (contentVisible) View.VISIBLE else View.INVISIBLE
        progress?.visibility = if (loaderVisible) View.VISIBLE else View.GONE
        errorText?.visibility = if (errorVisible) View.VISIBLE else View.GONE
    }

    override fun showLoader() {
        toggleLayers(contentVisible = false, loaderVisible = true, errorVisible = false)
    }

    override fun showShops(shops: List<ShopEntity>) {
        if (shops.isEmpty()) {
            errorText?.text = "Нет доступных магазинов"
            toggleLayers(contentVisible = false, loaderVisible = false, errorVisible = true)
        } else {
            adapter.setData(shops)
            toggleLayers(contentVisible = true, loaderVisible = false, errorVisible = false)
        }
    }
}

class ShopHolder(view: View) : RecyclerView.ViewHolder(view) {
    val shopName: TextView = view.findViewById(R.id.shopName)
    val phone: TextView = view.findViewById(R.id.phone)
    val subway: TextView = view.findViewById(R.id.subway)
}

class ShopsAdapter : RecyclerView.Adapter<ShopHolder>() {

    private val items: MutableList<ShopEntity> = mutableListOf()

    fun setData(data: List<ShopEntity>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    private fun inflate(parent: ViewGroup, @LayoutRes layoutId: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ShopHolder {
        return ShopHolder(inflate(parent, R.layout.item_shop))
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: ShopHolder, position: Int) {
        holder.shopName.text = items[position].name
        holder.phone.text = "Телефон: ${items[position].name}"
        holder.subway.text =
            "Метро: ${items[position].subway.joinToString(separator = ", ") { it.name }}"
    }

    override fun getItemCount() = items.size
}