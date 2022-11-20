package com.program.moulde_login.ui.fragment;

import android.view.View;
import android.widget.Button;

import com.program.moudle_base.base.BaseFragment;
import com.program.moudle_base.model.BaseResponseBean;
import com.program.moudle_base.utils.ToastUtils;
import com.program.moudle_base.view.LoadingDialog;
import com.program.moudle_base.view.LoginEditView;
import com.program.moudle_base.view.WaitDialog;
import com.program.moulde_login.R;
import com.program.moulde_login.model.bean.SendSmsVo;
import com.program.moulde_login.model.bean.UserR;
import com.program.moulde_login.presenter.IRegisterPresenter;
import com.program.moulde_login.ui.view.CodeEditView;
import com.program.moulde_login.utils.PresenterManager;
import com.program.moulde_login.view.IRegisterCallback;


public class RegisterFragment extends BaseFragment implements CodeEditView.PhoneCodeListener, IRegisterCallback {

    private CodeEditView mEditTuringCode;
    private LoginEditView mEditPhone;
    private CodeEditView mEditPhoneCode;
    private LoginEditView mEditNickName;
    private LoginEditView mEditPsw;
    private Button mBtnRegister;
    private IRegisterPresenter mRegisterPresenter;
    private LoadingDialog mLoadingDialog;


    @Override
    protected int getRootViewResId() {
        return R.layout.moudlelogin_fragment_register;
    }

    @Override
    protected void initView(View rootView) {
        setupState(State.SUCCESS);
        mEditTuringCode = rootView.findViewById(R.id.edit_turing_code);
        mEditPhone = rootView.findViewById(R.id.edit_phone);
        mEditPhoneCode = rootView.findViewById(R.id.edit_phone_code);
        mEditNickName = rootView.findViewById(R.id.edit_nickname);
        mEditPsw = rootView.findViewById(R.id.edit_password);
        mBtnRegister = rootView.findViewById(R.id.btn_register);

        LoadingDialog.Builder builder = new LoadingDialog.Builder(getContext())
                .setMessage("请求中...")
                .setCancelable(true)//返回键是否可点击
                .setCancelOutside(false);//窗体外是否可点击
        mLoadingDialog = builder.create();

        mEditPhoneCode.setPhoneCodeListener(this);
    }

    @Override
    protected void initListener() {
        mBtnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mEditPhone.getValue().equals("")||mEditPhone.getValue()==null){
                    ToastUtils.showToast("请输入手机号");
                    return;
                }
                if (mEditNickName.getValue().equals("")||mEditNickName.getValue()==null){
                    ToastUtils.showToast("请输入名字");
                    return;
                }
                if (mEditPsw.getValue().equals("")||mEditPsw.getValue()==null){
                    ToastUtils.showToast("请输入密码");
                    return;
                }

                mLoadingDialog.show();
                mRegisterPresenter.postRegister(new UserR(mEditPhone.getValue(),mEditPsw.getValue(),mEditNickName.getValue()),mEditPhoneCode.getValue());
            }
        });
    }

    @Override
    protected void initPresenter() {
        super.initPresenter();
        mRegisterPresenter = PresenterManager.getInstance().getRegisterPresenter();
        mRegisterPresenter.registerViewCallback(this);
    }

    @Override
    public String sendMessage() {
        return null;
    }

    @Override
    public boolean isPreContented() {
        return false;
    }

    @Override
    public void getSms() {
        if (mEditPhone.getValue().equals("")||mEditPhone.getValue()==null){
            ToastUtils.showToast("请输入手机号");
            return;
        }
        if (mEditTuringCode.getValue().equals("")||mEditTuringCode.getValue()==null){
            ToastUtils.showToast("请输入图灵验证码");
            return;
        }
        mLoadingDialog.show();
        String keyCode = mEditTuringCode.getKeyCode();
        mRegisterPresenter.getSmsCode(new SendSmsVo(mEditPhone.getValue(),mEditTuringCode.getValue()),keyCode);

    }

    @Override
    public void setSmsCode(BaseResponseBean data) {
        mLoadingDialog.dismiss();
        ToastUtils.showToast(data.getMessage());
    }

    @Override
    public void setRegister(BaseResponseBean data) {
        mLoadingDialog.dismiss();
//        if (!data.getSuccess()){
            ToastUtils.showToast("身份验证失败");
//        }
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
