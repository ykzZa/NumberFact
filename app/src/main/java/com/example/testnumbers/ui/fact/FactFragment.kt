package com.example.testnumbers.ui.fact

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.testnumbers.R
import com.example.testnumbers.databinding.FragmentFactBinding


class FactFragment : Fragment(R.layout.fragment_fact) {

    private lateinit var binding: FragmentFactBinding
    private val args: FactFragmentArgs by navArgs()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentFactBinding.bind(view)

        binding.apply {
            textViewNumber.text = args.search.number
            textViewFact.text = args.search.text
            imgButtonBack.setOnClickListener {
                activity?.onBackPressed()
            }
        }
    }
}