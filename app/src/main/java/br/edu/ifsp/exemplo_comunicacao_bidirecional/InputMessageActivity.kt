package br.edu.ifsp.exemplo_comunicacao_bidirecional

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import br.edu.ifsp.exemplo_comunicacao_bidirecional.databinding.ActivityInputMessageBinding

class InputMessageActivity : AppCompatActivity() {

    private lateinit var binding: ActivityInputMessageBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInputMessageBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSave.setOnClickListener { handleMessage() }
    }

    private fun handleMessage() {
        val str = binding.edittextMessage.text.toString()
        if (str.isEmpty()) {
            /*
             * Como a mensagem está em branco, está se considerando qye a ação da activity
             * foi cancelada. Neste caso, não se retorna o resultado, apenas se configura
             * que o resultado foi cancelado.
             */
            Toast.makeText(
                this,
                "Você não digitou uma mensagem",
                Toast.LENGTH_SHORT
            ).show()
            setResult(RESULT_CANCELED)
        } else {
            /*
             * Aqui temos que o usuário fez a entrada de uma mensagem, assim a activity
             * gera resultados. Inclui-se o resultado em um objeto do tipo Intent para
             * que seja recuperado na activity que lançou (chamou) a presente activity
             * para a entrada de dados.
             */
            val mIntent = Intent()
            mIntent.putExtra(MainActivity.KEY_MESSAGE, str)

            /*
             * Como resultados informa-se que houve sucesso (RESULT_OK) e se inclui
             * a Intent com os dados de retorno. Essa Intent entra na activity que
             * chamou no método de callback como um argumento, especificamente o
             * argumento "result".
             */
            setResult(RESULT_OK, mIntent)
        }
        finish()
    }
}