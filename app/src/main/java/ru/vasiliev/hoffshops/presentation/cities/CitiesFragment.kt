package ru.vasiliev.hoffshops.presentation.cities

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
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
import ru.vasiliev.hoffshops.di.CitiesScope
import ru.vasiliev.hoffshops.presentation.ShopsScreen
import javax.inject.Inject
import javax.inject.Provider


@CitiesScope
class CitiesFragment : MvpAppCompatFragment(), CitiesView {

    private var content: RecyclerView? = null
    private var refresh: Button? = null
    private var progress: ProgressBar? = null
    private var errorText: TextView? = null
    private val adapter = CitiesAdapter {
        openCityShop(it)
    }

    private lateinit var shopsScreen: ShopsScreen

    @Inject
    lateinit var citiesProvider: Provider<CitiesPresenter>

    private val presenter by moxyPresenter { citiesProvider.get() }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        (context as? ShopsScreen)?.let {
            shopsScreen = it
        }
        App.appComponent.getCitiesComponent().inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(R.layout.fragment_cities, container, false)
    }

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
        refresh = view.findViewById(R.id.contentRefresh)
        refresh?.setOnClickListener {
            presenter.loadCities(true)
        }
        progress = view.findViewById(R.id.progress)
        errorText = view.findViewById(R.id.error)
    }

    override fun showLoader() {
        toggleLayers(contentVisible = false, loaderVisible = true, errorVisible = false)
    }

    override fun showCities(cities: List<String>) {
        if (cities.isEmpty()) {
            errorText?.text = "Нет доступных магазинов"
            toggleLayers(contentVisible = false, loaderVisible = false, errorVisible = true)
        } else {
            adapter.setData(cities)
            toggleLayers(contentVisible = true, loaderVisible = false, errorVisible = false)
        }
    }

    override fun openCityShop(city: String) {
        shopsScreen.openShops(city)
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
        refresh?.visibility = if (contentVisible) View.VISIBLE else View.INVISIBLE
        progress?.visibility = if (loaderVisible) View.VISIBLE else View.GONE
        errorText?.visibility = if (errorVisible) View.VISIBLE else View.GONE
    }
}

class CityHolder(view: View, clickListener: (String) -> Unit) : RecyclerView.ViewHolder(view) {
    val city: TextView = view.findViewById(R.id.city)

    init {
        city.setOnClickListener {
            clickListener.invoke(city.text.toString())
        }
    }
}

class CitiesAdapter(
    private val clickListener: (String) -> Unit
) : RecyclerView.Adapter<CityHolder>() {

    private val items: MutableList<String> = mutableListOf()

    fun setData(data: List<String>) {
        items.clear()
        items.addAll(data)
        notifyDataSetChanged()
    }

    private fun inflate(parent: ViewGroup, @LayoutRes layoutId: Int): View =
        LayoutInflater.from(parent.context).inflate(layoutId, parent, false)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityHolder {
        return CityHolder(inflate(parent, R.layout.item_city), clickListener)
    }

    override fun onBindViewHolder(holder: CityHolder, position: Int) {
        holder.city.text = items[position]
    }

    override fun getItemCount() = items.size
}