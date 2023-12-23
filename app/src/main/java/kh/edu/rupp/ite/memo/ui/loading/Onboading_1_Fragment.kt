package kh.edu.rupp.ite.memo.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.R
import kh.edu.rupp.ite.memo.databinding.FragmentOnboading1Binding

@AndroidEntryPoint
class Onboading_1_Fragment : Fragment() {

    private var _binding: FragmentOnboading1Binding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboading1Binding.inflate(inflater, container, false)
        return binding.root
    }
    //
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        binding.nextScreen.setOnClickListener {

            it.findNavController().navigate(R.id.action_onboading_1_Fragment_to_onboading_2_Fragment2)
        }


    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
