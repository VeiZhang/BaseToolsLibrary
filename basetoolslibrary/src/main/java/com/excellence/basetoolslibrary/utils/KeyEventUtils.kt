package com.excellence.basetoolslibrary.utils

import android.view.View
import android.view.ViewGroup
import android.widget.AbsListView
import android.widget.AdapterView
import android.widget.GridView
import androidx.leanback.widget.BaseGridView
import androidx.leanback.widget.HorizontalGridView
import androidx.leanback.widget.VerticalGridView
import kotlin.math.min

/**
 * <pre>
 *     author : VeiZhang
 *     blog   : http://tiimor.cn
 *     time   : 2022/5/13
 *     desc   : 按键事件
 * </pre>
 */
object KeyEventUtils {

    /**
     * key up key event, cycle move
     *
     * @param parent
     * @param numColumns [VerticalGridView.setNumColumns]
     * @return
     */
    @JvmStatic
    fun listKeyUp(parent: VerticalGridView, numColumns: Int): Boolean {
        return listKeyUp(parent, numColumns, false)
    }

    /**
     * key up key event, cycle move
     *
     * @param parent
     * @param numColumns [VerticalGridView.setNumColumns]
     * @param handleKey 是否手动处理
     * @return
     */
    @JvmStatic
    fun listKeyUp(parent: VerticalGridView, numColumns: Int, handleKey: Boolean): Boolean {
        var selectedPosition = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1

        /**
         * 取最大 <= firstLine 是第一行
         */
        val firstLine = numColumns - 1

        /**
         * 取最小 >= lastLine 是最后一行
         */
        val lastLine = lastPos - lastPos % numColumns
        if (selectedPosition <= firstLine) {
            selectedPosition = min(lastLine + selectedPosition, lastPos)
            parent.selectedPosition = selectedPosition
            return true
        }
        if (handleKey) {
            /**
             * 手动设置：带锁弹框上下移动消失，并且需要传递key事件给列表时，需要手动设置选中，这样会选中下一个
             * 否则return false，自动选中，是不会选中下一个
             */
            selectedPosition -= numColumns
            selectedPosition = if (selectedPosition < 0) lastPos else selectedPosition
            parent.selectedPosition = selectedPosition
            return true
        }
        return false
    }

    /**
     * key down key event, cycle move
     *
     * @param parent
     * @param numColumns [VerticalGridView.setNumColumns]
     * @return
     */
    @JvmStatic
    fun listKeyDown(parent: VerticalGridView, numColumns: Int): Boolean {
        return listKeyDown(parent, numColumns, false)
    }

    /**
     * key down key event, cycle move
     *
     * @param parent
     * @param numColumns [VerticalGridView.setNumColumns]
     * @param handleKey 是否手动处理
     * @return
     */
    @JvmStatic
    fun listKeyDown(parent: VerticalGridView, numColumns: Int, handleKey: Boolean): Boolean {
        var selectedPosition = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1
        val lastLine = lastPos - lastPos % numColumns
        if (selectedPosition >= lastLine) {
            selectedPosition = min(selectedPosition % numColumns, lastPos)
            parent.selectedPosition = selectedPosition
            return true
        } else if (selectedPosition + numColumns > lastPos) {
            selectedPosition = lastPos
            parent.selectedPosition = selectedPosition
            return true
        }
        if (handleKey) {
            /**
             * 手动设置
             */
            selectedPosition += numColumns
            selectedPosition = if (selectedPosition > lastPos) 0 else selectedPosition
            parent.selectedPosition = selectedPosition
            return true
        }
        return false
    }

    /**
     * key down key event, cycle move
     *
     * @param parent
     * @param numColumns
     * @return
     */
    @JvmStatic
    fun listKeyLeft(parent: VerticalGridView, numColumns: Int): Boolean {
        if (numColumns <= 1) {
            return false
        }
        var selectedPosition = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1
        selectedPosition -= 1
        if (selectedPosition < 0) {
            selectedPosition = lastPos
            parent.selectedPosition = selectedPosition
            return true
        } else if ((selectedPosition + 1) % numColumns == 0) {
            parent.selectedPosition = selectedPosition
            return true
        }
        return false
    }

    /**
     * key down key event, cycle move
     *
     * @param parent
     * @param numColumns
     * @return
     */
    @JvmStatic
    fun listKeyRight(parent: VerticalGridView, numColumns: Int): Boolean {
        if (numColumns <= 1) {
            return false
        }
        var selectedPosition = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1
        selectedPosition += 1
        if (selectedPosition > lastPos) {
            selectedPosition = 0
            parent.selectedPosition = selectedPosition
            return true
        } else if (selectedPosition % numColumns == 0) {
            parent.selectedPosition = selectedPosition
            return true
        }
        return false
    }

    /**
     * page up key event, cycle move
     *
     * @param parent
     * @param pageItemCount
     */
    @JvmStatic
    fun listPageUp(parent: BaseGridView, pageItemCount: Int): Boolean {
        var pageItemCount = pageItemCount
        if (pageItemCount <= 0) {
            pageItemCount = parent.childCount
        }
        var selectPos = parent.selectedPosition
        val count = parent.adapter!!.itemCount
        selectPos = if (selectPos == 0) {
            count - 1
        } else {
            if (selectPos - pageItemCount < 0) {
                0
            } else {
                selectPos - pageItemCount
            }
        }
        parent.selectedPosition = selectPos
        return true
    }

