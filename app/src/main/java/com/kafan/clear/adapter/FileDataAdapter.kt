package com.kafan.clear.adapter

import com.kafan.clear.R
import com.kafan.clear.base.BaseBindingQuickAdapter
import com.kafan.clear.data.FileAndroidData
import com.kafan.clear.databinding.ItemMobileFileBinding

/**
 * @author ysj
 * @date 2021/10/29
 * @description
 **/
class FileDataAdapter :
    BaseBindingQuickAdapter<FileAndroidData, ItemMobileFileBinding>
        (R.layout.item_mobile_file) {

    override fun convert(holder: BaseBindingHolder, item: FileAndroidData) {
        val binding = holder.getViewBinding<ItemMobileFileBinding>()
        binding.run {
            ivIcon.setImageDrawable(item.icon)
            tvLabel?.text = item.label
            tvPackageName?.text = item.packageName
        }
    }
}