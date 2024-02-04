package indi.midreamsheep.app.tre.plugin.markdown.parser.paragraph.head

import indi.midreamsheep.app.tre.context.api.annotation.setting.Config
import indi.midreamsheep.app.tre.model.setting.AbstractStandardConfig
import indi.midreamsheep.app.tre.model.setting.settingitems.IntInputSettingItem

@Config
class HeadConfiguration: AbstractStandardConfig() {
    private val head1Size = IntInputSettingItem(20, "一级标题大小")
    private val head2Size = IntInputSettingItem(30, "二级标题大小")
    private val head3Size = IntInputSettingItem(40, "三级标题大小")
    private val head4Size = IntInputSettingItem(50, "四级标题大小")
    private val head5Size = IntInputSettingItem(60, "五级标题大小")
    private val head6Size = IntInputSettingItem(70, "六级标题大小")
    override fun getConfigName(): String {
        return "标题配置"
    }
    fun getHeadSize(level: Int): Int {
        return when (level) {
            1 -> head1Size.getData()
            2 -> head2Size.getData()
            3 -> head3Size.getData()
            4 -> head4Size.getData()
            5 -> head5Size.getData()
            6 -> head6Size.getData()
            else -> 0
        }
    }
}