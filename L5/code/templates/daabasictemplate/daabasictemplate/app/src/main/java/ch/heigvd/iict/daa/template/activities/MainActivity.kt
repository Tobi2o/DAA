package ch.heigvd.iict.daa.template.activities

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import ch.heigvd.iict.daa.template.R
import ch.heigvd.iict.daa.template.adapter.RecyclerAdapter
import ch.heigvd.iict.daa.template.utils.Cache
import java.net.URL

class MainActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var swipeRefreshLayout: SwipeRefreshLayout

    // Liste des URLs d'images dynamiques
    private val imageUrls = List(10) { URL("https://daa.iict.ch/images/${it + 1}.jpg") }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Initialisation du cache
        Cache.initialize(cacheDir)

        // Initialisation de la RecyclerView
        recyclerView = findViewById(R.id.recycler_view)
        recyclerView.layoutManager = GridLayoutManager(this, 3)
        recyclerView.adapter = RecyclerAdapter(imageUrls)

        // Initialisation du SwipeRefreshLayout
        swipeRefreshLayout = findViewById(R.id.swipe_refresh_layout)
        swipeRefreshLayout.setOnRefreshListener {
            refreshImages()
        }
    }

    /**
     * Initialise ou rafraîchit la liste des images affichées.
     * Actuellement, ce refresh ne fait que simuler une action en désactivant l'animation.
     */
    private fun refreshImages() {
        // Placeholder : à compléter pour réellement rafraîchir les images si besoin
        Log.d("MainActivity", "Refresh triggered (no actual logic yet)")
        swipeRefreshLayout.isRefreshing = false
    }

    /**
     * Initialise le menu d'options en haut de l'écran.
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Gère les actions des items du menu.
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.menu_actions_clear_cache -> {
                clearCache()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    /**
     * Placeholder pour la logique de nettoyage du cache.
     */
    private fun clearCache() {
        Cache.clear()
        Log.d("MainActivity", "Cache cleared")
    }

    /**
     * Vérifie si une connexion réseau est disponible.
     * @return `true` si le réseau est disponible, `false` sinon.
     */
    private fun isNetworkAvailable(): Boolean {
        val connectivityManager =
            getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetworkInfo
        return activeNetwork != null && activeNetwork.isConnected
    }
}
