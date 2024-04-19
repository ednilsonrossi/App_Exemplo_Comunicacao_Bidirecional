package br.edu.ifsp.exemplo_comunicacao_bidirecional

import android.content.Intent
import android.os.Bundle
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.exemplo_comunicacao_bidirecional.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    /*
     * O companion object é usado para definir membros estáticos associados à classe.
     * Esses membros podem ser acessados diretamente pelo nome da classe,
     * sem a necessidade de criar uma instância da classe.
     */
    companion object {
        const val KEY_MESSAGE = "message"
    }

    private lateinit var binding: ActivityMainBinding

    /*
     * O objeto resultLauncher fará o tratamento do resultado gerado pela
     * Activity por meio da chamada callback.
     */
    private lateinit var resultLauncher: ActivityResultLauncher<Intent>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.textviewMessage.text = "Não há mensagem."
        binding.buttonGetMessage.setOnClickListener { openMessageActivity() }

        setLauncher()
    }

    private fun openMessageActivity() {
        val mIntent = Intent(this, InputMessageActivity::class.java)
        /*
         * Como será iniciada a nova activity (InputMessageActivity) e ela
         * irá gerar um resultado, solicitamos que o launcher (resultLauncher)
         * lance a chamada da Intent criada, com isso o launcher fica
         * aguardando o resultado.
         */
        resultLauncher.launch(mIntent)
    }

    private fun setLauncher(){
        /*
         * Define-se o listener que irá monitorar os resultados da Activity.
         * Para o registro do listener (design pattern observer) é informado
         * o contrato (ActivityResultsContracts) e o método de callback
         * que trata o resultado, quando esse resultado chegar até
         * a activity (MainActivity).
         */
        resultLauncher = registerForActivityResult(
            ActivityResultContracts.StartActivityForResult(),
            ActivityResultCallback { result ->
                if (result.resultCode == RESULT_OK) {
                    val str = result.data?.getStringExtra(KEY_MESSAGE)
                    binding.textviewMessage.text = str
                }
            }
        )
    }
}