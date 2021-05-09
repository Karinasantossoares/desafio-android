package com.picpay.desafio.android.ui.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.isVisible
import androidx.lifecycle.Observer
import com.picpay.desafio.android.databinding.FragmentUserBinding
import com.picpay.desafio.android.ui.adapter.UserListAdapter
import com.picpay.desafio.android.viewModel.UserViewModel
import org.koin.android.viewmodel.ext.android.viewModel


class UserFragment : Fragment() {
    private lateinit var binding: FragmentUserBinding
    private val viewModel: UserViewModel by viewModel()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentUserBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.btnRetry.setOnClickListener {
            viewModel.start()
        }

        binding.swipeRefresh.setOnRefreshListener {
            viewModel.start()
        }

        viewModel.loadLiveData.observe(viewLifecycleOwner, Observer {
            binding.swipeRefresh.isRefreshing = it
        })

        viewModel.successListUserLiveData.observe(viewLifecycleOwner, Observer {
            val adapter = UserListAdapter()
            adapter.users = it
            binding.recyclerView.adapter = adapter
        })

        viewModel.errorLiveData.observe(viewLifecycleOwner, Observer {
            binding.btnRetry.isVisible = it
        })

        viewModel.cachedLoadListUserLiveData.observe(viewLifecycleOwner, Observer {
            if(it){
                binding.labelLastRefreshTime.animate().setDuration(400L).alpha(1F)
            }else{
                binding.labelLastRefreshTime.animate().setDuration(400L).alpha(0F)
            }
        })


    }

}