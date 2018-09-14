package com.qxj.conclusion.MVPDevelop.View

import com.qxj.conclusion.ConclusionUtils.LogTool
import com.qxj.conclusion.MVPDevelop.MVP.BaseActivity
import com.qxj.conclusion.MVPDevelop.MVP.IPresenter
import com.qxj.conclusion.MVPDevelop.MVP.IView
import com.qxj.conclusion.MVPDevelop.Presenter.UserContract
import com.qxj.conclusion.MVPDevelop.Presenter.UserPresenter
import com.qxj.conclusion.R
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.toast

class MainActivity: BaseActivity(), UserContract.IUserView {

    private val TAG: String = MainActivity::class.java.name

    override val mPresenter: UserPresenter = UserPresenter(this)

    override fun getLayoutId(): Int = R.layout.activity_main

    override fun initView() {
        tvUser.setOnClickListener{
            LogTool.d(TAG, "开始添加名字")
            mPresenter.addUser("秦小杰")
        }
    }

    override fun showAddUserResult(boolean: Boolean) {
        LogTool.d(TAG, "添加名字结果")
        if (boolean) {
            toast("添加名字成功")
        } else {
            toast("添加名字失败")
        }
    }
}