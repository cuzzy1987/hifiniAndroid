package com.me.hifimusic.ui.dashboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class DashboardViewModel : ViewModel() {

    private val _text = MutableLiveData<String>().apply {
//        value = "This is dashboard Fragment"
        value = "<div class=\"media-body\"> \n" +
                "     <div class=\"subject break-all\"> \n" +
                "         <i class=\"icon-top-3\"></i> \n" +
                "         <a href=\"thread-6.htm\">\n" +
                "             <span style=\"color:#007ef7;\"> 积分 / VIP 使用问题说明, 砥砺前行, 不忘初心</span>\n" +
                "        </a> \n" +
                "     </div> \n" +
                "     <div class=\"d-flex justify-content-between small mt-1\"> \n" +
                "      <div> \n" +
                "          <span class=\"haya-post-info-username \"> \n" +
                "              <a style=\"color:#748594;\" href=\"forum-9.htm\">\n" +
                "                <span class=\"board-bg\" style=\"border-radius: 2px;background-color: #f1c84c;width: 0.64rem;height: 0.64rem;display: inline-block;margin-right: 5px;\">\n" +
                "                </span>站务\n" +
                "            </a> \n" +
                "            <span class=\"koox-g  hidden-sm \"> • </span> \n" +
                "            <span class=\"username text-grey mr-1   hidden-sm\" uid=\"1\">Admin</span> \n" +
                "            <span class=\"date text-grey hidden-sm\">2018-12-10</span> </span> <span> \n" +
                "            <span class=\"text-grey mx-2\">←</span> <span class=\"username text-grey mr-1 \" uid=\"939435\">525347</span> <span class=\"text-grey\">11小时前</span> </span> \n" +
                "      </div> \n" +
                "      <div class=\"text-muted small\"> <span class=\"eye comment-o ml-2 hidden-sm d-none\"><i class=\"jan-icon-fire-1\"></i>171767</span> <span class=\"reply comment-o ml-2 hidden-sm\">648</span> <span class=\"rank comment-o ml-2 hidden-sm d-none\"><i class=\"jan-icon-crown\"></i>No.1</span> \n" +
                "      </div> \n" +
                "     </div> \n" +
                "    </div>\n" +
                "    <div class=\"media-body\"> "
    }
    val text: LiveData<String> = _text
}