package com.yes.or.no.app

import android.app.AlertDialog
import android.os.Bundle
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.yes.or.no.app.databinding.FragmentFlipBinding

class FlipFragment: Fragment() {
    private var _binding: FragmentFlipBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: RecViewModel


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreate(savedInstanceState)
        viewModel = ViewModelProvider(this)[RecViewModel::class.java]
        _binding = FragmentFlipBinding.inflate(layoutInflater)
        val root: View = binding.root

        binding.buttonStart.setOnClickListener {
            val v = layoutInflater.inflate(R.layout.add, null)
            val textEdit = v.findViewById<EditText>(R.id.add_edit)
            AlertDialog.Builder(context)
                .setTitle("write down what you are guessing at")
                .setView(v)
                .setPositiveButton("Guess") { d, _ ->
                    viewModel.addRecord(textEdit.text.toString(), makeFlip())
                    d.dismiss()
                }
                .setNegativeButton("Go back") { d, _ ->
                    d.dismiss()
                }
                .show()

        }

        return root
    }

    private fun makeAnim() {
        binding.myText.text = ""
        binding.imageView.alpha = 0f
        binding.gifView.alpha = 1f
        binding.gifView.setImageResource(R.drawable.coin_anim)
    }

    private fun makeFlip(): String {
        makeAnim()
        val random = (0..10).random()
        if (random % 2 == 0) {
            changeView(1)    // Success anim
        } else {
            changeView(2)   // Fail anim
        }

        return if (random % 2 == 0) "Yes" else "No"
    }


    private fun changeView(result: Int) {
        object : CountDownTimer(2000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                binding.buttonStart.isClickable = false
            }

            override fun onFinish() {
                if (result == 0)
                    binding.imageView.setImageResource(R.drawable.success)
                else
                    binding.imageView.setImageResource(R.drawable.failure)
                binding.gifView.alpha = 0f
                binding.buttonStart.isClickable = true
                binding.imageView.alpha = 1f
            }
        }.start()
    }


    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}