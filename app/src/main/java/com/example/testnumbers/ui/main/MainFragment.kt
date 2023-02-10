package com.example.testnumbers.ui.main

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.testnumbers.R
import com.example.testnumbers.data.api.ApiService
import com.example.testnumbers.data.search.Search
import com.example.testnumbers.databinding.FragmentMainBinding
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


const val BASE_URL = "http://numbersapi.com/"

class MainFragment : Fragment(R.layout.fragment_main), SearchAdapter.OnItemClickListener {

    private lateinit var binding: FragmentMainBinding
    private lateinit var api: ApiService
    private val mMainViewModel: MainViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        api = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiService::class.java)
    }

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentMainBinding.bind(view)
        val searchAdapter = SearchAdapter(this)

        binding.apply {
            buttonGetFact.setOnClickListener {
                if(editTextNumberInput.text.toString() == "") {
                    Toast.makeText(context, "Input your number", Toast.LENGTH_SHORT).show()
                } else {
                    if(isOnline(requireContext())) {
                        mMainViewModel.onGetButtonClicked(editTextNumberInput.text.toString())
                    } else {
                        Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show()
                    }
                }
                editTextNumberInput.text?.clear()
            }
            buttonGetRandomFact.setOnClickListener {
                if(isOnline(requireContext())) {
                    mMainViewModel.onGetRandomButtonClicked()
                } else {
                    Toast.makeText(context, "No Internet connection", Toast.LENGTH_SHORT).show()
                }
            }
            recyclerViewSearchHistory.apply {
                adapter = searchAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        mMainViewModel.getSearches.observe(viewLifecycleOwner) {
            searchAdapter.submitList(it.reversed())
            GlobalScope.launch(Dispatchers.Main) {
                delay(200)
                binding.recyclerViewSearchHistory.layoutManager?.scrollToPosition(0)
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun isOnline(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (connectivityManager != null) {
            val capabilities =
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)
            if (capabilities != null) {
                if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) {
                    return true
                } else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) {
                    return true
                }
            }
        }
        return false
    }

    override fun onItemClick(search: Search) {
        val action = MainFragmentDirections.actionMainFragmentToFactFragment(search)
        findNavController().navigate(action)
    }
}