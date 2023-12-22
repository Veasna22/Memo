package kh.edu.rupp.ite.memo.ui.loading

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import dagger.hilt.android.AndroidEntryPoint
import kh.edu.rupp.ite.memo.databinding.FragmentOnboardingOneBinding

@AndroidEntryPoint
class LoadingFragment : Fragment() {

    private var _binding: FragmentOnboardingOneBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOnboardingOneBinding.inflate(inflater, container, false)
        return binding.root
    }
//
//    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//
//        // Set onClickListener for the "Next" button
//        binding.btn.setOnClickListener {
//            // Navigate to the destination fragment when the button is clicked
//            // Replace R.id.destination_fragment with the ID of your desired destination fragment
//            it.findNavController().navigate(R.id.action_registerFragment_to_homeFragment)
//        }
//
//        // Example: If you want to navigate back to the previous fragment on Back button click
//        binding.backButton.setOnClickListener {
//            // Navigate back to the previous fragment
//            findNavController().popBackStack()
//        }
//    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
