package com.program.module_wenda.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.entity.MultiItemEntity;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.chad.library.adapter.base.listener.OnItemClickListener;
import com.makeramen.roundedimageview.RoundedImageView;
import com.program.lib_base.LogUtils;
import com.program.lib_common.DateUtils;
import com.program.lib_common.RoutePath;
import com.program.lib_common.UIUtils;
import com.program.lib_common.service.ucenter.wrap.UcenterServiceWrap;
import com.program.module_wenda.R;
import com.program.module_wenda.adapter.WendaAnswerAdapter;
import com.program.module_wenda.callback.IWendaAnswerCallback;
import com.program.module_wenda.model.bean.AnswerBean;
import com.program.module_wenda.model.bean.WendaContentBean;
import com.program.module_wenda.model.bean.WendaSubCommentInputBean;
import com.program.module_wenda.presenter.IWendaAnswerPresenter;
import com.program.module_wenda.presenter.Impl.WendaAnswerPresenterImpl;
import com.program.module_wenda.utils.PresenterManager;
import com.program.moudle_base.adapter.CommPriseAdapter;
import com.program.moudle_base.model.BaseResponseBean;
import com.program.moudle_base.model.PriseSobBean;
import com.program.moudle_base.model.SubCommentBean;
import com.program.moudle_base.model.TitleMultiBean;
import com.program.moudle_base.utils.CommonViewUtils;
import com.program.moudle_base.utils.ToastUtils;
import com.program.moudle_base.view.FixedHeightBottomSheetDialog;
import com.program.moudle_base.view.MyCodeViewJava;
import com.program.moudle_base.view.ReplyBottomSheetDialog;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;
import com.trello.rxlifecycle4.components.support.RxAppCompatActivity;

import net.mikaelzero.mojito.Mojito;
import net.mikaelzero.mojito.loader.glide.GlideImageLoader;
import net.mikaelzero.mojito.view.sketch.SketchImageLoadFactory;

import java.util.Iterator;
import java.util.List;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

@Route(path = RoutePath.Wenda.PAGE_ANSWER_DETAIL)
public class WendaAnswerActivity extends RxAppCompatActivity implements IWendaAnswerCallback {

    @Autowired(name = RoutePath.Wenda.PARAMS_WENDA_CONTENT)
    public WendaContentBean.DataBean mWendaContentBean;

    @Autowired(name = RoutePath.Wenda.PARAMS_ANSWER)
    public AnswerBean.DataBean mAnswerBean;

