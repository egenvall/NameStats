package com.egenvall.namestats.model

import com.egenvall.namestats.R
import com.egenvall.namestats.databinding.HeaderContactBinding
import com.genius.groupie.Item


open class ContactHeader(val letter : String, val childrenCount : Int) : Item<HeaderContactBinding>() {
    override fun getLayout() = R.layout.header_contact
    override fun bind(binding: HeaderContactBinding, position: Int) {
        binding.headerLabel.text = letter
        binding.headerCount.text = childrenCount.toString()
    }
}