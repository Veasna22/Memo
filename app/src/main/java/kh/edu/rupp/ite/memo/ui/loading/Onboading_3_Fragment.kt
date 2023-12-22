package kh.edu.rupp.ite.memo.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.R

import kh.edu.rupp.ite.memo.databinding.FragmentOnboading3Binding


@AndroidEntryPoint
class Onboading_3_Fragment : Fragment() {

    private var _binding: FragmentOnboading3Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboading3Binding.inflate(inflater, container, false)
        return binding.root
    }
    //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nextScreen3.setOnClickListener {

            it.findNavController().navigate(R.id.action_onboading_3_Fragment_to_homeFragment)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
