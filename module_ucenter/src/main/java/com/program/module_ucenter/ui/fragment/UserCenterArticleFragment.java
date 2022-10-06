package com.program.module_ucenter.ui.fragment;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.program.lib_base.LogUtils;
import com.program.lib_common.Constants;
import com.program.module_ucenter.R;
import com.program.module_ucenter.adapter.UserCenterArticleAdapter;
import com.program.module_ucenter.callback.IUserCenterArticleCallback;
import com.program.module_ucenter.model.domain.ArticleBean;
import com.program.module_ucenter.model.domain.ShareBean;
import com.program.module_ucenter.model.domain.UserWendaBean;
import com.program.module_ucenter.presenter.IUserCenterArticlePresenter;
import com.program.module_ucenter.utils.PresenterManager;
import com.program.moudle_base.base.BaseApplication;
import com.program.moudle_base.base.BaseFragment;
import com.program.moudle_base.utils.ToastUtils;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.OnLoadMoreListener;
import com.scwang.smart.refresh.layout.listener.OnRefreshListener;

import kotlin.collections.CollectionsKt;

public class UserCenterArticleFragment extends BaseFragment implements IUserCenterArticleCallback {

    private String mUserId;
    private String mDataType;
    private UserCenterArticleAdapter mAdapter;
    private IUserCenterArticlePresenter mCenterArticlePresenter;
    private RecyclerView mRvList;
    private SmartRefreshLayout mRefreshLayout;

    @Override
    protected int getRootViewResId() {
        return R.layout.moduleucenter_fragment_ucenter_list;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.LOADING);
        mUserId = requireArguments().getString("userId");
        mDataType = requireArguments().getString(Constants.DATA_TYPE);
        LogUtils.d(UserCenterArticleFragment.class, "mUserId = " + mUserId);
        LogUtils.d(UserCenterArticleFragment.class, "mDataType = " + mDataType);

        switch (mDataType) {
            case Constants.DATA_TPTE_ARTICLE:
                mAdapter = new UserCenterArticleAdapter(R.layout.moduleucenter_adapter_article, CollectionsKt.mutableListOf());
                break;
            case Constants.DATA_TPTE_SHARA:
                mAdapter = new UserCenterArticleAdapter(R.layout.moduleucenter_adapter_share, CollectionsKt.mutableListOf());
                break;
            case Constants.DATA_TPTE_WENDA:
                mAdapter = new UserCenterArticleAdapter(R.layout.moduleucenter_adapter_wenda, CollectionsKt.mutableListOf());
                break;
        }
        mRvList = rootView.findViewById(R.id.rv_list);
        mRvList.setLayoutManager(new LinearLayoutManager(BaseApplication.getAppContext()));
        mRvList.setAdapter(mAdapter);
        mRefreshLayout = rootView.findViewById(R.id.refreshLayout);
    }

    /**
     * 1文章
     * 2分享
     * 3问答
     */
    @Override
    protected void initListener() {
        mRefreshLayout.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(@NonNull RefreshLayout refreshLayout) {
                MultiItemEntity item = mAdapter.getItem(0);
                switch (item.getItemType()) {
                    case 1:
                        mCenterArticlePresenter.getArticleList(mUserId);
                        break;
                    case 2:
                        mCenterArticlePresenter.getShareList(mUserId);
                        break;
                    case 3:
                        mCenterArticlePresenter.getWendaList(mUserId);
                        break;
                }
            }
        });
        mRefreshLayout.setOnLoadMoreListener(new OnLoadMoreListener() {
            @Override
            public void onLoadMore(@NonNull RefreshLayout refreshLayout) {
                MultiItemEntity item = mAdapter.getItem(0);
                switch (item.getItemType()) {
                    case 1:
                        mCenterArticlePresenter.getArticleListMore(mUserId);
                        break;
                    case 2:
                        mCenterArticlePresenter.getShareListMore(mUserId);
                        break;
                    case 3:
                        mCenterArticlePresenter.getWendaListMore(mUserId);
                        break;
                }
            }
        });
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mCenterArticlePresenter = PresenterManager.getInstance().getUserCenterArticlePresenter();
        mCenterArticlePresenter.registerViewCallback(this);
        switch (mDataType) {
            case Constants.DATA_TPTE_ARTICLE:
                mCenterArticlePresenter.getArticleList(mUserId);
                break;
            case Constants.DATA_TPTE_SHARA:
                mCenterArticlePresenter.getShareList(mUserId);
                break;
            case Constants.DATA_TPTE_WENDA:
                mCenterArticlePresenter.getWendaList(mUserId);
                break;
        }
    }


    @Override
    public void setArticleData(ArticleBean data) {
        LogUtils.d("test", "data size == " + data.getData().getList().size());
        mAdapter.setList(data.getData().getList());
        finishRefresh();
        if (!data.getData().getHasNext()) {
            mRefreshLayout.setEnableLoadMore(false);
        }
        setupState(State.SUCCESS);
    }

    private void finishRefresh() {
        if (mRefreshLayout.isRefreshing()) {
            mRefreshLayout.finishRefresh();
        }
    }

    @Override
    public void setShareData(ShareBean data) {
        mAdapter.setList(data.getData().getList());
        finishRefresh();
        if (!data.getData().getHasNext()) {
            mRefreshLayout.setEnableLoadMore(false);
        }
        setupState(State.SUCCESS);
    }

    @Override
    public void setWendaData(UserWendaBean data) {
        mAdapter.setList(data.getData().getContent());
        finishRefresh();
        if (data.getData().getLast()) {
            mRefreshLayout.setEnableLoadMore(false);
        }
        setupState(State.SUCCESS);
    }

    @Override
    public void setArticleDataMore(ArticleBean data) {
        mAdapter.addData(data.getData().getList());
        finishLoading();
    }

    private void finishLoading() {
        if (mRefreshLayout.isLoading()) {
            mRefreshLayout.finishLoadMore();
        }
    }

    @Override
    public void setShareDataMore(ShareBean data) {
        mAdapter.addData(data.getData().getList());
        LogUtils.d(UserCenterArticleFragment.class,"setShareDataMore share data == "+data.toString());
        finishLoading();
    }

    @Override
    public void setWendaDataMore(UserWendaBean data) {
        mAdapter.addData(data.getData().getContent());
        finishLoading();
    }

    @Override
    public void ToastErrorMsg(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {
        setupState(State.EMPTY);
    }
}