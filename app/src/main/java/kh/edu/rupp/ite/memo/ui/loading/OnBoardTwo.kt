package kh.edu.rupp.ite.memo.ui.loading

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.FragmentOnBoardTwoBinding

@AndroidEntryPoint
class OnBoardTwo : Fragment() {

    private var _binding: FragmentOnBoardTwoBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnBoardTwoBinding.inflate(inflater, container, false)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nextScreen2.setOnClickListener {

            it.findNavController().navigate(R.id.action_onBoardTwo_to_onBoardThree)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}