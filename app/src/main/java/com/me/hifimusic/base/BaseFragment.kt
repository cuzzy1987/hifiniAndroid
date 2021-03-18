package com.me.hifimusic.base

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment

open class BaseFragment: Fragment() {

    val _TAG = "BaseFragment"

    override fun onAttach(context: Context) {
        super.onAttach(context)
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onAttach ")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onCreate ")
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onCreateView ")
        return super.onCreateView(inflater, container, savedInstanceState)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onActivityCreated ")
    }

    override fun onStart() {
        super.onStart()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onStart ")
    }

    override fun onResume() {
        super.onResume()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onResume ")
    }

    override fun onPause() {
        super.onPause()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onPause ")
    }

    override fun onStop() {
        super.onStop()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onStop ")
    }

    override fun onDestroyView() {
        super.onDestroyView()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onDestroyView ")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onDestroy ")
    }

    override fun onDetach() {
        super.onDetach()
        Log.d(_TAG,"${this::javaClass.get().name}_${this::javaClass.hashCode()} onDetach ")
    }

}