package com.furkanmulayim.cardholder.ui.list_future

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.furkanmulayim.cardholder.R
import com.furkanmulayim.cardholder.databinding.FragmentCardListBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class CardListFragment : Fragment() {
    private lateinit var binding: FragmentCardListBinding
    private lateinit var viewModel: CardListViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_card_list, container, false)
        viewModel = ViewModelProvider(this)[CardListViewModel::class.java]
        binding.viewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getAllCards()
    }

    private fun getAllCards() {
        binding.recyc.layoutManager = LinearLayoutManager(requireContext())
        viewModel.cardList.observe(viewLifecycleOwner) {
            val adapter = CardListAdapter(requireContext(), it)
            binding.recyc.adapter = adapter

            if (adapter.itemCount > 0) {
                binding.textListControl.visibility = View.GONE
            } else {
                binding.textListControl.visibility = View.VISIBLE
            }
        }
    }


    override fun onResume() {
        super.onResume()
        viewModel.getCardList()
    }
}