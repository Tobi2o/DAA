package ch.heigvd.iict.daa.template

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

class StepFragment : Fragment() {

    companion object {
        private const val ARG_STEP_NUMBER = "step_number"

        // Méthode statique pour créer une nouvelle instance du fragment avec l'étape courante
        fun newInstance(stepNumber: Int): StepFragment {
            val fragment = StepFragment()
            val args = Bundle()
            args.putInt(ARG_STEP_NUMBER, stepNumber)
            fragment.arguments = args
            return fragment
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_step, container, false)
        val stepNumber = arguments?.getInt(ARG_STEP_NUMBER) ?: 0
        val textViewStep = view.findViewById<TextView>(R.id.textViewStep)
        textViewStep.text = "Step $stepNumber"
        return view
    }
}
