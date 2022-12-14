package com.program.module_ucenter.ui;

import androidx.annotation.Nullable;

import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Route;
import com.allen.library.SuperTextView;
import com.program.lib_base.LogUtils;
import com.program.lib_base.StatusBarUtil;
import com.program.lib_common.UIUtils;
import com.program.lib_common.Constants;
import com.program.lib_common.RoutePath;
import com.program.lib_common.service.ucenter.wrap.UcenterServiceWrap;
import com.program.module_ucenter.R;
import com.program.module_ucenter.callback.IMsgCenterCallback;
import com.program.module_ucenter.model.domain.UnreadMsgBean;
import com.program.module_ucenter.presenter.IMsgCenterPresenter;
import com.program.module_ucenter.utils.PresenterManager;
import com.scwang.smart.refresh.footer.ClassicsFooter;
import com.scwang.smart.refresh.header.ClassicsHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshFooter;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshFooterCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;

import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

@Route(path = RoutePath.Ucenter.PAGE_MESSAGE)
public class MsgCenterActivity extends RxAppCompatActivity implements IMsgCenterCallback {


    private RelativeLayout mRelativeLayoutBar;
    private ImageView mIvBack;
    private TextView mTvTitle;
    private TextView mTvSearch;
    private ImageView mIvRight;
    private SmartRefreshLayout mRefreshLayout;

    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.transparent, R.color.colorPrimary);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
    }

    private SuperTextView mTvWenda;
    private SuperTextView mTvArticle;
    private SuperTextView mTvDynamic;
    private SuperTextView mTvStar;
    private SuperTextView mTvAt;
    private SuperTextView mTvSystem;
    private IMsgCenterPresenter mMsgCenterPresenter;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.moduleucenter_activity_msg_center);
        initView();
        initEvent();
        initPresenter();
        initStatusBar();
    }

    private void initStatusBar() {
        StatusBarUtil.immersive(this);
        StatusBarUtil.darkMode(this,true);
        StatusBarUtil.setPaddingSmart(this,mRelativeLayoutBar);
    }

    protected void initEvent() {
       mTvTitle.setText("消息通知");
       mTvSearch.setText("全部已读");
       mTvSearch.setVisibility(View.VISIBLE);
       mIvRight.setVisibility(View.GONE);
       initListener();
    }

    private void initListener() {
        mTvWenda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //问答回答
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_WENDA,"问答回答");
            }
        });
        mTvArticle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //文章评论
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_ARTICLE,"文章评论");
            }
        });
        mTvDynamic.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //动态评论
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_DYNAMIC,"动态评论");
            }
        });
        mTvStar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //点赞
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_STAR,"给朕点赞");
            }
        });
        mIvBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        mTvSystem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_SYSTEM,"系统消息");
            }
        });
        mTvAt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchMsgList(Constants.Ucenter.PAGE_MSG_AT,"@我的");
            }
        });
    }


    protected void initPresenter() {
        mMsgCenterPresenter = PresenterManager.getInstance().getMsgCenterPresenter();
        mMsgCenterPresenter.registerViewCallback(this);
        mMsgCenterPresenter.getMsg();
    }


    protected void initView() {
        mRelativeLayoutBar = this.findViewById(R.id.include_bar);
        mIvBack = mRelativeLayoutBar.findViewById(R.id.ivBack);
        mTvTitle = mRelativeLayoutBar.findViewById(R.id.tv_title);
        mTvSearch = mRelativeLayoutBar.findViewById(R.id.tvSearch);
        mIvRight = mRelativeLayoutBar.findViewById(R.id.ivRight);
        mRefreshLayout = this.findViewById(R.id.refreshLayout);
        mTvWenda = this.findViewById(R.id.tv_wenda);
        mTvArticle = this.findViewById(R.id.tv_article);
        mTvDynamic = this.findViewById(R.id.tv_dynamic);
        mTvStar = this.findViewById(R.id.tv_star);
        mTvAt = this.findViewById(R.id.tv_at);
        mTvSystem = this.findViewById(R.id.tv_system);
    }

    @Override
    public void setMsg(UnreadMsgBean data) {
        UnreadMsgBean.DataBean msg = data.getData();

        if (msg.getWendaMsgCount()!=0){
            LogUtils.d("test","getWendaMsgCount =="+msg.getWendaMsgCount());
            mTvWenda.setRightString(checkNum(msg.getWendaMsgCount()));
            mTvWenda.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvWenda.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvWenda.getRightTextView().setVisibility(View.GONE);
        }
        if (msg.getArticleMsgCount()!=0){
            mTvArticle.setRightString(checkNum(msg.getArticleMsgCount()));
            mTvArticle.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvArticle.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvArticle.getRightTextView().setVisibility(View.GONE);
        }

        if (msg.getMomentCommentCount()!=0){
            mTvDynamic.setRightString(checkNum(msg.getMomentCommentCount()));
            mTvDynamic.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvDynamic.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvDynamic.getRightTextView().setVisibility(View.GONE);
        }

        if (msg.getThumbUpMsgCount()!=0){
            mTvStar.setRightString(checkNum(msg.getThumbUpMsgCount()));
            mTvStar.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvStar.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvStar.getRightTextView().setVisibility(View.GONE);
        }

        if (msg.getAtMsgCount()!=0){
            mTvAt.setRightString(checkNum(msg.getAtMsgCount()));
            mTvAt.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvAt.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvAt.getRightTextView().setVisibility(View.GONE);
        }

        if (msg.getSystemMsgCount()!=0){
            mTvSystem.setRightString(checkNum(msg.getSystemMsgCount()));
            mTvSystem.getRightTextView().setHeight(UIUtils.dp2px(16f));
            mTvSystem.getRightTextView().setWidth(UIUtils.dp2px(16f));
        }else {
            mTvSystem.getRightTextView().setVisibility(View.GONE);
        }
    }

    @Override
    public void setReanReturn() {
        mTvWenda.getRightTextView().setVisibility(View.GONE);
        mTvArticle.getRightTextView().setVisibility(View.GONE);
        mTvDynamic.getRightTextView().setVisibility(View.GONE);
        mTvStar.getRightTextView().setVisibility(View.GONE);
        mTvAt.getRightTextView().setVisibility(View.GONE);
        mTvSystem.getRightTextView().setVisibility(View.GONE);
    }

    private String checkNum(int num){
        if (num>99){
            return "99";
        }else {
            return num+"";
        }
    }

    @Override
    public void onMsgError(String error) {

    }

    @Override
    public LifecycleTransformer<Object> getBindLifecycle() {

        return bindToLifecycle();
    }


    @Override
    public void onError() {

    }

    @Override
    public void onLoading() {

    }

    @Override
    public void onEmpty() {

    }
}