package com.example.graduationproject.ui.fragments

import android.content.Context
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.view.MenuProvider
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.graduationproject.R
import com.example.graduationproject.databinding.FragmentMenuBinding
import com.example.graduationproject.ui.adapter.FoodsAdapter
import com.example.graduationproject.ui.viewmodel.MenuViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MenuFragment : Fragment() , SearchView.OnQueryTextListener {
    private lateinit var binding: FragmentMenuBinding
    private lateinit var viewModel: MenuViewModel
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_menu, container, false)
        binding.menuFragment = this
        binding.toolbarMenuTitle = "Menu"

        //Network
        val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        if (networkInfo == null || !networkInfo.isConnectedOrConnecting) {
            Toast.makeText(context, "You do not have an internet connection.", Toast.LENGTH_SHORT).show()
        } else {
            viewModel.loadFoods()
        }

        (activity as AppCompatActivity).setSupportActionBar(binding.toolbarMenu)

        viewModel.foodList.observe(viewLifecycleOwner){
            val adapter = FoodsAdapter(requireContext(),it,viewModel)
            binding.adapter = adapter
        }

        binding.rvMenu.layoutManager = LinearLayoutManager(requireContext())

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.search, menu)

                val item = menu.findItem(R.id.action_search)
                val searchView = item.actionView as SearchView
                searchView.setOnQueryTextListener(this@MenuFragment)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return false
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        return binding.root
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val tempViewModel:MenuViewModel by viewModels()
        viewModel = tempViewModel
    }

    override fun onQueryTextSubmit(query: String): Boolean {
        viewModel.search(query)
        return true
    }

    override fun onQueryTextChange(newText: String): Boolean {
        viewModel.search(newText)
        return true
    }

    override fun onResume() {
        super.onResume()
        viewModel.loadFoods()
    }
}


