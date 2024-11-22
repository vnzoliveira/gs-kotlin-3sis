package com.fiap.viniciusoliveira_93136
import com.fiap.viniciusoliveira_93136.AlunosActivity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.SearchView
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.fiap.viniciusoliveira_93136.adapter.DicaAdapter
import com.fiap.viniciusoliveira_93136.db.Helper
import com.fiap.viniciusoliveira_93136.model.Dica
import com.fiap.viniciusoliveira_93136.ui.theme.Viniciusoliveira_93136Theme

class MainActivity : AppCompatActivity() {

    private lateinit var dicaAdapter: DicaAdapter
    private lateinit var listaDicas: MutableList<Dica>
    private lateinit var recyclerView: RecyclerView
    private lateinit var searchView: SearchView
    private lateinit var dbHelper: Helper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recycler_view)
        searchView = findViewById(R.id.search_view)

        dbHelper = Helper(this)
        listaDicas = dbHelper.getAllDicas()

        dicaAdapter = DicaAdapter(listaDicas)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = dicaAdapter

        configurarSearchView()

        val btnCreditos: Button = findViewById(R.id.btn_alunos)
        btnCreditos.setOnClickListener {
            val intent = Intent(this, AlunosActivity::class.java)
            startActivity(intent)
        }
    }

    private fun configurarSearchView() {
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                val listaFiltrada = listaDicas.filter {
                    it.titulo.contains(newText!!, ignoreCase = true) ||
                            it.descricao.contains(newText, ignoreCase = true)
                }
                dicaAdapter.filterList(listaFiltrada)
                return true
            }
        })
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
            text = "Hello $name!",
            modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Viniciusoliveira_93136Theme {
        Greeting("Android")
    }
}