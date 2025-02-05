package com.ilyeong.movieverse.presentation.profile

import android.view.LayoutInflater
import android.view.ViewGroup
import com.ilyeong.movieverse.databinding.FragmentProfileBinding
import com.ilyeong.movieverse.presentation.common.BaseFragment

class ProfileFragment : BaseFragment<FragmentProfileBinding>() {

    override val viewBindingInflater: (LayoutInflater, ViewGroup?, Boolean) -> FragmentProfileBinding
        get() = FragmentProfileBinding::inflate
}