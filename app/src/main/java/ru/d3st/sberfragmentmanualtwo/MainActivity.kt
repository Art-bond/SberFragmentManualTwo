package ru.d3st.sberfragmentmanualtwo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.add
import androidx.fragment.app.commit
import androidx.fragment.app.replace

const val BACK_COUNT = "BACK_COUNT"
const val ACTIVITY_TAG = "ACTIVITY_TAG"
const val BACK_STACK = " BACK_STACK"

enum class OperationFragment {
    REPLACE, ADD, NOTHING
}

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (savedInstanceState == null) {
            val bundleZero = bundleOf(BACK_COUNT to 0)
            supportFragmentManager.commit {
                setReorderingAllowed(true)
                add<FragmentFirst>(R.id.fragment_container_first)
                add<FragmentSecond>(R.id.fragment_container_second, args = bundleZero)
            }
        }

    }

    override fun onResume() {
        super.onResume()

        val firstFragment = supportFragmentManager.findFragmentById(R.id.fragment_container_first)
        if (firstFragment != null) {
            val btnAdd = firstFragment.requireView().findViewById<Button>(R.id.btn_add).apply {
                setOnClickListener {
                    //select operation (Replace or Add)
                    val operationFragment = getFragmentOperation(firstFragment)
                    //status CheckBox
                    val checkState = getCheckState(firstFragment)
                    //amount Fragments in BackStack
                    var backCount = supportFragmentManager.backStackEntryCount
                    //if we add fragments in backStack, we increase count
                    // and provide it to now created fragment
                    if (checkState) backCount++
                    val bundleCount = bundleOf(BACK_COUNT to backCount)
                    supportFragmentManager.commit {
                        if (operationFragment == OperationFragment.ADD) {
                            add<FragmentSecond>(
                                R.id.fragment_container_second,
                                args = bundleCount
                            )
                        }
                        if (operationFragment == OperationFragment.REPLACE) {
                            replace<FragmentSecond>(
                                R.id.fragment_container_second,
                                args = bundleCount
                            )
                        }
                        setReorderingAllowed(true)
                        if (checkState) addToBackStack(BACK_STACK)
                    }
                    Log.i(ACTIVITY_TAG, "stack $backCount")

                }
            }
            val btnRemove =
                firstFragment.requireView().findViewById<Button>(R.id.btn_remove).apply {
                    setOnClickListener {
                        //remove fragment from BackStack
                        supportFragmentManager.popBackStack()
                    }
                }
        }
    }
}


private fun getCheckState(firstFragment: Fragment): Boolean {
    val checkBox = firstFragment.requireView().findViewById<CheckBox>(R.id.check_back)
    return checkBox.isChecked
}

private fun getFragmentOperation(firstFragment: Fragment): OperationFragment {
    val radioGroup =
        firstFragment.requireView().findViewById<RadioGroup>(R.id.radio_container)
    return when (radioGroup.checkedRadioButtonId) {
        R.id.radio_replace -> OperationFragment.REPLACE
        R.id.radio_add -> OperationFragment.ADD
        else -> OperationFragment.NOTHING
    }
}