    /**
     * page down key event, cycle move
     *
     * @param parent
     * @param pageItemCount
     */
    @JvmStatic
    fun listPageDown(parent: BaseGridView, pageItemCount: Int): Boolean {
        var pageItemCount = pageItemCount
        if (pageItemCount <= 0) {
            pageItemCount = parent.childCount
        }
        var selectPos = parent.selectedPosition
        val count = parent.adapter!!.itemCount
        selectPos = if (selectPos == count - 1) {
            0
        } else {
            if (selectPos + pageItemCount >= count) {
                count - 1
            } else {
                selectPos + pageItemCount
            }
        }
        parent.selectedPosition = selectPos
        return true
    }

    /**
     * key left key event, cycle move, default row is 1 [HorizontalGridView.setNumRows]
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyLeft(parent: HorizontalGridView): Boolean {
        var selectPos = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1
        if (selectPos <= 0) {
            selectPos = lastPos
            parent.selectedPosition = selectPos
            return true
        }
        return false
    }

    /**
     * key right key event, cycle move, default row is 1 [HorizontalGridView.setNumRows]
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyRight(parent: HorizontalGridView): Boolean {
        var selectPos = parent.selectedPosition
        val lastPos = parent.adapter!!.itemCount - 1
        if (selectPos >= lastPos) {
            selectPos = 0
            parent.selectedPosition = selectPos
            return true
        }
        return false
    }

    /**
     * key up key event, cycle move
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyUp(parent: AdapterView<*>): Boolean {
        var numColumns = 1
        if (parent is GridView) {
            numColumns = parent.numColumns
        }
        var selectPos = parent.selectedItemPosition
        val lastPos = parent.count - 1
        val firstLine = numColumns - 1
        if (selectPos <= firstLine) {
            selectPos = lastPos
            parent.setSelection(selectPos)
            return true
        }
        return false
    }

    /**
     * key down key event, cycle move
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyDown(parent: AdapterView<*>): Boolean {
        var numColumns = 1
        if (parent is GridView) {
            numColumns = parent.numColumns
        }
        var selectPos = parent.selectedItemPosition
        val lastPos = parent.count - 1
        val lastLine = lastPos - lastPos % numColumns
        if (selectPos >= lastLine) {
            selectPos = 0
            parent.setSelection(selectPos)
            return true
        }
        return false
    }

    /**
     * key left key event, cycle move
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyLeft(parent: AdapterView<*>): Boolean {
        var numRows = 1
        if (parent is GridView) {
            numRows = parent.getCount() / parent.numColumns
            if (numRows != 1) {
                return false
            }
        }
        var selectPos = parent.selectedItemPosition
        val lastPos = parent.count - 1
        if (selectPos <= 0) {
            selectPos = lastPos
            parent.setSelection(selectPos)
            return true
        }
        return false
    }

    /**
     * key right key event, cycle move
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyRight(parent: AdapterView<*>): Boolean {
        var numRows = 1
        if (parent is GridView) {
            numRows = parent.getCount() / parent.numColumns
            if (numRows != 1) {
                return false
            }
        }
        var selectPos = parent.selectedItemPosition
        val lastPos = parent.count - 1
        if (selectPos >= lastPos) {
            selectPos = 0
            parent.setSelection(selectPos)
            return true
        }
        return false
    }

    /**
     * page up key event, cycle move
     *
     * @param parent
     * @param pageCount
     */
    @JvmStatic
    fun listPageUp(parent: AbsListView, pageCount: Int): Boolean {
        var selectPos = parent.selectedItemPosition
        val count = parent.count
        selectPos = if (selectPos == 0) {
            count - 1
        } else {
            if (selectPos - pageCount < 0) {
                0
            } else {
                selectPos - pageCount
            }
        }
        parent.smoothScrollToPositionFromTop(selectPos, 0, 0)
        parent.setSelection(selectPos)
        return true
    }

    /**
     * page down key event, cycle move
     *
     * @param parent
     * @param pageCount
     */
    @JvmStatic
    fun listPageDown(parent: AbsListView, pageCount: Int): Boolean {
        var selectPos = parent.selectedItemPosition
        val count = parent.count
        selectPos = if (selectPos == count - 1) {
            0
        } else {
            if (selectPos + pageCount >= count) {
                count - 1
            } else {
                selectPos + pageCount
            }
        }
        parent.smoothScrollToPositionFromTop(selectPos, 0, 0)
        parent.setSelection(selectPos)
        return true
    }

    /**
     * key left key event, cycle move, default row is 1 [HorizontalGridView.setNumRows]
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyLeft(parent: ViewGroup, focusView: View?): Boolean {
        var selectPos = parent.indexOfChild(focusView)
        val lastPos = parent.childCount - 1
        if (selectPos <= 0) {
            selectPos = lastPos
            parent.getChildAt(selectPos).requestFocus()
            return true
        }
        return false
    }

    /**
     * key right key event, cycle move, default row is 1 [HorizontalGridView.setNumRows]
     *
     * @param parent
     * @return
     */
    @JvmStatic
    fun listKeyRight(parent: ViewGroup, focusView: View?): Boolean {
        var selectPos = parent.indexOfChild(focusView)
        val lastPos = parent.childCount - 1
        if (selectPos >= lastPos) {
            selectPos = 0
            parent.getChildAt(selectPos).requestFocus()
            return true
        }
        return false
    }
}