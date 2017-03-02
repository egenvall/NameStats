package com.egenvall.namestats.model

import com.egenvall.namestats.R
import com.egenvall.namestats.databinding.ItemContactBinding
import com.genius.groupie.Item


class ContactItem(val name : String,val number: String, val onClick: (ContactItem) -> Unit ) : Item<ItemContactBinding>() {
    override fun getLayout() = R.layout.item_contact
    override fun bind(binding: ItemContactBinding, position: Int) {
        binding.name.text = name
        binding.root.setOnClickListener { onClick.invoke(this) }
    }
}