package com.program.module_moyu.callback;

import com.program.moudle_base.model.MoyuRequestBean;
import com.program.moudle_base.base.IBaseCallback;
import com.program.moudle_base.model.MoyuItemBean;
import com.trello.rxlifecycle4.LifecycleTransformer;

import java.util.List;

public interface IMoyuListFragmentCallback extends IBaseCallback {

    String getKey();

    void setMoyuUpdate(MoyuRequestBean.DataBean data);

    void setList(List<MoyuItemBean> data);

    void setListMore(List<MoyuItemBean> data);

    void setErrorMsg(String msg);

    LifecycleTransformer<Object> TobindToLifecycle();
}
