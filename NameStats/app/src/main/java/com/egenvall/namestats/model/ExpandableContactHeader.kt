package com.egenvall.namestats.model

import com.egenvall.namestats.databinding.HeaderContactBinding
import com.genius.groupie.ExpandableGroup
import com.genius.groupie.ExpandableItem


class ExpandableContactHeader(char: String, count: Int) : ContactHeader(char,count), ExpandableItem {
    private var expandableGroup : ExpandableGroup? = null

    override fun bind(binding: HeaderContactBinding, position: Int) {
        super.bind(binding, position)
        binding.root.setOnClickListener { expandableGroup?.onToggleExpanded() }

    }
    override fun setExpandableGroup(onToggleListener: ExpandableGroup?) {
        expandableGroup = onToggleListener
    }
}