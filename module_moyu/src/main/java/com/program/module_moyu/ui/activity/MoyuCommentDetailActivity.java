package com.program.module_moyu.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.alibaba.android.arouter.facade.annotation.Autowired;
import com.alibaba.android.arouter.facade.annotation.Route;
import com.alibaba.android.arouter.launcher.ARouter;
import com.bumptech.glide.Glide;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.listener.OnItemChildClickListener;
import com.hjq.shape.layout.ShapeLinearLayout;
import com.hjq.shape.view.ShapeTextView;
import com.makeramen.roundedimageview.RoundedImageView;
import com.program.lib_base.LogUtils;
import com.program.lib_common.DateUtils;
import com.program.lib_common.RoutePath;
import com.program.module_moyu.R;
import com.program.module_moyu.adapter.FishCommentDetailListAdapter;
import com.program.module_moyu.callback.IMoyuCommentDetailActivityCallback;
import com.program.module_moyu.model.bean.MomentComment;
import com.program.module_moyu.model.bean.MomentCommentBean;
import com.program.module_moyu.model.bean.MomentSubComment;
import com.program.module_moyu.model.bean.MomentSubCommentBean;
import com.program.module_moyu.presenter.IMoyuCommentDetailActivityPresenter;
import com.program.module_moyu.utils.PresenterManager;
import com.program.moudle_base.model.BaseResponseBean;
import com.program.moudle_base.utils.SharedPreferencesUtils;
import com.program.moudle_base.utils.ToastUtils;
import com.program.moudle_base.view.ReplyBottomSheetDialog;
import com.trello.rxlifecycle4.LifecycleTransformer;
import com.trello.rxlifecycle4.RxLifecycle;

import io.reactivex.rxjava3.subjects.BehaviorSubject;

/**
 * 评论列表
 */
@Route(path = RoutePath.Moyu.PAGE_DETAIL_COMMENT)
public class MoyuCommentDetailActivity extends AppCompatActivity implements IMoyuCommentDetailActivityCallback {

    @Autowired(name = RoutePath.Moyu.MOYU_DETAIL_ID)
    public String mId;