    private WendaAnswerAdapter mAdapter;
    private RecyclerView mRvContent;
    private FixedHeightBottomSheetDialog mBottomSheetDialog;
    private ReplyBottomSheetDialog mReplyDialog;
    private TextView mTvHeaderBickName;
    private RoundedImageView mIvHeaderAvatar;
    private MyCodeViewJava mCodeView;
    private TextView mTvThumb;
    private TextView mTvTitle;
    private TextView mTvSob;
    private TextView mTvView;
    private TextView mTvPublishTime;
    private TextView mTvQuestioner;
    private IWendaAnswerPresenter mWendaAnswerPresenter;
    private TextView mTvHeaderFollow;
    private TextView mTvReply;
    private TextView mTvReward;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulewenda_wenda_activity_answer);
        ARouter.getInstance().inject(this);         //不添加会收不到信息
        LogUtils.d("test", "mWendaContentBean = " + mWendaContentBean);
        LogUtils.d("test", "mAnswerBean = " + mAnswerBean);
        Mojito.initialize(GlideImageLoader.Companion.with(this), new SketchImageLoadFactory()); //没有这个图片不会显示
        initView();
        initPresenter();
        initListener();
    }

    private void initPresenter() {
        mWendaAnswerPresenter = PresenterManager.getInstance().getWendaAnswerPresenter();
        mWendaAnswerPresenter.registerViewCallback(this);
        //检查是否点赞
        mWendaAnswerPresenter.isThumbCheck(mAnswerBean.getId());
    }

    private void initListener() {
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object item = adapter.getItem(position);
                if (item instanceof SubCommentBean){
                    int id = view.getId();
                    if (id == R.id.tv_your_nickname || id == R.id.iv_your_avatar) {
                        UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchDetail(((SubCommentBean) item).getYourUid());
                    }else if (id==R.id.tv_be_nickname){
                        UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchDetail(((SubCommentBean) item).getBeUid());
                    } else if (id == R.id.iv_comment_reply) {
                        showReplyDialog((SubCommentBean) item);
                    }
                }
            }
        });
        mTvThumb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mTvThumb.getTag() == null) {
                    mWendaAnswerPresenter.toWendaCommentThumb(mAnswerBean.getId());
                }
            }
        });
        //todo:关注
        mIvHeaderAvatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mAnswerBean != null) {
                    UcenterServiceWrap.Singletion.INSTANCE.getHolder().launchDetail(mAnswerBean.getUid());
                }
            }
        });
        mTvReply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyDialog(null);
            }
        });
        //todo:点赞
        //打赏
        mTvReward.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showPriseDialog();
            }
        });
    }

    private void showPriseDialog() {
        LayoutInflater inflater = LayoutInflater.from(this);
        View inflate = inflater.inflate(R.layout.modulebase_common_prise_dialog, null);
        mBottomSheetDialog.setContentView(inflate);
        mBottomSheetDialog.show();

        RoundedImageView ivAvatar = inflate.findViewById(R.id.iv_avatar);
        TextView tvNickName = inflate.findViewById(R.id.tv_nickname);
        Glide.with(ivAvatar.getContext())
                .load(mAnswerBean.getAvatar())
                .placeholder(com.program.moudle_base.R.drawable.shape_grey_background)
                .circleCrop()
                .into(ivAvatar);
        tvNickName.setText(mAnswerBean.getNickname());
        inflate.findViewById(R.id.iv_close).setOnClickListener(view -> mBottomSheetDialog.dismiss());
        RecyclerView rvSob = inflate.findViewById(R.id.rv_sob);
        rvSob.setLayoutManager(new GridLayoutManager(this,3));
        CommPriseAdapter commPriseAdapter = new CommPriseAdapter();
        rvSob.setAdapter(commPriseAdapter);
        PriseSobBean selectItem = new PriseSobBean("",20,true);
        Button btnPrise = inflate.findViewById(R.id.btn_prise);
        commPriseAdapter.setOnItemClickListener(new OnItemClickListener() {
            @Override
            public void onItemClick(@NonNull BaseQuickAdapter<?, ?> adapter, @NonNull View view, int position) {
                Object item = adapter.getItem(position);
                PriseSobBean data = (PriseSobBean) item;
                selectItem.setLabel(data.getLabel());
                selectItem.setValue(data.getValue());
                selectItem.setChecked(data.isChecked());
                List<PriseSobBean> beanList = commPriseAdapter.getData();
                Iterator<PriseSobBean> iterator = beanList.iterator();
                while (iterator.hasNext()) {
                    PriseSobBean next = iterator.next();
                    next.setChecked(selectItem.getValue()==next.getValue());
                }
                commPriseAdapter.notifyDataSetChanged();
            }
        });
        btnPrise.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mWendaAnswerPresenter.toCommentPrise(mAnswerBean.getId(),selectItem.getValue());
                mBottomSheetDialog.dismiss();
            }
        });

    }

    private void showReplyDialog(SubCommentBean subComment) {
        mReplyDialog.show();
        if (subComment==null) {
            if (mAnswerBean != null) {
                mReplyDialog.setReplyTitle("评论 @"+mAnswerBean.getNickname());
            }
        }else {
            mReplyDialog.setReplyTitle("评论 @"+subComment.getBeNickname());
        }
        mReplyDialog.sendListener(new ReplyBottomSheetDialog.OnSendListener() {
            @Override
            public void onSend(String v) {
                if (subComment==null){
                    replyAnswer(v);
                }else {
                    replyComment(v,subComment);
                }
            }
        });

    }

    private void replyComment(String v, SubCommentBean subComment) {
        WendaSubCommentInputBean wendaSubCommentInputBean = new WendaSubCommentInputBean(
                subComment.getParentId(),
                subComment.getBeNickname(),
                subComment.getBeUid(),
                mAnswerBean.getWendaId(),
                v
        );
        mWendaAnswerPresenter.replyAnswer(wendaSubCommentInputBean);
    }

    private void replyAnswer(String v) {
        if (mAnswerBean != null) {
            WendaSubCommentInputBean wendaSubCommentInputBean = new WendaSubCommentInputBean(
                    mAnswerBean.getId(),
                    mAnswerBean.getNickname(),
                    mAnswerBean.getUid(),
                    mAnswerBean.getWendaId(),
                    v
            );
            mWendaAnswerPresenter.replyAnswer(wendaSubCommentInputBean);
        }
    }

    private void initView() {
        RelativeLayout includeBar = this.findViewById(R.id.include_bar);
        mRvContent = this.findViewById(R.id.rv_content);
        mTvHeaderBickName = this.findViewById(R.id.tv_header_nickname);
        mIvHeaderAvatar = this.findViewById(R.id.iv_header_avatar);
        mTvThumb = this.findViewById(R.id.tv_thumb);
        mTvHeaderFollow = this.findViewById(R.id.tv_header_follow);
        mTvReply = this.findViewById(R.id.tv_reply);
        mTvReward = this.findViewById(R.id.tv_reward);
        includeBar.findViewById(R.id.tvSearch).setVisibility(View.GONE);
        includeBar.findViewById(R.id.ivBack).setOnClickListener(view -> finish());

        LayoutInflater inflater = LayoutInflater.from(this);
        View inflate = inflater.inflate(R.layout.modulewenda_wenda_detail_header, null);
        mCodeView = inflate.findViewById(R.id.codeView);
        mTvTitle = inflate.findViewById(R.id.tv_title);
        mTvSob = inflate.findViewById(R.id.tv_sob);
        mTvView = inflate.findViewById(R.id.tv_view);
        mTvPublishTime = inflate.findViewById(R.id.tv_publishTime);
        mTvQuestioner = inflate.findViewById(R.id.tv_questioner);
        TextView tvLabels = inflate.findViewById(R.id.tv_labels);
        tvLabels.setVisibility(View.GONE);
        RelativeLayout layoutVisibility = inflate.findViewById(R.id.layout_visibility);
        layoutVisibility.setVisibility(View.VISIBLE);

        mAdapter = new WendaAnswerAdapter();
        mRvContent.setHasFixedSize(true);
        mRvContent.setAdapter(mAdapter);
        mRvContent.setLayoutManager(new LinearLayoutManager(this));
        mAdapter.addHeaderView(inflate);
        mBottomSheetDialog = new FixedHeightBottomSheetDialog(
          this,
          R.style.BottomSheetDialog,
                UIUtils.getScreenWidth()*2/3
        );
        mReplyDialog = new ReplyBottomSheetDialog(
                this,
                R.style.BottomSheetDialog
        );
        setViewData();
    }

    @SuppressLint({"JavascriptInterface", "SetTextI18n"})
    private void setViewData() {
        if (mAnswerBean != null) {
            mTvHeaderBickName.setText(mAnswerBean.getNickname());
            Glide.with(mIvHeaderAvatar.getContext())
                    .load(mAnswerBean.getAvatar())
                    .placeholder(com.program.moudle_base.R.drawable.shape_grey_background)
                    .circleCrop()
                    .into(mIvHeaderAvatar);
            mCodeView.showCode(mAnswerBean.getContent());
            mCodeView.addJavascriptInterface(this,"native");
            mCodeView.setWebViewClient(new WebViewClient(){
                @Override
                public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                    CommonViewUtils.toWebView(request.getUrl().toString());
                    return true;
                }
            });
            mAdapter.addData(new TitleMultiBean("评论（"+mAnswerBean.getWendaSubComments().size()+"）"));
            mAdapter.addData(mAnswerBean.getWendaSubComments());
            //todo:用户关系

            mTvThumb.setText(mAnswerBean.getThumbUp().toString());
        }
        if (mWendaContentBean != null) {
            if (mWendaContentBean.getIsResolve().equals("1")) {
                String title = "【已解决】"+mWendaContentBean.getTitle();
                mTvTitle.setText(
                        UIUtils.setTextViewContentStyle(
                                title,
                                new AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                                new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorSuccess)),
                                0,5
                        )
                );
            }else {
                mTvTitle.setText(mWendaContentBean.getTitle());
            }
            mTvSob.setText(mWendaContentBean.getSob()+"币");
            mTvView.setText(mWendaContentBean.getViewCount()+"浏览");
            mTvPublishTime.setText("发表于"+ DateUtils.timeFormat(mWendaContentBean.getCreateTime()));
            String str = "提问者 @"+mWendaContentBean.getNickname();
            mTvQuestioner.setText(UIUtils.setTextViewContentStyle(
                    str,
                    new AbsoluteSizeSpan(UIUtils.dp2px(14f)),
                    new ForegroundColorSpan(ContextCompat.getColor(this,R.color.colorPrimary)),
                    str.indexOf("@"),str.length()
            ));
        }
    }

    @Override
    public void replyAnswerReturn(BaseResponseBean data) {
        ToastUtils.showToast("评论成功");
        mReplyDialog.dismiss();
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
//        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
//        return  RxLifecycle.bind(objectBehaviorSubject);
        return this.bindToLifecycle();
    }

    @Override
    public void setRequestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void setPriseResult(BaseResponseBean data) {
        if (data.getSuccess()){
            ToastUtils.showToast("谢谢老板打赏");
        }
    }

    @Override
    public void setReturnThumbClick(BaseResponseBean data) {
        if (data.getSuccess()&&Integer.parseInt(data.getData()+"")!=0){
            mTvThumb.setTag(true);
            CommonViewUtils.setThumbStyle(mTvThumb,true);
        }
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void setReturnClickThumb(BaseResponseBean data) {
        if (data.getSuccess()){
            mTvThumb.setTag(true);
            mTvThumb.setText((mAnswerBean.getThumbUp()+1)+"");
            CommonViewUtils.setThumbStyle(mTvThumb,true);
        }
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