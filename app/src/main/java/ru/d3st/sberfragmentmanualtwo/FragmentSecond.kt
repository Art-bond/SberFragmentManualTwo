package ru.d3st.sberfragmentmanualtwo

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment

class FragmentSecond : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val someCount = requireArguments().getInt(BACK_COUNT)
        val textCount = view.findViewById<TextView>(R.id.tv_count).apply {
            text = resources.getString(R.string.fragment_in_backstack, someCount)
        }
    }
}