    @Autowired(name = RoutePath.Moyu.COMMENT)
    public MomentCommentBean.DataBean.ListBean mComment;
    private FishCommentDetailListAdapter mAdapter;
    private ShapeTextView mClReplyContainer;
    private ReplyBottomSheetDialog mReplyBottomSheetDialog;
    private IMoyuCommentDetailActivityPresenter mMoyuCommentDetailActivityPresenter;
    private SharedPreferencesUtils mPreferencesUtils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.modulemoyu_activity_moyu_comment_detail);
        ARouter.getInstance().inject(this);         //不添加会收不到信息
        initView();
        initEvent();
        initPresenter();
    }

    private void initPresenter() {
        mPreferencesUtils = SharedPreferencesUtils.getInstance(this);
        mMoyuCommentDetailActivityPresenter = PresenterManager.getInstance().getMoyuCommentDetailActivityPresenter();
        mMoyuCommentDetailActivityPresenter.registerViewCallback(this);
    }

    private void initEvent() {
        mClReplyContainer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                showReplyDialog(null);
            }
        });
        mAdapter.setOnItemChildClickListener(new OnItemChildClickListener() {
            @Override
            public void onItemChildClick(@NonNull BaseQuickAdapter adapter, @NonNull View view, int position) {
                Object item = adapter.getItem(position);
                int id = view.getId();
                if (item instanceof MomentSubCommentBean) {
                    if (id==R.id.iv_fish_pond_comment) {
                        showReplyDialog((MomentSubCommentBean) item);
                    }
                }
            }
        });
    }

    @SuppressLint("SetTextI18n")
    private void initView() {
        TextView tvReplyAndGreat = this.findViewById(R.id.tv_reply_and_great);
        ConstraintLayout fishPondDetailComment =this.findViewById(R.id.fish_pond_detail_comment);
        RoundedImageView ivAvatar = fishPondDetailComment.findViewById(R.id.iv_fish_pond_avatar);
        TextView tvNickName = fishPondDetailComment.findViewById(R.id.cb_fish_pond_nick_name);
        ImageView ivFishPondComment= fishPondDetailComment.findViewById(R.id.iv_fish_pond_comment);
        TextView tvDesc= fishPondDetailComment.findViewById(R.id.tv_fish_pond_desc);
        TextView tvReply= fishPondDetailComment.findViewById(R.id.tv_reply_msg);
        LinearLayout tvBuildReplyMsgContainer= fishPondDetailComment.findViewById(R.id.tv_build_reply_msg_container);

        ShapeLinearLayout commentContainer = this.findViewById(R.id.comment_container);
        mClReplyContainer = commentContainer.findViewById(R.id.tv_fish_pond_submit_comment);
        tvReplyAndGreat.setText(mComment.getSubComments().size()+"回复");
        ivFishPondComment.setVisibility(View.GONE);
        Glide.with(ivAvatar.getContext())
                .load(mComment.getAvatar())
                .placeholder(com.program.moudle_base.R.mipmap.ic_default_avatar)
                .circleCrop()       //圆角
                .into(ivAvatar);
        tvNickName.setText(mComment.getNickname());
        tvDesc.setText(mComment.getPosition()+" · "+ DateUtils.timeFormat(mComment.getCreateTime()));
        tvDesc.setMaxLines(Integer.MAX_VALUE);
        tvReply.setText(mComment.getContent());
        tvBuildReplyMsgContainer.setVisibility(View.GONE);
        RecyclerView rvFishCommentDetailList = this.findViewById(R.id.rv_fish_commend_detail_list);
        rvFishCommentDetailList.setLayoutManager(new LinearLayoutManager(this));
//        rvFishCommentDetailList.addItemDecoration();
        mAdapter = new FishCommentDetailListAdapter();
        rvFishCommentDetailList.setAdapter(mAdapter);
        mAdapter.addData(mComment.getSubComments());

        mReplyBottomSheetDialog = new ReplyBottomSheetDialog(this, R.style.BottomSheetDialog);
    }


    private void showReplyDialog(MomentSubCommentBean item){
        String token = mPreferencesUtils.getString(SharedPreferencesUtils.USER_TOKEN_COOKIE, "");
        if (token.equals("")){
            setRequestError("请先登录");
            ARouter.getInstance()
                    .build(RoutePath.Login.PATH_lOGIN)
                    .withString(RoutePath.PATH,RoutePath.Ucenter.FRAGMENT_UCENTER)
                    .navigation();
            return;
        }
        mReplyBottomSheetDialog.show();
        mReplyBottomSheetDialog.setReplyTitle("回复 @"+(item!=null&&item instanceof MomentSubCommentBean?item.getNickname().toString():mComment.getNickname()));
        mReplyBottomSheetDialog.sendListener(new ReplyBottomSheetDialog.OnSendListener() {
            @Override
            public void onSend(String v) {
                if (item==null){
                    mMoyuCommentDetailActivityPresenter.toIssueComment(
                            new MomentSubComment(mId,v,mComment.getUserId(),mComment.getId())
                    );
                }else {
                    mMoyuCommentDetailActivityPresenter.toIssueComment(
                            new MomentSubComment(mId,v,item.getUserId(),mComment.getId())
                    );
                }
            }
        });
    }

    @Override
    public LifecycleTransformer<Object> TobindToLifecycle() {
        BehaviorSubject<Object> objectBehaviorSubject = BehaviorSubject.create();
        return  RxLifecycle.bind(objectBehaviorSubject);
    }

    @Override
    public void setRequestError(String msg) {
        ToastUtils.showToast(msg);
    }

    @Override
    public void returnSubComment(BaseResponseBean data) {
        if (data.getSuccess()){
            mReplyBottomSheetDialog.dismiss();
        }
        ToastUtils.showToast(data.getMessage());
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